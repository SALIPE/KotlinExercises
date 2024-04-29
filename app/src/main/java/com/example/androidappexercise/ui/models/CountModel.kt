package com.example.androidappexercise.ui.models

data class CountModel(
    var count:Int
)

class CounterRepository{
    private var _counter = CountModel(0)

    fun getCounter() = _counter

    fun incrementCounter(){
        _counter.count++
    }

    fun decrementCounter(){
        _counter.count--
    }
}
