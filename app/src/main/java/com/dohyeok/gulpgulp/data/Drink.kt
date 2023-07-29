package com.dohyeok.gulpgulp.data

import androidx.annotation.IdRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "drinks")
class Drink constructor(
    @ColumnInfo(name = "iconres") @IdRes var iconId: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "amount") var amount: Int
) {
    @ColumnInfo(name = "order") var order: Int = 0

    @ColumnInfo(name = "id") @PrimaryKey
    var id: String = UUID.randomUUID().toString()
}