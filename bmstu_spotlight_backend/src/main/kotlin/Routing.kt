package com.thread_sleepers

import com.thread_sleepers.db.DbManager
import com.thread_sleepers.models.request.AuthRequest
import com.thread_sleepers.models.response.ScheduleResponse
import com.thread_sleepers.util.ScheduleGenerator
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.logging.Logger
import jdk.internal.util.StaticProperty.userName
import kotlin.math.roundToInt

fun Application.configureRouting(config: JWTConfig) {

    routing {
        authRoutes(config)

        routesWithoutAuth()

        authenticate(JWT_AUTH) {
            mainRoutes()
        }
    }
}

fun Route.mainRoutes() {
    get("/") {
        val principal = call.principal<JWTPrincipal>()
        val userName = principal?.payload?.getClaim(PAYLOAD_CLAIM)?.asString()
        val expiresAt = principal?.expiresAt?.time?.minus(System.currentTimeMillis())

        if (!userName.isNullOrEmpty() && DbManager.checkUserInDb(userName)) {
            call.respondText("Hello $userName! The token expiresAt $expiresAt")
        } else {
            call.respond(status = HttpStatusCode.Unauthorized, message = "Invalid auth data")
        }
    }

    get("/schedule") {
        val principal = call.principal<JWTPrincipal>()
        val userName = principal?.payload?.getClaim(PAYLOAD_CLAIM)?.asString()
        if (!userName.isNullOrEmpty() && DbManager.checkUserInDb(userName)) {
            call.respond(ScheduleResponse(ScheduleGenerator.generate()))
        } else {
            call.respond(status = HttpStatusCode.Unauthorized, message = "Invalid auth data")
        }
    }
}

fun Route.routesWithoutAuth() {
    get("/schedule") {
        call.respond(ScheduleResponse(ScheduleGenerator.generate()))
    }
    get("/profile") {
        // call.respond(ScheduleResponse(ScheduleGenerator.generate()))
    }
}

fun Route.authRoutes(config: JWTConfig) {

    post("signup") {
        val requestData = call.receive<AuthRequest>()

        if (DbManager.checkUserInDb(requestData.userName)) {
            call.respond(status = HttpStatusCode.BadRequest, "User already exist")
        } else {
            DbManager.putToDb(requestData)
            val token = generateToken(config, userName = requestData.userName)
            call.respond(mapOf("token" to token))
        }
    }

    post("login") {
        val requestData = call.receive<AuthRequest>()

        val storedPassword = DbManager.getPasswordForUser(requestData.userName)
            ?: return@post call.respond(status = HttpStatusCode.BadRequest, "User doesn't exists")

        if (storedPassword == requestData.password) {
            val token = generateToken(config = config, userName = requestData.userName)
            call.respond(mapOf("token" to token))
        } else {
            call.respond(status = HttpStatusCode.BadRequest, "Invalid credentials")
        }
    }
}


/*val usersDB = mutableMapOf<String, String>()


    post("signup") {
        val requestData = call.receive<AuthRequest>()

        if (usersDB.containsKey(requestData.userName)) {
            call.respond(status = HttpStatusCode.BadRequest, "User already exist")
        } else {
            usersDB[requestData.userName] = requestData.password
            val token = generateToken(config, userName = requestData.userName)
            call.respond(mapOf("token" to token))
        }
    }

    post("login") {
        val requestData = call.receive<AuthRequest>()

        val storedPassword = usersDB[requestData.userName] ?:
        return@post call.respond(status = HttpStatusCode.BadRequest,"User doesn't exists")

        if (storedPassword == requestData.password) {
            val token = generateToken(config = config, userName = requestData.userName)
            call.respond(mapOf("token" to token))
        } else {
            call.respond(status = HttpStatusCode.BadRequest,"Invalid credentials")
        }
    }*/
