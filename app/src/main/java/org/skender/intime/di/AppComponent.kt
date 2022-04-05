package org.skender.intime.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.Component
import org.skender.intime.di.module.ApiModule
import org.skender.intime.di.module.AppModule
import org.skender.intime.feature.medical_data.domain.MedicalDataInteractor
import org.skender.intime.feature.medical_data.network.MedicalDataRepository
import org.skender.intime.network.ApiInterface
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {
    fun context(): Context
    fun inject(activity: AppCompatActivity)
    fun inject(fragment: Fragment)
    fun apiInterface(): ApiInterface
    fun getMedicalRepository(): MedicalDataRepository
    fun getMedicalInteractor(): MedicalDataInteractor
}