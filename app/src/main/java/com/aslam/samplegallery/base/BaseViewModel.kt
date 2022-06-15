package com.aslam.samplegallery.base

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job



abstract class BaseViewModel : ViewModel() {
  companion  object {
      var job = Job()
  }
    var outcomeLiveData = MediatorLiveData<Result<*>>()

    // Cancel the job when the view model is destroyed
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}