package org.pipelinecd.client

import com.yammer.dropwizard.config.Environment
import org.pipelinecd.client.resources.IndexResource
import org.pipelinecd.client.resources.PipelineResource
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ClientApiServiceSpec extends Specification {

    def 'Serves the IndexResource'() {
        given:
        def env = Mock(Environment)
        def service = new ClientApiService()
        def config = new ClientApiConfiguration()

        when:
        service.run(config, env)

        then:
        1 * env.addResource(_ as IndexResource)
    }

    def 'Serves the PipelineResource'() {
        given:
        def env = Mock(Environment)
        def service = new ClientApiService()
        def config = new ClientApiConfiguration()

        when:
        service.run(config, env)

        then:
        1 * env.addResource(_ as PipelineResource)
    }
}
