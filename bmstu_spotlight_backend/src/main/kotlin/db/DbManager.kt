package com.thread_sleepers.db

import com.thread_sleepers.models.request.AuthRequest
import java.io.File
import java.nio.file.Paths

object DbManager {
    val dbFile = File("db/users.txt").apply {
        parentFile?.mkdirs()
        if (!exists()) {
            createNewFile()
        }
    }

    fun putToDb(authRequest: AuthRequest) {
        dbFile.appendText("${authRequest.userName} ${authRequest.password}\n")
    }

    fun checkUserInDb(userName: String): Boolean {
        val lines = dbFile.readLines()
        for (line in lines) {
            if (line.startsWith(userName)) {
                return true
            }
        }
        return false
    }

    fun getPasswordForUser(userName: String): String? {
        val lines = dbFile.readLines()
        for (line in lines) {
            if (line.startsWith(userName)) {
                return line.split(" ")[1]
            }
        }
        return null
    }
}