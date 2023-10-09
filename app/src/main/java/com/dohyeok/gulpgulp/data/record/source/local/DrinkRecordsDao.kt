package com.dohyeok.gulpgulp.data.record.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dohyeok.gulpgulp.data.drink.DrinkItem
import com.dohyeok.gulpgulp.data.record.DrinkRecord
import java.time.LocalDate
import java.time.LocalTime

@Dao
interface DrinkRecordsDao {
    @Insert
    fun insertRecord(record: DrinkRecord)

    @Delete
    fun deleteRecord(record: DrinkRecord)

    @Query("SELECT * FROM drink_record")
    fun selectAllRecords(): List<DrinkRecord>

    @Query("SELECT * FROM drink_record WHERE record_date = :date")
    fun selectRecords(date: LocalDate): List<DrinkRecord>

    @Query("SELECT SUM(drink_amount) FROM drink_record WHERE record_date = :date")
    fun getDrinkAmountSum(date: LocalDate): Int

    @Query("UPDATE drink_record SET " +
        "record_time = :recordTime," +
        "drink_icon = :icon," +
        "drink_name = :name," +
        "drink_amount = :amount " +
        "WHERE record_id = :recordId")
    fun updateRecord(recordId: String, recordTime: LocalTime, icon: String, name: String, amount: Int)
}