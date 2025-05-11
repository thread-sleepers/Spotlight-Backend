package com.thread_sleepers

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respondText
import java.util.Date

const val PAYLOAD_CLAIM = "username"
const val JWT_AUTH = "jwt-auth"

fun Application.configureAuthorization(config: JWTConfig) {

    install(Authentication) {
        jwt(JWT_AUTH) {
            realm = config.realm

            val jwtVerifier = JWT
                .require(Algorithm.HMAC256(config.secret))
                .withAudience(config.audience)
                .withIssuer(config.issuer)
                .build()

            verifier(jwtVerifier)

            validate { credential ->
                val userName = credential.payload.getClaim(PAYLOAD_CLAIM).asString()
                if (!userName.isNullOrBlank()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { _, _ ->
                call.respondText(
                    text = "Token is not valid or has expired",
                    status = HttpStatusCode.Unauthorized
                )
            }
        }
    }
}

fun generateToken(config: JWTConfig, userName: String): String {
    return JWT.create()
        .withAudience(config.audience)
        .withIssuer(config.issuer)
        .withClaim(PAYLOAD_CLAIM, userName)
        .withExpiresAt(Date(System.currentTimeMillis() + config.tokenExpiry))
        .sign(Algorithm.HMAC256(config.secret))
}

data class JWTConfig(
    val realm: String,
    val secret: String,
    val issuer: String,
    val audience: String,
    val tokenExpiry: Long,
)

