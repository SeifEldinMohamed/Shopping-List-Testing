package com.androiddevs.shoppinglisttestingyt.ui

import com.androiddevs.shoppinglisttestingyt.getOrAwaitValueTest
import com.androiddevs.shoppinglisttestingyt.repositories.FakeShoppingRepository
import com.androiddevs.shoppinglisttestingyt.utils.Status
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ShoppingViewModelTest{
    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setup(){
        viewModel= ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty field returns error` (){
        viewModel.insertShoppingItem("name", "","3.0")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }
}