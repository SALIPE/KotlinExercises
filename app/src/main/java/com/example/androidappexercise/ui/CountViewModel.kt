package com.example.androidappexercise.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.androidappexercise.ui.models.CounterRepository

class CountViewModel: ViewModel() {

    private val _repository: CounterRepository = CounterRepository()
    private val _count = mutableStateOf<Int>(_repository.getCounter().count)

    val count: MutableState<Int> = _count

    fun increment(){
        _repository.incrementCounter()
        _count.value = _repository.getCounter().count
    }

    fun decrement(){
        _repository.decrementCounter()
        _count.value = _repository.getCounter().count
    }
}