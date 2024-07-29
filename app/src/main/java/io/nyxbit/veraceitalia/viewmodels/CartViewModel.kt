package io.nyxbit.veraceitalia.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.nyxbit.veraceitalia.models.Item

class CartViewModel : ViewModel() {
    private var list = mutableListOf<Item>()
    private var subtotal = MutableLiveData<Int>().apply { value = 0 }

    fun add(item: Item) {
        list.add(item)
        updateSubtotal()
    }
    fun remove(item: Item) {
        list.remove(item)
        updateSubtotal()
    }
    fun clear() {
        list.clear()
        subtotal.value = 0
    }
    fun getList(): MutableList<Item> {
        return list
    }
    fun replace(oldItem: Item, newItem: Item) {
        val index = list.indexOf(oldItem)
        if (index != -1) {
            list[index] = newItem
        }
        updateSubtotal()
    }
    fun exists(item: Item): Boolean {
        return list.contains(item)
    }
    fun getSubtotal(): MutableLiveData<Int> {
        return subtotal
    }
    private fun updateSubtotal() {
        subtotal.value = list.sumOf { it.subtotal }
    }

}
