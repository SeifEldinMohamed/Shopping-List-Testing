package com.androiddevs.shoppinglisttestingyt.local.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingDao
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingDatabase
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)  // we make sure that all tests inside this class will run on the emulator and tell jUnit that this is instrumented tests
@SmallTest // to tell junit that we write here unit tests
class ShoppingDaoTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        dao = database.shoppingDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingEntity(1, "name", 2, 2f, "url")
        dao.insertShoppingItem(shoppingItem)

        val allShoppingItems = dao.getShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).contains(shoppingItem)

    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingEntity(1, "name", 2, 2f, "url")
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItems = dao.getShoppingItems().getOrAwaitValue() // empty

        assertThat(allShoppingItems).doesNotContain(shoppingItem)

    }

    @Test
    fun getTotalPriceSum() = runBlockingTest {
        val shoppingItem1 = ShoppingEntity(1, "name", 2, 10f, "url")
        val shoppingItem2 = ShoppingEntity(2, "name", 4, 5.5f, "url")
        val shoppingItem3 = ShoppingEntity(3, "name", 0, 100f, "url")
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)


        val totalPriceSum = dao.getTotalPrice().getOrAwaitValue()

        assertThat(totalPriceSum).isEqualTo(2 * 10f + 4 * 5.5f)

    }

}

// @MediumTest -> Integrated test
// @LargeTest -> UI test

// the difference bet Room.inMemoryDatabaseBuilder() and Room.databaseBuilder() is that inMemoryDatabaseBuilder()
// in not a real database( save in RAM )

// allowMainThreadQueries() : we allow that we access this room database from the main thread.
// to make sure that all actions are executed one after another

// runBlocking : it's just a way to execute a coroutine in the main Thread, so that will actually block
// the main thread with suspend calls but we can execute suspend fun instead of that coroutine
//  runBlockingTest : optimize for test cases (will skip the delay fun to avoid delaying our test case).

// live data runs Asynchronous and that is a problem even we used getOrAwaitValue() fun so to solve
// this problem we need to tell junit to execute all teh code inside of this class one function after
// another in the same thread.

//     @Rule
//                -->  @get:Rule
//    @JvmField