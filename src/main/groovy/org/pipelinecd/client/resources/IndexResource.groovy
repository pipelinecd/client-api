package org.pipelinecd.client.resources

import groovy.util.logging.Slf4j

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Response

import static javax.ws.rs.core.Response.Status.OK

@Slf4j
@Path('/')
class IndexResource {

    @GET
    Response get() {
        Response.ResponseBuilder.newInstance()
                .entity("OK")
                .status(OK)
                .build()
    }
}
