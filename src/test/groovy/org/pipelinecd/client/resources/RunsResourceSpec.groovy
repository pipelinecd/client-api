package org.pipelinecd.client.resources

import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.GenericType
import com.sun.jersey.api.client.WebResource
import com.yammer.dropwizard.testing.ResourceTest
import org.joda.time.DateTime
import org.pipelinecd.client.api.PipelineRun
import org.pipelinecd.client.api.PipelineRunStatus
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class RunsResourceSpec extends Specification {

    TestResource resource = new TestResource()

    def 'GET #path results in 200 OK and mocked collection of PipelineRun objects'() {
        given:
        def response = request(path)
                .get(ClientResponse)
        expect:
        response.status == ClientResponse.Status.OK.statusCode
        response.length == -1
        response.hasEntity()
        def pipelines = response.getEntity(new GenericType<Set<PipelineRun>>() {})
        pipelines.size() == 4
        pipelines.contains(new PipelineRun(UUID.fromString('7f9c0b40-1b8b-4e67-b7fa-e519e031b6c6'), new DateTime(2013, 12, 25, 11, 45, 40).toDate(), PipelineRunStatus.RUNNING, 'compile'))
        pipelines.contains(new PipelineRun(UUID.fromString('8b54e412-73da-4ddd-abc4-d46dda017a36'), new DateTime(2013, 12, 26, 12, 0, 0).toDate(), PipelineRunStatus.FAILED, 'component-test'))
        pipelines.contains(new PipelineRun(UUID.fromString('2fde8a18-dd2c-4503-9dcb-dd3308d83437'), new DateTime(2013, 12, 27, 13, 15, 10).toDate(), PipelineRunStatus.SUCCESS, 'deploy to prod'))
        pipelines.contains(new PipelineRun(UUID.fromString('296948e4-b731-46c3-82c2-a318b72c39cc'), new DateTime(2013, 12, 28, 14, 30, 30).toDate(), PipelineRunStatus.NEED_ACTION, 'deploy to prod'))

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
        pipeline == new PipelineRun(UUID.fromString(pipelineId), new DateTime(2013, 12, 25, 11, 45, 40).toDate(), PipelineRunStatus.RUNNING, 'compile')

        where:
        path    | pipelineId
        '/runs' | '992acd98-6b67-4da7-b02e-56f2f8126681'
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
