package org.pipelinecd.client

import com.yammer.dropwizard.config.Environment
import org.pipelinecd.client.resources.PipelineResource
import spock.lang.Specification

class ClientApiServiceSpec extends Specification {

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
