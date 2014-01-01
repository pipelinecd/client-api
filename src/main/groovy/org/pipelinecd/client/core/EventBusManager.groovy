package org.pipelinecd.client.core

import com.hazelcast.config.Config
import com.hazelcast.config.TopicConfig
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance
import com.yammer.dropwizard.lifecycle.Managed

class EventBusManager implements Managed {
    public static final String DEFAULT_TOPIC = 'default'
    HazelcastInstance instance

    @Override
    void start() throws Exception {
        def config = new Config()
        config.instanceName = 'client-api'

        def topic = new TopicConfig()
        topic.name = DEFAULT_TOPIC
        config.addTopicConfig(topic)
        instance = Hazelcast.newHazelcastInstance(config)
    }

    @Override
    void stop() throws Exception {
        instance.shutdown()
        Hazelcast.shutdownAll()
    }

    def getTopic() {
        return getTopic(DEFAULT_TOPIC)
    }

    def getTopic(String topic) {
        if (!instance) {
            throw new IllegalStateException("Event bus has not been started yet!")
        }
        return instance.getTopic(topic)
    }
}
