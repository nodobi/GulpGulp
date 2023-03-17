package com.dohyeok.gulpgulp.util

import java.time.LocalDate
import java.time.LocalTime

fun String.padZero(length: Int = 2) = padStart(length, '0')
fun Int.padZero() = toString().padZero()

val LocalDate.yearMonthDateKrFormat: String
    get() = "${year}년 ${monthValue.padZero()}월 ${dayOfMonth.padZero()}일"

val LocalDate.yearMonthKrFormat: String
    get() = "${year}년 ${monthValue.padZero()}월"

val LocalDate.monthDayKrFormat: String
    get() = "${monthValue}월 ${dayOfMonth}일"

val LocalDate.yearMonthDateFormat: String
    get() = "${year}-${monthValue.padZero()}-${dayOfMonth.padZero()}"

val LocalDate.yearMonthFormat: String
    get() = "${year}-${monthValue.padZero()}"

val LocalDate.monthDayFormat: String
    get() = "${monthValue}-${dayOfMonth.padZero()}"


val LocalDate.toDatabaseFormat: String
    get() = toString()

val LocalTime.toDatabaseFormat: String
    get() = withNano(0).toString()

val String.toLocalDateFormat: LocalDate
    get() = LocalDate.parse(this)

val String.toLocalTimeFormat: LocalTime
    get() = LocalTime.parse(this)