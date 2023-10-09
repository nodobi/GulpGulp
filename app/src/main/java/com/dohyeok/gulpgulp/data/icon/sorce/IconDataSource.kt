package com.dohyeok.gulpgulp.data.icon.sorce

import com.dohyeok.gulpgulp.data.icon.Icon

interface IconDataSource {
    suspend fun addIcon(icon: Icon)
    suspend fun addIcon(iconName: String)
    suspend fun getIcon(iconName: String): Icon
    suspend fun getIconResId(iconName: String): Int
    suspend fun getAllIcons(): Map<String, Icon>
    suspend fun getAllIconsResId(): Map<String, Int>
    suspend fun getAllIconsName(): List<String>
    suspend fun updateResId(iconName: String, resId: Int)
}