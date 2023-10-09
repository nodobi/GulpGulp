package com.dohyeok.gulpgulp.view.base

interface BaseContract {
    interface View {

    }
    interface Presenter<T: View> {
        var _view: T?
        val view get() = _view!!
        fun attachView(view: T) {
            _view = view
        }
        fun detachView() {
            _view = null
        }
    }
}