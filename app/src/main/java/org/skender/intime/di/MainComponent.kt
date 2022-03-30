package org.skender.intime.di

import dagger.Component
import org.skender.intime.main.MainActivity
import org.skender.intime.feature.main.MainFragment
import org.skender.intime.feature.medical_data.MedicalDataFragment
import javax.inject.Scope

@PerActivity
@Component(dependencies = [AppComponent::class])
interface MainComponent {
    fun inject (mainActivity: MainActivity)
    fun inject (mainFragment: MainFragment)
    fun inject (medicalDataFragment: MedicalDataFragment)
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerActivity