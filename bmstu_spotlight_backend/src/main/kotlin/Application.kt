package com.thread_sleepers

import io.ktor.server.application.*
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.netty.EngineMain


fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    val config = getJwtConfig(environment.config)

    configureLogging()
    configureSerialization()
    configureAuthorization(config)
    configureRouting(config)
}

private fun getJwtConfig(appConfig: ApplicationConfig): JWTConfig {
    val jwt = appConfig.config("ktor.jwt")
    val realm = jwt.property("realm").getString()
    val secret = jwt.property("secret").getString()
    val issuer = jwt.property("issuer").getString()
    val audience = jwt.property("audience").getString()
    val tokenExpiry = jwt.property("expiry").getString().toLong()

    return JWTConfig(
        realm = realm,
        secret = secret,
        issuer = issuer,
        audience = audience,
        tokenExpiry = tokenExpiry
    )
}
