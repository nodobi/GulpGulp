package com.dohyeok.gulpgulp.util

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.replaceFragment(
    @IdRes resId: Int, fragment: Fragment
) {
    supportFragmentManager.beginTransaction().replace(resId, fragment).commit()
}