package com.ua.publGradle

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.PostgreSQLContainer

@SpringBootTest
class PublGradleApplicationTests(
	@Autowired
	private val dataSourceContainer: PostgreSQLContainer<*>
) {
	@Test
	@DisplayName("container is up and running")
	fun containerTest() {
		Assertions.assertTrue(dataSourceContainer.isRunning)
	}

	@Test
	fun contextLoads() {
	}

}
