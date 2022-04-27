package com.androiddevs.shoppinglisttestingyt.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingItem
import com.androiddevs.shoppinglisttestingyt.data.remote.responses.ImageResponse
import com.androiddevs.shoppinglisttestingyt.other.Constants.MAX_NAME_LENGTH
import com.androiddevs.shoppinglisttestingyt.other.Constants.MAX_PRICE_LENGTH
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

    private val mInsertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> =
        mInsertShoppingItemStatus

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
        if (name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()) {
            mInsertShoppingItemStatus.postValue(Event(Resource.error("Todos os campos devem " +
                    "ser preenchidos", null)))
            return
        }
        if (name.length > MAX_NAME_LENGTH) {
            mInsertShoppingItemStatus.postValue(Event(Resource.error("Nome não pode" +
                    "ultrapassar $MAX_NAME_LENGTH caracteres",null)))
            return
        }
        if (priceString.length > MAX_PRICE_LENGTH) {
            mInsertShoppingItemStatus.postValue(Event(Resource.error("Preço não pode" +
                    "ultrapassar $MAX_PRICE_LENGTH dígitos", null)))
            return
        }

        val amount = try {
            amountString.toInt()
        } catch (e: Exception) {
            mInsertShoppingItemStatus.postValue(Event(Resource.error("Digite uma quantia " +
                    "válida", null)))
            return
        }
        val shoppingItem = ShoppingItem(name, amount, priceString.toFloat(), mImageUrl.value ?: "")
        mInsertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
        insertShoppingItem(shoppingItem)
        setImageUrl("")
    }

    fun searchImage(imageQuery: String) {
        if (imageQuery.isEmpty()) {
            return
        }
        mImage.value = Event(Resource.loading(null))

        viewModelScope.launch {
            val response = repository.searchImage(imageQuery)
            mImage.value = Event(response)
        }
    }
}



