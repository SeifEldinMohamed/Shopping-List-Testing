package com.androiddevs.shoppinglisttestingyt.reposotories

import androidx.lifecycle.LiveData
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingDao
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingEntity
import com.androiddevs.shoppinglisttestingyt.data.remote.PixabayApi
import com.androiddevs.shoppinglisttestingyt.data.remote.responses.ApiResponse
import com.androiddevs.shoppinglisttestingyt.utils.Resource
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayApi: PixabayApi
) : ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingEntity) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingEntity) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingEntity>> {
        return shoppingDao.getShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.getTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ApiResponse> {
        return try {
            val response = pixabayApi.searchForImages(imageQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unkwon error occured", null)
            } else {
                Resource.error("An unkwon error occured", null)
            }
        } catch (e: Exception) {
            Resource.error("couldn't reach the server, check your internet connection", null)
        }
    }
}