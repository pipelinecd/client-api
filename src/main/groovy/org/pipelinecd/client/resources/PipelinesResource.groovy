package org.pipelinecd.client.resources

import com.google.common.collect.Sets
import groovy.util.logging.Slf4j
import org.pipelinecd.client.api.Pipeline
import org.pipelinecd.client.core.PipelineRepository

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.GenericEntity
import javax.ws.rs.core.Response

@Slf4j
@Path('pipelines')
class PipelinesResource {

    PipelineRepository repo = new PipelineRepository()

    @GET
    Response getPipelines() {
        def entity = new GenericEntity<Set<Pipeline>>(Sets.newHashSet(repo.all)) {}
        return Response.ok(entity).build()
    }

    @GET
    @Path('{id}')
    Pipeline getPipeline(@PathParam('id') String id) {
        return repo.getById(id)
    }

    @Path('{pipelineId}/runs')
    PipelineRunsResource getRuns(@PathParam('pipelineId') String pipelineId) {
        return new PipelineRunsResource(pipelineId);
    }
}
