package nebur.myapp2.di.forMenu

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import nebur.menu.DishesFromApp
import nebur.myapp2.impl.forMenu.DishesForMenu

@Module
@InstallIn(ViewModelComponent::class)
abstract class DishesForMenuModule {

    @Binds
    abstract fun bindDishesFromApp(
        dishesForMenu: DishesForMenu
    ): DishesFromApp

}