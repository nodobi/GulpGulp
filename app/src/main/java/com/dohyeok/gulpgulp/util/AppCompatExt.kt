package com.dohyeok.gulpgulp.util

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.startActivityExt(currentContext: Context, target: Class<*>) {
    startActivity(Intent(currentContext, target))
}

fun AppCompatActivity.replaceFragment(containerResId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(containerResId, fragment).commit()
}