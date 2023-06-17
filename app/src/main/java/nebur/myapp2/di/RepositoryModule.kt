package nebur.myapp2.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nebur.data.repository.AllUserSaveREPO
import nebur.data.repository.DishesREPO
import nebur.data.source.local.dishes.IdishLocal
import nebur.data.source.remote.DishesRemote
import nebur.data.source.store.AllUserSaveStore

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideDishesREPO(dishesRemote: DishesRemote, idishLocal: IdishLocal): DishesREPO {
        return DishesREPO(dishesRemote, idishLocal)
    }

    @Provides
    fun provideAllUserSaveREPO(allUserSaveStore: AllUserSaveStore): AllUserSaveREPO {
        return AllUserSaveREPO(allUserSaveStore)
    }

}