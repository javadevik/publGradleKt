package com.ua.publGradle.repositories

import com.ua.publGradle.entities.PublishEntity
import org.springframework.data.repository.CrudRepository

interface PublishRepository : CrudRepository<PublishEntity, Long> {

    override fun findAll(): List<PublishEntity>

    fun findAllByTimeLessThanEqual(time: Long): List<PublishEntity>

    fun findAllByTimeGreaterThanOrderByPriority(time: Long): List<PublishEntity>

}