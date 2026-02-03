package com.example.vehiclecompanion.database.di

import android.content.Context
import androidx.room.Room
import com.example.vehiclecompanion.database.VehicleCompanionDatabase
import com.example.vehiclecompanion.database.dao.FavoritesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val DATABASE_NAME = "vehicle_companion_db"

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        context = app,
        klass = VehicleCompanionDatabase::class.java,
        name = DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideVehicleDao(database: VehicleCompanionDatabase) = database.getVehicleDao()

    @Provides
    fun provideFavoritesDao(database: VehicleCompanionDatabase): FavoritesDao =
        database.getFavoritesDao()
}