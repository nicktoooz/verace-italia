package io.nyxbit.veraceitalia.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.nyxbit.veraceitalia.models.Item
class SelectionViewModel : ViewModel() {
    private val _list = MutableLiveData<MutableList<Item>>().apply { value = mutableListOf() }
    val list: LiveData<MutableList<Item>> get() = _list

    private val _subtotal = MutableLiveData<Float>().apply { value = 0f }
    val subtotal: LiveData<Float> get() = _subtotal

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
            items[index].quantity = 0
            items.removeAt(index)
            _list.value = items
            updateSubtotal()
        }
    }

    fun clear() {
        _list.value = mutableListOf()
        _subtotal.value = 0f
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
        val items = _list.value ?: mutableListOf()
        val subtotal = items.fold(0f) { acc, item -> acc + item.subtotal }
        _subtotal.value = subtotal
    }

}
