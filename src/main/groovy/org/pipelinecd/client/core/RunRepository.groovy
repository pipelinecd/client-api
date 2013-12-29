package org.pipelinecd.client.core

import org.joda.time.DateTime
import org.pipelinecd.client.api.PipelineRun
import org.pipelinecd.client.api.PipelineRunStatus
import org.pipelinecd.client.api.Stage
import org.pipelinecd.client.api.StageStatus

import static org.pipelinecd.client.core.PipelineRepository.*

class RunRepository {
    public static final String PIPE_1_RUN_1 = '7f9c0b40-1b8b-4e67-b7fa-e519e031b6c6'
    public static final String PIPE_2_RUN_1 = '8b54e412-73da-4ddd-abc4-d46dda017a36'
    public static final String PIPE_3_RUN_1 = '296948e4-b731-46c3-82c2-a318b72c39cc'
    public static final String PIPE_4_RUN_1 = '2fde8a18-dd2c-4503-9dcb-dd3308d83437'
    public static final String PIPE_4_RUN_2 = '3fde8a18-dd2c-4503-9dcb-dd3308d83437'
    public static final String PIPE_4_RUN_3 = '4fde8a18-dd2c-4503-9dcb-dd3308d83437'
    static def pipes = [
            (PIPE_1): [
                    new PipelineRun(UUID.fromString(PIPE_1_RUN_1), new DateTime(2013, 12, 25, 11, 45, 40).toDate(), PipelineRunStatus.RUNNING, 'compile', [
                            new Stage(UUID.randomUUID(), "commit", StageStatus.SUCCESS),
                            new Stage(UUID.randomUUID(), "component-test", StageStatus.SUCCESS),
                            new Stage(UUID.randomUUID(), "deploy", StageStatus.SUCCESS),
                            new Stage(UUID.randomUUID(), "selenium", StageStatus.FAILED),
                            new Stage(UUID.randomUUID(), "release", StageStatus.TODO),
                    ] as Set),
            ],
            (PIPE_2): [
                    new PipelineRun(UUID.fromString(PIPE_2_RUN_1), new DateTime(2013, 12, 26, 12, 0, 0).toDate(), PipelineRunStatus.FAILED, 'component-test', [
                            new Stage(UUID.randomUUID(), "commit", StageStatus.SUCCESS),
                            new Stage(UUID.randomUUID(), "release", StageStatus.NEED_ACTION),
                    ] as Set),
            ],
            (PIPE_3): [
                    new PipelineRun(UUID.fromString(PIPE_3_RUN_1), new DateTime(2013, 12, 28, 14, 30, 30).toDate(), PipelineRunStatus.NEED_ACTION, 'deploy to prod', [
                            new Stage(UUID.randomUUID(), "commit", StageStatus.SUCCESS),
                            new Stage(UUID.randomUUID(), "component-test", StageStatus.RUNNING),
                            new Stage(UUID.randomUUID(), "deploy", StageStatus.TODO),
                            new Stage(UUID.randomUUID(), "release", StageStatus.TODO),
                    ] as Set),
            ],
            (PIPE_4): [
                    new PipelineRun(UUID.fromString(PIPE_4_RUN_1), new DateTime(2013, 12, 27, 13, 15, 10).toDate(), PipelineRunStatus.SUCCESS, 'deploy to prod', [
                            new Stage(UUID.randomUUID(), "commit", StageStatus.SUCCESS),
                            new Stage(UUID.randomUUID(), "component-test", StageStatus.SUCCESS),
                            new Stage(UUID.randomUUID(), "deploy", StageStatus.SUCCESS),
                            new Stage(UUID.randomUUID(), "selenium", StageStatus.FAILED),
                            new Stage(UUID.randomUUID(), "release", StageStatus.TODO),
                    ] as Set),
                    new PipelineRun(UUID.fromString(PIPE_4_RUN_2), new DateTime(2013, 12, 27, 13, 15, 10).toDate(), PipelineRunStatus.SUCCESS, 'deploy to prod', [
                            new Stage(UUID.randomUUID(), "commit", StageStatus.SUCCESS),
                            new Stage(UUID.randomUUID(), "component-test", StageStatus.SUCCESS),
                            new Stage(UUID.randomUUID(), "deploy", StageStatus.SUCCESS),
                            new Stage(UUID.randomUUID(), "selenium", StageStatus.FAILED),
                            new Stage(UUID.randomUUID(), "release", StageStatus.TODO),
                    ] as Set),
                    new PipelineRun(UUID.fromString(PIPE_4_RUN_3), new DateTime(2013, 12, 27, 13, 15, 10).toDate(), PipelineRunStatus.SUCCESS, 'deploy to prod', [
                            new Stage(UUID.randomUUID(), "commit", StageStatus.SUCCESS),
                            new Stage(UUID.randomUUID(), "component-test", StageStatus.SUCCESS),
                            new Stage(UUID.randomUUID(), "deploy", StageStatus.SUCCESS),
                            new Stage(UUID.randomUUID(), "selenium", StageStatus.FAILED),
                            new Stage(UUID.randomUUID(), "release", StageStatus.TODO),
                    ] as Set),
            ]
    ]

    List<PipelineRun> getAll() {
        return pipes.values().flatten()
    }

    PipelineRun getById(String id) {
        return all.find { it.id.toString() == id }
    }

    List<PipelineRun> getAllByPipelineId(String id) {
        return pipes.get(id)
    }
}
