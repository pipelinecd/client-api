package org.pipelinecd.client.resources.chat

import groovy.util.logging.Slf4j
import org.atmosphere.config.service.Disconnect
import org.atmosphere.config.service.ManagedService
import org.atmosphere.config.service.Ready
import org.atmosphere.cpr.AtmosphereResource
import org.atmosphere.cpr.AtmosphereResourceEvent

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Produces

import static javax.ws.rs.core.MediaType.APPLICATION_JSON

@Slf4j
@ManagedService(path = '/')
class ChatResource {

    /**
     * Invoked when the connection as been fully established and suspended, e.g ready for receiving messages.
     *
     * @param r
     */
    @Ready
    public void onReady(final AtmosphereResource r) {
        log.info("Browser {} connected.", r.uuid());
    }

    /**
     * Invoked when the client disconnect or when an unexpected closing of the underlying connection happens.
     *
     * @param event
     */
    @Disconnect
    public void onDisconnect(AtmosphereResourceEvent event) {
        if (event.isCancelled()) {
            log.info("Browser {} unexpectedly disconnected", event.getResource().uuid());
        } else if (event.isClosedByClient()) {
            log.info("Browser {} closed the connection", event.getResource().uuid());
        }
    }

    /**
     * Simple annotated class that demonstrate how {@link org.atmosphere.config.managed.Encoder} and {@link org.atmosphere.config.managed.Decoder
     * can be used.
     *
     * @param message an instance of {@link Message}* @return
     * @throws IOException
     */
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public ChatMessage onMessage(ChatMessage message) throws IOException {
        log.info("{} just send {}", message.getAuthor(), message.getMessage());
        return message;
    }
}
