package org.skender.intime.di.module

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.skender.intime.feature.medical_data.MedicalDataViewModel
import org.skender.intime.feature.medical_data.domain.MedicalDataInteractor
import org.skender.intime.feature.medical_data.network.MedicalDataRepository
import org.skender.intime.network.ApiInterface
import javax.inject.Singleton

@Module
class AppModule (private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }

    @Provides
    fun provideMedicalDataRepository(api: ApiInterface): MedicalDataRepository {
        return MedicalDataRepository(api)
    }

    @Provides
    fun provideMedicalDataInteractor(repository: MedicalDataRepository): MedicalDataInteractor {
        return MedicalDataInteractor(repository)
    }
}