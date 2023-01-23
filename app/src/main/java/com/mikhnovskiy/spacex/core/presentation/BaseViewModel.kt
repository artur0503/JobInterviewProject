package com.mikhnovskiy.spacex.core.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    protected val subjectList = mutableListOf<Disposable>()
    protected val failureMutableLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val failureLiveData: LiveData<Throwable>
        get() = failureMutableLiveData

    override fun onCleared() {
        super.onCleared()
        subjectList.forEach {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
        subjectList.clear()
    }

}