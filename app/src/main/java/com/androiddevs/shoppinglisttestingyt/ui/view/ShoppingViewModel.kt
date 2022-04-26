package com.androiddevs.shoppinglisttestingyt.ui.view

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingItem
import com.androiddevs.shoppinglisttestingyt.data.remote.responses.ImageResponse
import com.androiddevs.shoppinglisttestingyt.other.Event
import com.androiddevs.shoppinglisttestingyt.other.Resource
import com.androiddevs.shoppinglisttestingyt.repositories.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository
) : ViewModel() {

    private val mShoppingItems = repository.observeAllShoppingItems()

    private val mTotalPrice = repository.observeAllShoppingItems()

    private val mImage = MutableLiveData<Event<Resource<ImageResponse>>>()
    val image: LiveData<Event<Resource<ImageResponse>>> = mImage

    private val mImageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> = mImageUrl

    private val mInsertShoppingItem = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItem: LiveData<Event<Resource<ShoppingItem>>> = mInsertShoppingItem

    fun setImageUrl(url: String) {
        mImageUrl.postValue(url)
    }

    fun insertShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun validateToAddShoppingItem(name: String, amountString: String, priceString: String) {

    }

    fun searchImage(imageQuery: String) {

    }
}



