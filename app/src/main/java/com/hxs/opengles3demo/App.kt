package com.hxs.opengles3demo

import android.app.Application
import android.content.Context

class App : Application() {


    companion object {
        lateinit var context: Context
        fun resource() = context.resources
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}