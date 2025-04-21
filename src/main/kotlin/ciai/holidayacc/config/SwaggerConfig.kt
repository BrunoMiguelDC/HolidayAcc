package ciai.holidayacc.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

// Based on: https://www.bezkoder.com/spring-boot-swagger-3/
@Configuration
class SwaggerConfig {

    @Value("\${openapi.dev-url}")
    private val devUrl: String? = null

    @Value("\${openapi.prod-url}")
    private val prodUrl: String? = null

    @Bean
    fun myOpenAPI(): OpenAPI {
        val devServer = Server()
        devServer.url = devUrl
        devServer.description = "Server URL in Development environment"
        val prodServer = Server()
        prodServer.url = prodUrl
        prodServer.description = "Server URL in Production environment"
        val contact = Contact()
        contact.email = "holidayacc@gmail.com"
        contact.name = "Holiday Acc"
        contact.url = "https://holidayacc.com"
        val mitLicense: License = License().name("MIT License").url("https://choosealicense.com/licenses/mit/")
        val info: Info = Info()
            .title("Apartment Reservation Management API")
            .version("1.0")
            .contact(contact)
            .description("This API exposes endpoints to manage apartment reservations.")
            .termsOfService("https://www.bezkoder.com/terms")
            .license(mitLicense)
        return OpenAPI().info(info).servers(listOf(devServer, prodServer))
    }

}