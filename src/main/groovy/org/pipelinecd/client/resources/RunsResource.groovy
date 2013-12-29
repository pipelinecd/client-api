package org.pipelinecd.client.resources

import com.google.common.collect.Sets
import groovy.util.logging.Slf4j
import org.pipelinecd.client.api.PipelineRun
import org.pipelinecd.client.core.RunRepository

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.GenericEntity
import javax.ws.rs.core.Response

@Slf4j
@Path('runs')
class RunsResource {

    RunRepository repo = new RunRepository()

    @GET
    Response get() {
        def entity = new GenericEntity<Set<PipelineRun>>(Sets.newHashSet(repo.all)) {}
        return Response.ok(entity).build()
    }

    @GET
    @Path('{id}')
    PipelineRun get(@PathParam('id') String id) {
        return repo.getById(id)
    }
}
