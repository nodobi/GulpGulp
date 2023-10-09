package com.dohyeok.gulpgulp.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

val LocalDate.toTimeStamp: String
    get() = this.format(DateTimeFormatter.ISO_LOCAL_DATE)


val String.toLocalDate: LocalDate
    get() = LocalDate.parse(this)
