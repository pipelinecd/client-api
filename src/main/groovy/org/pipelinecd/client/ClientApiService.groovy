package org.pipelinecd.client

import com.yammer.dropwizard.Service
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.config.FilterBuilder
import org.eclipse.jetty.servlets.CrossOriginFilter
import org.pipelinecd.client.core.EventBusManager
import org.pipelinecd.client.core.EventReader
import org.pipelinecd.client.resources.IndexResource
import org.pipelinecd.client.resources.PipelinesResource
import org.pipelinecd.client.resources.RunsResource

import static com.yammer.dropwizard.config.HttpConfiguration.ConnectorType.NONBLOCKING

class ClientApiService extends Service<ClientApiConfiguration> {
    @Override
    void initialize(Bootstrap<ClientApiConfiguration> bootstrap) {
        bootstrap.name = 'client-api'
    }

    @Override
    void run(ClientApiConfiguration config, Environment env) throws Exception {
        manageEvents(env)

        forceNonBlockingConnectionType(config);
        addCrossOriginSupport(env)

        env.addResource(new IndexResource())
        env.addResource(new PipelinesResource())
        env.addResource(new RunsResource())
    }

    private void manageEvents(Environment env) {
        def busManager = new EventBusManager()
        env.manage(busManager)
        env.manage(new EventReader(busManager))
    }

    private void addCrossOriginSupport(Environment env) {
        FilterBuilder filterConfig = env.addFilter(CrossOriginFilter, "/*");
        filterConfig.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
    }

    private void forceNonBlockingConnectionType(ClientApiConfiguration config) {
        config.getHttpConfiguration().setConnectorType(NONBLOCKING)
    }
}
