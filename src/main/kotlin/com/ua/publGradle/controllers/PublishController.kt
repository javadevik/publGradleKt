package com.ua.publGradle.controllers

import com.ua.publGradle.entities.PublishEntity
import com.ua.publGradle.services.PublishService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/publishes")
class PublishController(
    private val publishService: PublishService
) {
    @GetMapping
    fun findAll(): ResponseEntity<List<PublishEntity>> {
        val publishes = publishService.findAll();
        return ResponseEntity(publishes, HttpStatus.OK)
    }

    @GetMapping("/publish")
    fun findById(@RequestParam publishId: Long): ResponseEntity<*> {
        val publishFound = publishService.findById(publishId)
        return ResponseEntity(publishFound, HttpStatus.OK)
    }

    @GetMapping("/published")
    fun findAllPublished(): ResponseEntity<List<PublishEntity>> {
        val publishes = publishService.getAllPublished()
        return ResponseEntity(publishes, HttpStatus.OK)
    }

    @GetMapping("/unpublished")
    fun findAllUnpublished(): ResponseEntity<List<PublishEntity>> {
        val publishes = publishService.getAllUnpublished()
        return ResponseEntity(publishes, HttpStatus.OK)
    }

    @PostMapping
    fun save(@RequestBody publish: PublishEntity): ResponseEntity<PublishEntity> {
        val publishSaved = publishService.save(publish)
        return ResponseEntity(publishSaved, HttpStatus.CREATED)
    }

    @PatchMapping
    fun update(
        @RequestParam publishId: Long,
        @RequestBody publishUpdate: PublishEntity
    ): ResponseEntity<PublishEntity> {
        val publishUpdated = publishService.update(publishId, publishUpdate)
            ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return ResponseEntity(publishUpdated, HttpStatus.OK)
    }

    @DeleteMapping
    fun delete(@RequestParam publishId: Long): ResponseEntity<*> {
        publishService.delete(publishId)
        return ResponseEntity.ok(HttpStatus.OK)
    }
}