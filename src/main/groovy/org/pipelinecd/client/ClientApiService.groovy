package org.pipelinecd.client

import com.yammer.dropwizard.Service
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.config.FilterBuilder
import org.atmosphere.cpr.AtmosphereServlet
import org.eclipse.jetty.servlets.CrossOriginFilter
import org.pipelinecd.client.resources.PipelineResource

import static com.yammer.dropwizard.config.HttpConfiguration.ConnectorType.NONBLOCKING

class ClientApiService extends Service<ClientApiConfiguration> {
    @Override
    void initialize(Bootstrap<ClientApiConfiguration> bootstrap) {
        bootstrap.setName('pipe-client-api')
    }

    @Override
    void run(ClientApiConfiguration config, Environment env) throws Exception {
        env.addResource(new PipelineResource())
        initializeAtmosphere(config, env)
    }

    private initializeAtmosphere(ClientApiConfiguration config, Environment environment) {
        config.getHttpConfiguration().setConnectorType(NONBLOCKING);

        FilterBuilder filterConfig = environment.addFilter(CrossOriginFilter, "/chat");
        filterConfig.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");

        AtmosphereServlet atmosphereServlet = new AtmosphereServlet();
        atmosphereServlet.framework().addInitParameter(
                "com.sun.jersey.config.property.packages",
                "org.pipelinecd.client.resources.chat"
        );
        atmosphereServlet.framework().addInitParameter("org.atmosphere.websocket.messageContentType", "application/json");
        environment.addServlet(atmosphereServlet, "/chat/*");
//        environment.addResource(new ChatResource())
    }
}
