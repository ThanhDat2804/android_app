package com.example.project1842.ViewModel

import androidx.lifecycle.ViewModel
import com.example.project1842.Repository.MainRepository

class MainViewModel(val respository: MainRepository) : ViewModel() {

    constructor() : this(MainRepository())

    fun loadLocation() = respository.location

    fun loadCategory() = respository.category

    fun loadData() = respository.items
}