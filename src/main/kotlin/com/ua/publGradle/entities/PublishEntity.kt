package com.ua.publGradle.entities

import javax.persistence.*

@Entity
@Table(name = "publishes")
class PublishEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    var title: String,
    var description: String,

    var text: String,
    var priority: Int,

    @Column(name = "publish_at")
    var time: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PublishEntity

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (text != other.text) return false
        if (priority != other.priority) return false
        if (time != other.time) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + priority
        result = 31 * result + time.hashCode()
        return result
    }
}

