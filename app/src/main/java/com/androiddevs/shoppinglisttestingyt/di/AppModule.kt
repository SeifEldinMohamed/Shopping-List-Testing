package com.androiddevs.shoppinglisttestingyt.di

import android.content.Context
import androidx.room.Room
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingDao
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingDatabase
import com.androiddevs.shoppinglisttestingyt.data.remote.PixabayApi
import com.androiddevs.shoppinglisttestingyt.reposotories.DefaultShoppingRepository
import com.androiddevs.shoppinglisttestingyt.reposotories.ShoppingRepository
import com.androiddevs.shoppinglisttestingyt.utils.Constants.BASE_URL
import com.androiddevs.shoppinglisttestingyt.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class) // make sure that the life time of the dependencies we declare here in this module will be as long as our application lives
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository( // since we pass a ShoppingRepository Interface in our ViewModel Constructor dagger will look if we provide such an interface
        dao: ShoppingDao,
        api:PixabayApi
    ) = DefaultShoppingRepository(dao, api) as ShoppingRepository

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingDatabase
    ) = database.shoppingDao()

    // Retrofit
    @Singleton
    @Provides
    fun providePixabayApi(): PixabayApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(PixabayApi::class.java)
}