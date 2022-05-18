package com.androiddevs.shoppinglisttestingyt.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingEntity)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingEntity)

    @Query("SELECT * FROM shopping_items")
    fun getShoppingItems(): LiveData<List<ShoppingEntity>>

    @Query("SELECT SUM(price * amount) FROM shopping_items")
    fun getTotalPrice(): LiveData<Float>
}