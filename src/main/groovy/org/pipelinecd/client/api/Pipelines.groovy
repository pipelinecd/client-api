package org.pipelinecd.client.api

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
class Pipelines {
    Set<Pipeline> pipeline

    Pipelines() {
    }

    Pipelines(Set<Pipeline> pipeline) {
        this.pipeline = pipeline
    }
}
