package com.thread_sleepers.util

import com.thread_sleepers.models.response.ScheduleDto

object ScheduleGenerator {
    fun generate(): List<ScheduleDto> {
        return listOf(
            ScheduleDto(
                title = "Иностранный язык",
                type = "Семинар",
                place = "389",
                startTime = "8:30",
                endTime = "10:00"
            ),
            ScheduleDto(
                title = "Математичесиий анализ",
                type = "Лекция",
                place = "384",
                startTime = "10:10",
                endTime = "11:40"
            ),
            ScheduleDto(
                title = "Обед",
                type = null,
                place = "Столовая",
                startTime = "11:40",
                endTime = "12:25"
            ),
            ScheduleDto(
                title = "История",
                type = "Лекция",
                place = "404",
                startTime = "12:25",
                endTime = "13:55"
            ),
            ScheduleDto(
                title = "Физическая культура",
                type = null,
                place = "Спортзал",
                startTime = "14:05",
                endTime = "15:35"
            )
        )
    }
}