package com.dohyeok.gulpgulp.util

import android.content.Context
import android.content.Intent
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.replaceFragment(
    @IdRes resId: Int, fragment: Fragment
) {
    supportFragmentManager.beginTransaction().replace(resId, fragment).commit()
}

fun AppCompatActivity.startActivityWithoutIntent(packageContext: Context, cls: Class<*>) {
    startActivity(Intent(packageContext, cls))
}