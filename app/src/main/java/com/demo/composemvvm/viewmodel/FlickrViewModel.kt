package com.demo.composemvvm.viewmodel

import FlickrResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.composemvvm.data.FlickrRepository
import kotlinx.coroutines.launch

class FlickrViewModel : ViewModel() {
    private val repository = FlickrRepository()

    private val _photoItems = MutableLiveData<FlickrResponse?>()
    val photoItems: LiveData<FlickrResponse?> = _photoItems

    fun fetchCreditCards(tags: String?) {
        _photoItems.value = null
        viewModelScope.launch {
            try {
                val cards = repository.getPhotosItems(tags?.trim())
                _photoItems.value = cards
                Log.e("FetchCreditCard", _photoItems.value.toString())
            } catch (e: Exception) {
                // Handle error
                Log.e("FetchCreditCard", e.message.toString())
            }
        }
    }
}
