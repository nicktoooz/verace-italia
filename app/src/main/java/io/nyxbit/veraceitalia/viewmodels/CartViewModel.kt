package io.nyxbit.veraceitalia.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.nyxbit.veraceitalia.models.Item
class CartViewModel : ViewModel() {
    private val _list = MutableLiveData<MutableList<Item>>().apply { value = mutableListOf() }
    val list: LiveData<MutableList<Item>> get() = _list

    private val _subtotal = MutableLiveData<Int>().apply { value = 0 }
    val subtotal: LiveData<Int> get() = _subtotal

    fun add(item: Item) {
        val items = _list.value ?: mutableListOf()
        val index = items.indexOfFirst { it.name == item.name }
        if (index >= 0) {
            val existingItem = items[index]
            existingItem.quantity += item.quantity
        } else {
            items.add(item)
        }
        _list.value = items
        updateSubtotal()
    }

    fun remove(item: Item) {
        val items = _list.value ?: mutableListOf()
        val index = items.indexOfFirst { it.name == item.name }
        if (index >= 0) {
            items[index].quantity = 0 // Reset quantity to 0 before removing
            items.removeAt(index)
            _list.value = items
            updateSubtotal()
        }
    }

    fun clear() {
        _list.value = mutableListOf()
        _subtotal.value = 0
    }

    fun replace(oldItem: Item, newItem: Item) {
        val items = _list.value ?: mutableListOf()
        val index = items.indexOfFirst { it.name == oldItem.name }
        if (index >= 0) {
            items[index] = newItem
            _list.value = items
        }
        updateSubtotal()
    }

    fun exists(item: Item): Boolean {
        return _list.value?.any { it.name == item.name } ?: false
    }

    private fun updateSubtotal() {
        _subtotal.value = _list.value?.sumOf { it.subtotal } ?: 0
    }
}
