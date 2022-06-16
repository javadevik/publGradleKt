package com.ua.publGradle.services.impl

import com.ua.publGradle.entities.PublishEntity
import com.ua.publGradle.repositories.PublishRepository
import com.ua.publGradle.services.PublishService
import com.ua.publGradle.util.DateService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PublishServiceImpl(
    private val publishRepository: PublishRepository,
    private val dateService: DateService
) : PublishService {

    override fun findAll(): List<PublishEntity> {
        return publishRepository.findAll()
    }

    override fun findById(id: Long): PublishEntity? {
        return publishRepository
            .findByIdOrNull(id)
    }

    override fun getAllPublished(): List<PublishEntity> {
        val currentTime = dateService.getCurrentTime()
        return publishRepository.findAllByTimeLessThanEqual(currentTime)
    }

    override fun getAllUnpublished(): List<PublishEntity> {
        val currentTime = dateService.getCurrentTime()
        return publishRepository.findAllByTimeGreaterThanOrderByPriority(currentTime)
    }

    override fun save(publish: PublishEntity): PublishEntity {
        return publishRepository.save(publish)
    }

    override fun update(id: Long, publishUpdate: PublishEntity): PublishEntity? {
        val publishToUpdate = findById(id)
            ?: return null

        with(publishToUpdate) {
            title = publishUpdate.title
            description = publishUpdate.description
            text = publishUpdate.text
            time = publishUpdate.time
            priority = publishUpdate.priority
        }

        return publishRepository.save(publishToUpdate)
    }

    override fun delete(publishId: Long) {
        publishRepository.deleteById(publishId)
    }

}