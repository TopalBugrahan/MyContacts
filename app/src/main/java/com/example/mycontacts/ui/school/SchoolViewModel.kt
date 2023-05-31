package com.example.mycontacts.ui.school

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SchoolViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is school Fragment"
    }
    val text: LiveData<String> = _text
}