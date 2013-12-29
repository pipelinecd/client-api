package org.pipelinecd.client.resources

import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.GenericType
import com.sun.jersey.api.client.WebResource
import com.yammer.dropwizard.testing.ResourceTest
import org.pipelinecd.client.api.Pipeline
import org.pipelinecd.client.api.PipelineRun
import org.pipelinecd.client.core.PipelineRepository
import org.pipelinecd.client.core.RunsRepository
import spock.lang.Specification
import spock.lang.Unroll

import static org.pipelinecd.client.core.PipelineRepository.*
import static org.pipelinecd.client.core.RunsRepository.*

@Unroll
class PipelinesResourceSpec extends Specification {

    TestResource resource = new TestResource()
    PipelineRepository pipeRepo = new PipelineRepository()
    RunsRepository runRepo = new RunsRepository()

    def 'GET #path results in 200 OK and mocked collection of Pipeline objects'() {
        given:
        def response = request(path)
                .get(ClientResponse)
        expect:
        response.status == ClientResponse.Status.OK.statusCode
        response.length == -1
        response.hasEntity()
        def projects = response.getEntity(new GenericType<Set<Pipeline>>() {})
        projects.size() == 4
        projects.contains(pipeRepo.getById(PIPE_1))
        projects.contains(pipeRepo.getById(PIPE_2))
        projects.contains(pipeRepo.getById(PIPE_3))
        projects.contains(pipeRepo.getById(PIPE_4))

        where:
        path          | _
        '/pipelines'  | _
        '/pipelines/' | _
    }

    def 'GET #path/#pipelineId results in 200 OK and Pipeline object with specific id'() {
        given:
        def response = request("${path}/${pipelineId}")
                .get(ClientResponse)
        expect:
        response.status == ClientResponse.Status.OK.statusCode
        response.length == -1
        response.hasEntity()
        def project = response.getEntity(Pipeline)
        project == pipeRepo.getById(PIPE_1)
        where:
        path         | pipelineId
        '/pipelines' | PIPE_1
    }

    def 'GET #path results in 200 OK and mocked collection of PipelineRun objects of Pipeline'() {
        given:
        def response = request(path)
                .get(ClientResponse)
        expect:
        response.status == ClientResponse.Status.OK.statusCode
        response.length == -1
        response.hasEntity()
        def pipelines = response.getEntity(new GenericType<Set<PipelineRun>>() {})
        pipelines.size() == 3
        pipelines.contains(runRepo.getById(PIPE_4_RUN_1))
        pipelines.contains(runRepo.getById(PIPE_4_RUN_2))
        pipelines.contains(runRepo.getById(PIPE_4_RUN_3))

        where:
        path                         | _
        "/pipelines/${PIPE_4}/runs"  | _
        "/pipelines/${PIPE_4}/runs/" | _
    }

    def 'GET #path/#runId results in 200 OK and PipelineRun object with specific id of Pipeline'() {
        given:
        def response = request("${path}/${runId}")
                .get(ClientResponse)
        expect:
        response.status == ClientResponse.Status.OK.statusCode
        response.length == -1
        response.hasEntity()
        def pipeline = response.getEntity(PipelineRun)
        pipeline == runRepo.getById(runId)

        where:
        path                        | runId
        "/pipelines/${PIPE_1}/runs" | PIPE_1_RUN_1
        "/pipelines/${PIPE_2}/runs" | PIPE_2_RUN_1
        "/pipelines/${PIPE_3}/runs" | PIPE_3_RUN_1
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
            addResource(new PipelinesResource())
        }

        @Override
        protected Client client() {
            return super.client()
        }
    }
}
