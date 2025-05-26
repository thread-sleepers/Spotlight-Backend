package com.thread_sleepers.models.response

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDto(
    val name: String,
    val email: String,
    val avatarUrl: String,
    val accountType: String,
    val language: String
)