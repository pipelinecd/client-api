package org.pipelinecd.client

import com.yammer.dropwizard.Service
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import org.pipelinecd.client.resources.IndexResource
import org.pipelinecd.client.resources.PipelineResource

class ClientApiService extends Service<ClientApiConfiguration> {
    @Override
    void initialize(Bootstrap<ClientApiConfiguration> bootstrap) {
        bootstrap.name = 'client-api'
    }

    @Override
    void run(ClientApiConfiguration config, Environment env) throws Exception {
        env.addResource(new IndexResource())
        env.addResource(new PipelineResource())
    }
}
