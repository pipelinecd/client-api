package org.pipelinecd.client.core

import com.hazelcast.core.Message
import com.hazelcast.core.MessageListener
import com.yammer.dropwizard.lifecycle.Managed
import groovy.util.logging.Slf4j

@Slf4j
class EventReader implements Managed, MessageListener<String> {
    private final EventBusManager busManager

    EventReader(EventBusManager busManager) {
        this.busManager = busManager
    }

    @Override
    void start() throws Exception {
        def topic = busManager.topic
        topic.addMessageListener(this)
    }

    @Override
    void stop() throws Exception {
    }

    @Override
    void onMessage(Message<String> message) {
        log.info('Published by [{}] at [{}]: {}',
                message.publishingMember, new Date(message.publishTime), message.messageObject)
    }
}
