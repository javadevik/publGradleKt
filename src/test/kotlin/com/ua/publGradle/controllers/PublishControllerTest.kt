package com.ua.publGradle.controllers

import com.ua.publGradle.factory.MapFactory
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation::class)
class PublishControllerTest(
    @Autowired
    private val dataSourceContainer: PostgreSQLContainer<*>,
    @Autowired
    private val webTestClient: WebTestClient,
    @Autowired
    @Qualifier("mapPublishFactory")
    private val mapFactory: MapFactory
) {

    @Test
    @DisplayName("container is up and running")
    @Order(1)
    fun containerTest() {
        Assertions.assertTrue(dataSourceContainer.isRunning)
    }

    @Test
    @Order(2)
    fun saveTest() {
        val responseRec = webTestClient
            .post()
            .uri("/publishes")
            .body(BodyInserters.fromValue(mapFactory.create()))
            .exchange()

        responseRec.expectStatus().isCreated

    }

    @Test
    @Order(3)
    fun findByIdWithHttpStatus200Test() {
        val responseRec = webTestClient
            .get()
            .uri { uriBuilder ->
                uriBuilder.path("/publishes/publish")
                    .queryParam("publishId", 1)
                    .build()
            }
            .exchange()

        responseRec.expectStatus().isOk
    }

    @Test
    @Order(4)
    fun findByIdWithHttpStatus404Test() {
        val responseRec = webTestClient
            .get()
            .uri { uriBuilder ->
                uriBuilder.path("/publishes/publish")
                    .queryParam("publishId", -30)
                    .build()
            }
            .exchange()

        responseRec.expectStatus().is4xxClientError
    }

    @Test
    @Order(5)
    fun findAllPublishedTest() {
        val responseRec = webTestClient
            .get()
            .uri("/publishes/published")
            .exchange()

        responseRec.expectStatus().isOk
    }

    @Test
    @Order(6)
    fun findAllUnpublishedTest() {
        val responseRec = webTestClient
            .get()
            .uri("/publishes/unpublished")
            .exchange()

        responseRec.expectStatus().isOk
    }

    @Test
    @Order(7)
    fun updateTest200() {
        val responseRec = webTestClient
            .patch()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/publishes")
                    .queryParam("publishId", 1)
                    .build()
            }
            .body(BodyInserters.fromValue(mapFactory.createUpdated()))
            .exchange()

        responseRec.expectStatus().isOk
    }

    @Test
    @Order(8)
    fun updateTest403() {
        val responseRec = webTestClient
            .patch()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/publishes")
                    .queryParam("publishId", -2)
                    .build()
            }
            .body(BodyInserters.fromValue(mapFactory.createUpdated()))
            .exchange()

        responseRec.expectStatus().is4xxClientError
    }

    @Test
    @Order(9)
    fun deleteTest() {
        val responseRec = webTestClient
            .delete()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/publishes")
                    .queryParam("publishId", 1)
                    .build()
            }
            .exchange()

        responseRec.expectStatus().isOk
    }
}
