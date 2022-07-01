package com.ua.publGradle.controllers

import com.ua.publGradle.factory.MapFactory
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD
import org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql("/init_books_table.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql("/delete_books_table.sql", executionPhase = AFTER_TEST_METHOD)
class PublishControllerTest(
    @Autowired
    private val webTestClient: WebTestClient,
    @Autowired
    @Qualifier("mapPublishFactory")
    private val mapFactory: MapFactory
) {

    @Test
    fun saveTest() {
        val responseRec = webTestClient
            .post()
            .uri("/publishes")
            .body(BodyInserters.fromValue(mapFactory.create()))
            .exchange()

        responseRec.expectStatus().isCreated

    }

    @Test
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
    fun findAllPublishedTest() {
        val responseRec = webTestClient
            .get()
            .uri("/publishes/published")
            .exchange()

        responseRec.expectStatus().isOk
    }

    @Test
    fun findAllUnpublishedTest() {
        val responseRec = webTestClient
            .get()
            .uri("/publishes/unpublished")
            .exchange()

        responseRec.expectStatus().isOk
    }

    @Test
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
