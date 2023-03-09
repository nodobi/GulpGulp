package com.dohyeok.gulpgulp.view.base

interface BaseContract {
    interface View {

    }
    interface Presenter<T: View> {
        var view: T
    }
}