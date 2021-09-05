package com.gsoft.interconsulta.di

import com.google.firebase.firestore.FirebaseFirestore
import com.gsoft.interconsulta.data.repository.PatientsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainActivityModule {

    @Provides
    @Singleton
    fun provideRepo(firestore: FirebaseFirestore) = PatientsRepository(firestore)

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }


}
