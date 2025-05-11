package com.thread_sleepers.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    @SerialName("user_name")
    val userName: String,
    val password: String
)