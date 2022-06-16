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
)