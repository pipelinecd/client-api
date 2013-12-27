package org.pipelinecd.client

import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.config.FilterBuilder
import org.eclipse.jetty.servlet.FilterHolder
import org.eclipse.jetty.servlets.CrossOriginFilter
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
        1 * env.addFilter(_ as CrossOriginFilter) >> new FilterBuilder(new FilterHolder(), null)
        1 * env.addResource(_ as PipelineResource)
    }
}
