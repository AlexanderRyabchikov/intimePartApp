package org.skender.intime

import android.app.Application
import org.skender.intime.di.AppComponent
import org.skender.intime.di.DaggerAppComponent
import org.skender.intime.di.module.ApiModule
import org.skender.intime.di.module.AppModule

class App : Application() {

    private lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()

        initComponents()
    }

    private fun initComponents() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .apiModule(ApiModule("https://github.com/")) //Test data
            .build()
    }

    fun getAppComponent() :AppComponent = appComponent

}