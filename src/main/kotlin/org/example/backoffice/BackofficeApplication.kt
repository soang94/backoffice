package org.example.backoffice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class BackofficeApplication

fun main(args: Array<String>) {
    runApplication<BackofficeApplication>(*args)
}
