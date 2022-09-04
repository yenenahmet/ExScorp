package com.example.exscorp.scenes

import DataSource
import Person
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exscorp.extension.checkNext
import com.example.exscorp.model.Response
import com.example.exscorp.model.Status
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {

    private val dataSource = DataSource()
    private val _liveDataPeople = MutableLiveData<Response<List<Person>>>()
    val liveDataPeople: LiveData<Response<List<Person>>>
        get() = _liveDataPeople

    private var pageNext: String = "1"
    var isItLoadingPeople = false


    fun getData() {
        if (!pageNext.checkNext()) {
            _liveDataPeople.value = Response(null, Status.ERROR, "An error occurred")
            return
        }
        viewModelScope.launch {
            try {
                dataSource.fetch(pageNext) { response, error ->
                    val errorMessage = error?.errorDescription
                    if (!errorMessage.isNullOrEmpty()) {
                        _liveDataPeople.value = Response(null, Status.ERROR, errorMessage)
                        return@fetch
                    }
                    val people = response?.people
                    if (people.isNullOrEmpty()) {
                        _liveDataPeople.value = Response(null, Status.ERROR, "Person not found!")
                        return@fetch
                    }
                    if (!response.next.checkNext()) {
                        _liveDataPeople.value = Response(null, Status.ERROR, "An error occurred")
                        return@fetch
                    }
                    _liveDataPeople.value = Response(response.people, Status.SUCCESS, "")
                    pageNext = response.next ?: "1"
                    isItLoadingPeople = false
                }
            } catch (ex: Exception) {
                _liveDataPeople.value = Response(null, Status.ERROR, "An error occurred")
            }
        }
    }

}