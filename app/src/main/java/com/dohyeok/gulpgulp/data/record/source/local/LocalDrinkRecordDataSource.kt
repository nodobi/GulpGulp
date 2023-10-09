package com.dohyeok.gulpgulp.data.record.source.local

import com.dohyeok.gulpgulp.data.record.DrinkRecord
import com.dohyeok.gulpgulp.data.record.source.DrinkRecordDataSource
import com.dohyeok.gulpgulp.di.module.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class LocalDrinkRecordDataSource @Inject constructor(
    private val drinkRecordsDao: DrinkRecordsDao,
    @IODispatcher private val ioContext: CoroutineDispatcher
): DrinkRecordDataSource {
    override suspend fun saveRecord(record: DrinkRecord) = withContext(ioContext){
        drinkRecordsDao.insertRecord(record)
    }

    override suspend fun deleteRecord(record: DrinkRecord) = withContext(ioContext) {
        drinkRecordsDao.deleteRecord(record)
    }

    override suspend fun updateRecordDrink(record: DrinkRecord) = withContext(ioContext){
        record.let {
            drinkRecordsDao.updateRecord(it.recordId, it.recordTime, it.drink.icon, it.drink.name, it.drink.amount)
        }
    }

    override suspend fun getAllRecords(): List<DrinkRecord> = withContext(ioContext) {
        drinkRecordsDao.selectAllRecords()
    }

    override suspend fun getRecordsWithDate(date: LocalDate): List<DrinkRecord> = withContext(ioContext) {
        drinkRecordsDao.selectRecords(date)
    }

    override suspend fun getDrinkAmountSum(date: LocalDate): Int = withContext(ioContext) {
        drinkRecordsDao.getDrinkAmountSum(date)
    }
}