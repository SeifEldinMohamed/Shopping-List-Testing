package com.androiddevs.shoppinglisttestingyt.di

import android.content.Context
import androidx.room.Room
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingDatabase
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import org.junit.runner.manipulation.Ordering
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
class TestAppModule {

    @Provides
    // we don't use @Singleton as we want to create new Instance for every test case
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context){
        Room.inMemoryDatabaseBuilder(context, ShoppingDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}