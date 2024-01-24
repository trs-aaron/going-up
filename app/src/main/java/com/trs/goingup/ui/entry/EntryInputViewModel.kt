package com.trs.goingup.ui.entry

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EntryInputViewModel : ViewModel() {

    private val _inputVal = MutableLiveData<String>().apply {
        value = ""
    }
    val inputVal: MutableLiveData<String> = _inputVal
}