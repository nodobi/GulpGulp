package com.dohyeok.gulpgulp.view.setting.contract

import com.dohyeok.gulpgulp.data.drink.DrinkRepository
import com.dohyeok.gulpgulp.data.goal.Goal
import com.dohyeok.gulpgulp.data.goal.GoalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SettingPresenter @Inject constructor(
    private val drinkRepository: DrinkRepository,
    private val goalRepository: GoalRepository,
    private val uiContext: CoroutineDispatcher
) : SettingContract.Presenter {
    override var _view: SettingContract.View? = null

    override var onGoalPickerCommit: (Int) -> Unit = { goalPickerCommitListener(it) }

    override fun loadGoalAmount() {
        CoroutineScope(uiContext).launch {
            val todayGoalAmount = goalRepository.getGoal(LocalDate.now(), true)!!.amount
            view.updateGoalAmount(todayGoalAmount)
        }
    }

    private fun goalPickerCommitListener(amount: Int) {
        // 저장소에 저장
        CoroutineScope(uiContext).launch {
            goalRepository.saveGoal(Goal(LocalDate.now(), amount))
            view.updateGoalAmount(amount)
        }
    }
}