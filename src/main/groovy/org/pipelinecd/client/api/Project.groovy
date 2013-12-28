package org.pipelinecd.client.api

import groovy.transform.Canonical

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

@Canonical
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
class Project {
    UUID id
    Date creationTime
}
