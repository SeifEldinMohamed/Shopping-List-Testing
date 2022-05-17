package com.androiddevs.shoppinglisttestingyt.data.local

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingEntity)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingEntity)

    @Query("SELECT * FROM shopping_items")
    fun getShoppingItems(): LiveData<List<ShoppingEntity>>

    @Query("SELECT SUM(price * amount) FROM shopping_items")
    fun getTotalPrice(): LiveData<List<ShoppingEntity>>
}