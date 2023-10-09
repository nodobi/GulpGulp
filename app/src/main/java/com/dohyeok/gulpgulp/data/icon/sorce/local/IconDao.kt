package com.dohyeok.gulpgulp.data.icon.sorce.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dohyeok.gulpgulp.data.icon.Icon

@Dao
interface IconDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIcon(icon: Icon)

    @Query("SELECT * FROM icon_table WHERE icon_name = :iconName")
    fun selectIcon(iconName: String): Icon

    @Query("SELECT * FROM icon_table")
    fun selectAllIcons(): List<Icon>

    @Query("UPDATE icon_table SET icon_resId = :resId WHERE icon_name = :iconName")
    fun updateResId(iconName: String, resId: Int)
}