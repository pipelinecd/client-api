package org.pipelinecd.client.core

import org.joda.time.DateTime
import org.pipelinecd.client.api.Pipeline

class PipelineRepository {
    public static final String PIPE_1 = '7f9c0b40-1b8b-4e67-b7fa-e519e031b6c6'
    public static final String PIPE_2 = '8b54e412-73da-4ddd-abc4-d46dda017a36'
    public static final String PIPE_3 = '2fde8a18-dd2c-4503-9dcb-dd3308d83437'
    public static final String PIPE_4 = '296948e4-b731-46c3-82c2-a318b72c39cc'
    private Set<Pipeline> pipelines = [
            new Pipeline(UUID.fromString(PIPE_1), new DateTime(2013, 12, 25, 11, 45, 40).toDate(), 'projectX'),
            new Pipeline(UUID.fromString(PIPE_2), new DateTime(2013, 12, 26, 12, 0, 0).toDate(), 'projectL'),
            new Pipeline(UUID.fromString(PIPE_3), new DateTime(2013, 12, 27, 13, 15, 10).toDate(), 'projectZ'),
            new Pipeline(UUID.fromString(PIPE_4), new DateTime(2013, 12, 28, 14, 30, 30).toDate(), 'projectY'),
    ]

    Set<Pipeline> getAll() {
        return pipelines
    }

    Pipeline getById(String id) {
        return pipelines.find { it.id.toString() == id }
    }
}
