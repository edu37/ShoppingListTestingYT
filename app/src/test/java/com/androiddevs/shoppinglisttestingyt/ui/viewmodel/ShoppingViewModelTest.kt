package com.androiddevs.shoppinglisttestingyt.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.androiddevs.shoppinglisttestingyt.MainCoroutineRule
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValueTest
import com.androiddevs.shoppinglisttestingyt.other.Constants
import com.androiddevs.shoppinglisttestingyt.other.Status
import com.androiddevs.shoppinglisttestingyt.repositories.FakeShoppingRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShoppingViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setup() {
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with valid input, returns success`() {
        viewModel.validateToAddShoppingItem("name", "2", "30")

        val item = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        Truth.assertThat(item.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `insert shopping item with empty name, returns error`() {
        viewModel.validateToAddShoppingItem("", "2", "30")

        val item = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        Truth.assertThat(item.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with empty amount, returns error`() {
        viewModel.validateToAddShoppingItem("name", "", "30")

        val item = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        Truth.assertThat(item.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with empty price, returns error`() {
        viewModel.validateToAddShoppingItem("name", "2", "")

        val item = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        Truth.assertThat(item.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long name, returns error`() {
        // Constrói uma string que ultrapassa o valor máximo permitido
        val name = buildString { for (i in 1..Constants.MAX_NAME_LENGTH + 1) append(1) }

        viewModel.validateToAddShoppingItem(name, "2", "30")

        val item = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        Truth.assertThat(item.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long price, returns error`() {
        // Constrói uma string que ultrapassa o valor máximo permitido
        val price = buildString { for (i in 1..Constants.MAX_PRICE_LENGTH + 1) append(1) }

        viewModel.validateToAddShoppingItem("name", "2", price)

        val item = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        Truth.assertThat(item.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount, returns error`() {

        viewModel.validateToAddShoppingItem("name", "9999999999999999", "30")

        val item = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        Truth.assertThat(item.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }
}