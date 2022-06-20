package com.ua.publGradle.controllers

import com.ua.publGradle.entities.PublishEntity
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Clock

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation::class)
class PublishControllerTest(
    @Autowired
    private val publishController: PublishController,
    @Autowired
    private val clock: Clock,
    @Autowired
    private val dataSourceContainer: PostgreSQLContainer<*>
) {

    @Test
    @DisplayName("container is up and running")
    @Order(1)
    fun containerTest() {
        Assertions.assertTrue(dataSourceContainer.isRunning)
    }

    @Test
    @Order(2)
    fun saveWithHttpStatus200Test() {
        val response: ResponseEntity<PublishEntity>
        val publish = PublishEntity(
            1,
            "title",
            "desc",
            "text",
            2,
            clock.millis()
        )
        response = publishController.save(publish)
        Assertions.assertEquals(publish, response.body)
    }

    @Test
    @Order(2)
    fun saveWithHttpStatus403Test() {
        val response: ResponseEntity<PublishEntity>
        val publish = PublishEntity(
            -1,
            "title",
            "desc",
            "text",
            2,
            1655304000000
        )
        response = publishController.save(publish)
        Assertions.assertNotEquals(publish, response.body)
    }

    @Test
    @Order(3)
    fun findByIdWithHttpStatus200Test() {
        val response = publishController.findById(1)
        Assertions.assertFalse(response.statusCode.isError)
        Assertions.assertNotNull(response.body)
    }

    @Test
    @Order(4)
    fun findByIdWithHttpStatus404Test() {
        val response = publishController.findById(-1)
        Assertions.assertTrue(response.statusCode.isError)
        Assertions.assertTrue(response.statusCode.is4xxClientError)
        Assertions.assertNull(response.body)
    }

    @Test
    @Order(5)
    fun findAllPublishedTest() {
        val response = publishController.findAllPublished()
        val published = response.body
        Assertions.assertFalse(published?.isEmpty()?:true)
    }

    @Test
    @Order(6)
    fun findAllUnpublishedTest() {
        val response = publishController.findAllUnpublished()
        val published = response.body
        Assertions.assertTrue(published?.isEmpty()?:false)
    }

    @Test
    @Order(7)
    fun updateTest200() {
        val newPublish = PublishEntity(
            1,
            "Updated title",
            "Updated desc",
            "Updated text",
            4,
            1655304000000
        )
        val response = publishController.update(1, newPublish)
        Assertions.assertFalse(response.statusCode.isError)
        Assertions.assertEquals(newPublish, response.body)
    }

    @Test
    @Order(8)
    fun updateTest403() {
        val newPublish = PublishEntity(
            1,
            "Updated title",
            "Updated desc",
            "Updated text",
            4,
            1655304000000
        )
        val response = publishController.update(-1, newPublish)
        Assertions.assertTrue(response.statusCode.isError)
        Assertions.assertTrue(response.statusCode.is4xxClientError)
        Assertions.assertNotEquals(newPublish, response.body)
    }

    @Test
    @Order(9)
    fun deleteTest() {
        val response = publishController.delete(1)
        Assertions.assertFalse(response.statusCode.isError)
    }
}