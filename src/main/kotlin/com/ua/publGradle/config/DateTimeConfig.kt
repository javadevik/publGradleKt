package com.ua.publGradle.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
@ComponentScan("com.ua.publGradle")
class DateTimeConfig {

    @Bean
    fun clockBean(): Clock {
        return Clock.systemDefaultZone()
    }
}