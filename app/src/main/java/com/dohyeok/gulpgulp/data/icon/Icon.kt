package com.dohyeok.gulpgulp.data.icon

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.*

/*
 * 아이콘이 같은 이름을 가질 수 있고, resId 는 계속 변경되기 때문에
 * 고유한 id를 만들어주기 위해 UUID 를 사용함
 */
@Entity(tableName = "icon_table", primaryKeys = ["icon_name", "icon_resId"])
data class Icon(
    @ColumnInfo("icon_name") var name: String,
    @ColumnInfo("icon_resId") var resId: Int,
)