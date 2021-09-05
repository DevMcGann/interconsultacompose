package com.gsoft.interconsulta

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AndroidApplication : Application() {

    companion object {

    }

    override fun onCreate() {
        super.onCreate()
    }
}