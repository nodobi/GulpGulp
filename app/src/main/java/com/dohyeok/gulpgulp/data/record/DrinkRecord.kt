package com.dohyeok.gulpgulp.data.record

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dohyeok.gulpgulp.data.drink.DrinkItem
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

/*
 * https://stackoverflow.com/questions/44822133/clean-architecture-mvp-using-different-repositories-for-different-models-is-it
 *
 * DrinkRecord 가 Repository 를 따로 가지지 않는 이유
 * 1. DrinkRecord 가 사용되는 경우, 항상 Goal.isComplete 를 갱신해야 하기 때문에, DrinkRecord와 Goal은 함께 쓰인다.
 * 2. 따라서 DrinkRecordRepository 를 독립적으로 사용할 경우가 일반적으로 존재하지 않는다.
 *
 */
@Entity("drink_record")
data class DrinkRecord(
    @ColumnInfo("record_date") val recordDate: LocalDate,
    @ColumnInfo("record_time") var recordTime: LocalTime,
    @Embedded("drink_") var drink: DrinkItem,
    @PrimaryKey
    @ColumnInfo("record_id") var recordId: String = UUID.randomUUID().toString()
) {

}