package com.dohyeok.gulpgulp.data.record.source

import com.dohyeok.gulpgulp.data.record.DrinkRecord
import java.time.LocalDate

interface DrinkRecordDataSource {
    suspend fun saveRecord(record: DrinkRecord)
    suspend fun deleteRecord(record: DrinkRecord)
    suspend fun getAllRecords(): List<DrinkRecord>
    suspend fun getRecordsWithDate(date: LocalDate): List<DrinkRecord>

    suspend fun getDrinkAmountSum(date: LocalDate): Int
    suspend fun updateRecordDrink(record: DrinkRecord)
}