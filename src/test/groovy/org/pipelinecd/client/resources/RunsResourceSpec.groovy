package org.pipelinecd.client.resources

import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.GenericType
import com.sun.jersey.api.client.WebResource
import com.yammer.dropwizard.testing.ResourceTest
import org.pipelinecd.client.api.PipelineRun
import org.pipelinecd.client.core.RunRepository
import spock.lang.Specification
import spock.lang.Unroll

import static org.pipelinecd.client.core.PipelineRepository.PIPE_1
import static org.pipelinecd.client.core.RunRepository.*

@Unroll
class RunsResourceSpec extends Specification {

    TestResource resource = new TestResource()
    RunRepository repo = new RunRepository()

    def 'GET #path results in 200 OK and mocked collection of PipelineRun objects'() {
        given:
        def response = request(path)
                .get(ClientResponse)
        expect:
        response.status == ClientResponse.Status.OK.statusCode
        response.length == -1
        response.hasEntity()
        def pipelines = response.getEntity(new GenericType<Set<PipelineRun>>() {})
        pipelines.size() == 6
        pipelines.contains(repo.getById(PIPE_1_RUN_1))
        pipelines.contains(repo.getById(PIPE_2_RUN_1))
        pipelines.contains(repo.getById(PIPE_3_RUN_1))
        pipelines.contains(repo.getById(PIPE_4_RUN_1))
        pipelines.contains(repo.getById(PIPE_4_RUN_2))
        pipelines.contains(repo.getById(PIPE_4_RUN_3))

        where:
        path     | _
        '/runs'  | _
        '/runs/' | _
    }

    def 'GET #path/#pipelineId results in 200 OK and PipelineRun object with specific id'() {
        given:
        def response = request("${path}/${pipelineId}")
                .get(ClientResponse)
        expect:
        response.status == ClientResponse.Status.OK.statusCode
        response.length == -1
        response.hasEntity()
        def pipeline = response.getEntity(PipelineRun)
        pipeline == repo.getById(pipelineId)

        where:
        path    | pipelineId
        '/runs' | PIPE_1
    }

    def setup() {
        resource.setUpJersey()
    }

    def cleanup() {
        resource.tearDownJersey()
    }

    private WebResource.Builder request(String path) {
        return resource.client().resource(path).requestBuilder
    }

    private class TestResource extends ResourceTest {

        @Override
        protected void setUpResources() throws Exception {
            addResource(new RunsResource())
        }

        @Override
        protected Client client() {
            return super.client()
        }
    }
}
