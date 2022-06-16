package com.ua.publGradle.services

import com.ua.publGradle.entities.PublishEntity

interface PublishService {
    fun findAll(): List<PublishEntity>
    fun findById(id: Long): PublishEntity?
    fun save(publish: PublishEntity): PublishEntity
    fun update(id: Long, publishUpdate: PublishEntity): PublishEntity?
    fun delete(publishId: Long)
    fun getAllPublished(): List<PublishEntity>
    fun getAllUnpublished(): List<PublishEntity>
}