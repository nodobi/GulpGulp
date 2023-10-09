package com.dohyeok.gulpgulp.data.drink

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "drink_items")
data class DrinkItem(
    @ColumnInfo("icon") var icon: String,
    @ColumnInfo("name") var name: String,
    @ColumnInfo("amount") var amount: Int,
    @PrimaryKey
    @ColumnInfo("id") var id: String = UUID.randomUUID().toString(),
) {
    @ColumnInfo("list_order") var order: Int = -1
}