package org.skender.intime.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.skender.intime.App
import org.skender.intime.R
import org.skender.intime.di.HasComponent
import org.skender.intime.di.DaggerMainComponent
import org.skender.intime.di.MainComponent

class MainActivity : AppCompatActivity(R.layout.activity_main), HasComponent<MainComponent> {

    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = DaggerMainComponent
            .builder()
            .appComponent((application as App).getAppComponent())
            .build()
        mainComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    private lateinit var mainComponent: MainComponent

    override fun getComponent(): MainComponent = mainComponent
}