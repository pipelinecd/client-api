package org.pipelinecd.client.resources

import com.google.common.collect.Sets
import groovy.util.logging.Slf4j
import org.joda.time.DateTime
import org.pipelinecd.client.api.Project

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.GenericEntity
import javax.ws.rs.core.Response

@Slf4j
@Path('project')
class ProjectResource {

    @GET
    Response get() {
        Set<Project> projects = [
                new Project(UUID.fromString('7f9c0b40-1b8b-4e67-b7fa-e519e031b6c6'), new DateTime(2013, 12, 25, 11, 45, 40).toDate()),
                new Project(UUID.fromString('8b54e412-73da-4ddd-abc4-d46dda017a36'), new DateTime(2013, 12, 26, 12, 0, 0).toDate()),
                new Project(UUID.fromString('2fde8a18-dd2c-4503-9dcb-dd3308d83437'), new DateTime(2013, 12, 27, 13, 15, 10).toDate()),
                new Project(UUID.fromString('296948e4-b731-46c3-82c2-a318b72c39cc'), new DateTime(2013, 12, 28, 14, 30, 30).toDate()),
        ]
        def entity = new GenericEntity<Set<Project>>(Sets.newHashSet(projects)) {}
        return Response.ok(entity).build()
    }

    @GET
    @Path('{id}')
    Project get(@PathParam('id') String id) {
        return new Project(UUID.fromString(id), new DateTime(2013, 12, 25, 11, 45, 40).toDate())
    }
}
