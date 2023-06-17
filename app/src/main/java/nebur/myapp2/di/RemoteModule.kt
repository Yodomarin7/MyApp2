package nebur.myapp2.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nebur.data.source.remote.DishesRemote

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideDishesRemote(): DishesRemote {
        return DishesRemote()
    }

}