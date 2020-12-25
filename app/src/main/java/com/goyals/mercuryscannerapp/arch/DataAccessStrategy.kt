package com.goyals.mercuryscannerapp.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.goyals.mercuryscannerapp.arch.Result.Status.ERROR
import com.goyals.mercuryscannerapp.arch.Result.Status.SUCCESS
import kotlinx.coroutines.Dispatchers

fun <A> performNwOperation(networkCall: suspend () -> Result<A>): LiveData<Result<A>> =
  liveData(Dispatchers.IO) {
    emit(Result.loading())
    val responseStatus = networkCall.invoke()
    if (responseStatus.status == SUCCESS) {
      emit(Result.success(responseStatus.data!!))
    } else if (responseStatus.status == ERROR) {
      emit(Result.error(responseStatus.message!!))
    }
  }
