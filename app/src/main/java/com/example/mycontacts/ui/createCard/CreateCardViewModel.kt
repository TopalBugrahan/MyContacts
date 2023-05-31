package com.example.mycontacts.ui.createCard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateCardViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Create Fragment"
    }
    val text: LiveData<String> = _text
}