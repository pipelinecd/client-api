package org.pipelinecd.client.resources

import groovy.util.logging.Slf4j

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Slf4j
@Path('/')
class PipelineResource {

    @GET
    Response get() {
        Response.ok().entity('OK').build()
    }
}
