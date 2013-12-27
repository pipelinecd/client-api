package org.pipelinecd.client.resources

import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.WebResource
import com.yammer.dropwizard.testing.ResourceTest
import spock.lang.Specification

class PipelineResourceSpec extends Specification {

    TestResource resource = new TestResource()

    def 'GET / results in 200 OK'() {
        given:
        def response = request('/')
                .get(ClientResponse)
        expect:
        response.status == ClientResponse.Status.OK.statusCode
        response.hasEntity()
        response.getEntity(String) == 'OK'
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
            addResource(new PipelineResource())
        }

        @Override
        protected Client client() {
            return super.client()
        }
    }
}
