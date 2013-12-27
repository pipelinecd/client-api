package org.pipelinecd.client.resources.chat

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class ChatMessage {
    String message
    String author
    final long time

    ChatMessage() {
        this("", "");
    }

    ChatMessage(String author, String message) {
        this.author = author;
        this.message = message;
        this.time = new Date().getTime();
    }
}
