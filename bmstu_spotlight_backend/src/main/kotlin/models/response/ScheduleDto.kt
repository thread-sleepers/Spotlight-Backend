package com.thread_sleepers.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleResponse(
    val items: List<ScheduleDto>
)

@Serializable
data class ScheduleDto(
    val title: String,
    val type: String?,
    val place: String,
    @SerialName("start_time")
    val startTime: String,
    @SerialName("end_time")
    val endTime: String,
)

