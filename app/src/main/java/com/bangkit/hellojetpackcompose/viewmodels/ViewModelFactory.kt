package com.bangkit.hellojetpackcompose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.hellojetpackcompose.repo.AppRepository

class ViewModelFactory(
    private val repository: AppRepository,
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}