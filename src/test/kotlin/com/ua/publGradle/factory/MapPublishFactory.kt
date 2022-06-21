package com.ua.publGradle.factory

import org.springframework.stereotype.Component
import java.time.Clock

@Component
class MapPublishFactory : MapFactory {

    override fun create(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map["id"] = "1"
        map["title"] = "Some title"
        map["description"] = "Some description"
        map["text"] = "Some title"
        map["priority"] = "1"
        map["time"] = "${Clock.systemDefaultZone().millis()}"
        return map
    }

    override fun createUpdated(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map["title"] = "Updated title"
        map["description"] = "Updated description"
        map["text"] = "Updated title"
        map["priority"] = "2"
        map["time"] = "${Clock.systemDefaultZone().millis()}"
        return map
    }
}