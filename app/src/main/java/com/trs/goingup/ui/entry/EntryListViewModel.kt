package com.trs.goingup.ui.entry

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trs.goingup.data.EntryDetail

class EntryListViewModel : ViewModel() {

    private val _entries = MutableLiveData<List<EntryDetail>>().apply {
        value = emptyList()
    }
    val entries: MutableLiveData<List<EntryDetail>> = _entries
}