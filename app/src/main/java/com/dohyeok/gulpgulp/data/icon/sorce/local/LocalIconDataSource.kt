package com.dohyeok.gulpgulp.data.icon.sorce.local

import com.dohyeok.gulpgulp.data.icon.Icon
import com.dohyeok.gulpgulp.data.icon.sorce.IconDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalIconDataSource @Inject constructor(
    var iconDao: IconDao, var ioDispatcher: CoroutineDispatcher
) : IconDataSource {

    override suspend fun addIcon(icon: Icon) = withContext(ioDispatcher) {
        iconDao.insertIcon(icon)
    }

    override suspend fun addIcon(iconName: String) = withContext(ioDispatcher) {
        // Repository 캐시에서 반환된다.
    }

    override suspend fun getIcon(iconName: String): Icon = withContext(ioDispatcher) {
        iconDao.selectIcon(iconName)
    }

    override suspend fun getIconResId(iconName: String): Int = withContext(ioDispatcher) {
        // Repository 캐시에서 반환된다.
        0
    }

    override suspend fun getAllIcons(): Map<String, Icon> = withContext(ioDispatcher) {
        iconDao.selectAllIcons().map { it.name to it }.toMap()
    }

    override suspend fun getAllIconsResId(): Map<String, Int> = withContext(ioDispatcher) {
        // Repository 캐시에서 반환된다.
        mapOf()
    }

    override suspend fun getAllIconsName(): List<String> = withContext(ioDispatcher) {
        // Repository 캐시에서 반환된다.
        listOf()
    }

    override suspend fun updateResId(iconName: String, resId: Int) = withContext(ioDispatcher) {
        iconDao.updateResId(iconName, resId)
    }
}