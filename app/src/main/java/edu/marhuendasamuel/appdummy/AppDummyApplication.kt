package edu.marhuendasamuel.appdummy

import android.app.Application
import edu.marhuendasamuel.appdummy.data.di.AppContainer
import edu.marhuendasamuel.appdummy.data.di.DefaultAppContainer

class AppDummyApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}