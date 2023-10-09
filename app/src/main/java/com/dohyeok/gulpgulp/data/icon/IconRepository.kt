package com.dohyeok.gulpgulp.data.icon

import android.annotation.SuppressLint
import android.content.Context
import com.dohyeok.gulpgulp.data.icon.sorce.IconDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


// TODO("완성은 했는데, 아이콘이 존재하지 않는다거나, 에러 체크하는 반환값이 필요할것같음
//  리턴 아이콘이 없는 경우, null 값을 리턴하기 보다는 X 아이콘을 반환하는거로!")
class IconRepository @Inject constructor(
    private var context: Context,
    private var localIconDataSource: IconDataSource
) : IconDataSource {
    private lateinit var notFoundIcon: Icon

    init {
        CoroutineScope(Dispatchers.IO).launch {
            notFoundIcon = getIcon("ic_close_24dp")
        }
    }

    // key: IconName, value: Icon Resource Id
//    private val cachedDrinkIconsLegeacy: LinkedHashMap<String, Int> = LinkedHashMap()

    // 만약 캐시가 Map<String, Icon> 이라면? -> 나중에 Icon 안에 추가적인 데이터가 들어가도 상관없다!
    // key: IconName, value: Icon
    private val cachedDrinkIcons: HashMap<String, Icon> = HashMap()

    override suspend fun addIcon(icon: Icon) {
        if(cachedDrinkIcons.isEmpty()) {
            refreshIcons()
        }
        val cachedIcon = cacheDrinkIcon(icon)
        localIconDataSource.addIcon(cachedIcon)
    }

    override suspend fun addIcon(iconName: String) {
        if(cachedDrinkIcons.isEmpty()) {
            refreshIcons()
        }
        val cachedIcon = cacheDrinkIcon(Icon(iconName, findIconResId(iconName)))
        localIconDataSource.addIcon(cachedIcon)
    }

    override suspend fun getIcon(iconName: String): Icon {
        if (cachedDrinkIcons.isEmpty()) {
            refreshIcons()
        }
        return cachedDrinkIcons[iconName] ?: notFoundIcon
    }

    // TODO("찾지 못했을 때 Return 값을 임시로 -1 을 반환하도록 변경하였음.")
    override suspend fun getIconResId(iconName: String): Int {
        if (cachedDrinkIcons.isEmpty()) {
            refreshIcons()
        }
        return cachedDrinkIcons.get(iconName)?.resId ?: notFoundIcon.resId
    }

    override suspend fun getAllIcons(): Map<String, Icon> {
        if(cachedDrinkIcons.isEmpty()){
            refreshIcons()
        }
        return cachedDrinkIcons
    }

    override suspend fun getAllIconsResId(): Map<String, Int> {
        if (cachedDrinkIcons.isEmpty()) {
            refreshIcons()
        }
        return cachedDrinkIcons.mapValues { it.value.resId }
    }

    override suspend fun getAllIconsName(): List<String> {
        if (cachedDrinkIcons.isEmpty()) {
            refreshIcons()
        }
        return cachedDrinkIcons.keys.toList()
    }

    override suspend fun updateResId(iconName: String, resId: Int) {
        if(!cachedDrinkIcons.containsKey(iconName)) {
            refreshIcons()
        }

        cachedDrinkIcons[iconName].apply {
            this?.resId = resId
        }?.let {
            cacheDrinkIcon(it)
            localIconDataSource.updateResId(iconName, resId)
        }
    }

    private suspend fun refreshIcons() {
        cachedDrinkIcons.clear()
        localIconDataSource.getAllIcons().forEach { (name, icon) ->
            val newResId = findIconResId(name)
            if(newResId != icon.resId) {
                localIconDataSource.updateResId(name, newResId)
                cacheDrinkIcon(icon.apply { resId = newResId })
            } else {
                cacheDrinkIcon(icon)
            }
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun findIconResId(iconName: String): Int =
        context.resources.getIdentifier(iconName, "drawable", context.packageName)


    private fun cacheDrinkIcon(icon: Icon): Icon {
        icon.let {
            cachedDrinkIcons.put(it.name, it)
        }
        return icon
    }
}