package com.androiddevs.shoppinglisttestingyt.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingEntity
import com.androiddevs.shoppinglisttestingyt.data.remote.responses.ApiResponse
import com.androiddevs.shoppinglisttestingyt.reposotories.ShoppingRepository
import com.androiddevs.shoppinglisttestingyt.utils.Resource

class FakeShoppingRepository: ShoppingRepository { // make this class to test viewModel

    private val shoppingItems = mutableListOf<ShoppingEntity>()
    private val observeShoppingItem = MutableLiveData<List<ShoppingEntity>>(shoppingItems)
    private val observeTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError :Boolean = false

    fun setShouldReturnNetworkError(value:Boolean){
        shouldReturnNetworkError = value
    }

    private fun refreshLifeData(){
        observeShoppingItem.postValue(shoppingItems)
        observeTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float {
        return shoppingItems.sumByDouble { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingEntity) {
            shoppingItems.add(shoppingItem)
        refreshLifeData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingEntity) {
        shoppingItems.remove(shoppingItem)
        refreshLifeData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingEntity>> {
        return observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observeTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ApiResponse> {
        return if(shouldReturnNetworkError){
            Resource.error("Error",null)
        }
        else{
            Resource.success(ApiResponse(listOf(), 0,0))
        }
    }


}