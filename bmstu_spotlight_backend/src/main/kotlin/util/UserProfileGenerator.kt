package com.thread_sleepers.util

import com.thread_sleepers.models.response.UserProfileDto

object UserProfileGenerator {
    fun generate(): UserProfileDto {
        return UserProfileDto(
            name = "Mike Wazowski",
            email = "mikewazowski@bmstu.ru",
            avatarUrl = "https://static.wikia.nocookie.net/disney/images/c/c5/%D0%9C%D0%B0%D0%B9%D0%BA.png/revision/latest?cb=20151025165018&path-prefix=ru",
            accountType = "BMSTU Account",
            language = "English"
        )
    }
}