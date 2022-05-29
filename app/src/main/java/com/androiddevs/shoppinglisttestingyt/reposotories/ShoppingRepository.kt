package com.androiddevs.shoppinglisttestingyt.reposotories

import androidx.lifecycle.LiveData
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingEntity
import com.androiddevs.shoppinglisttestingyt.data.remote.responses.ApiResponse
import com.androiddevs.shoppinglisttestingyt.utils.Resource
import retrofit2.Response

interface ShoppingRepository {
    suspend fun insertShoppingItem(shoppingItem: ShoppingEntity)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingEntity)

    fun observeAllShoppingItems(): LiveData<List<ShoppingEntity>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ApiResponse>
}