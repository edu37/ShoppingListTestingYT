package com.androiddevs.shoppinglisttestingyt.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingItem
import com.androiddevs.shoppinglisttestingyt.data.remote.responses.ImageResponse
import com.androiddevs.shoppinglisttestingyt.other.Resource

class FakeShoppingRepository: ShoppingRepository {

    private val listOfShoppingItems = mutableListOf<ShoppingItem>()

    private val observeAllShoppingItems = MutableLiveData<List<ShoppingItem>>(listOfShoppingItems)
    private val observeTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShoudReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observeAllShoppingItems.postValue(listOfShoppingItems)
        observeTotalPrice.postValue(listOfShoppingItems.sumOf { it.price.toDouble() }.toFloat())
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        listOfShoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        listOfShoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observeAllShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observeTotalPrice
    }

    override suspend fun searchImage(imageQuery: String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError) {
            return Resource.error("Error", null)
        } else {
            Resource.success(ImageResponse(emptyList(), 0, 0))
        }
    }
}