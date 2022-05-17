package com.androiddevs.shoppinglisttestingyt.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name:String,
    var amount: Int,
    var price: Float,
    var imageUrl: String
)