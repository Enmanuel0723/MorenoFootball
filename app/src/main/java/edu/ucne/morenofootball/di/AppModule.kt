package edu.ucne.morenofootball.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.morenofootball.data.MorenoFootballDb
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun providesMorenoFootballDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            MorenoFootballDb::class.java,
            "MorenoFootball.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideUsuarioDao(db: MorenoFootballDb) = db.usuarioDao()
}