package com.aslam.samplegallery.di

import com.aslam.samplegallery.data.ErrorDescription


sealed class Result<T> {

    class Loading<T>(var showLoader: Boolean=false):Result<T>()
    class Success<T>(var data: T) : Result<T>()
    class Failure<E>(val e: ErrorDescription?) : Result<E>()
}