package nebur.myapp2.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nebur.data.source.local.dishes.IdishLocal
import nebur.myapp2.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideIdishLocal(db: AppDatabase): IdishLocal {
        return db.idishLocal()
    }

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "myapp_database"
        ).build()
    }
}