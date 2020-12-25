package com.goyals.mercuryscannerapp.ui.edit_form

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goyals.mercuryscannerapp.arch.performNwOperation
import com.goyals.mercuryscannerapp.model.repo.AadharRepo
import com.goyals.mercuryscannerapp.model.schema.AadharRequest

class EditFormViewModel @ViewModelInject constructor(private val aadharRepo: AadharRepo) :
  ViewModel() {
  val idTypeLiveData = MutableLiveData<String>()
  val genderLiveData = MutableLiveData<String>()
  val locationLiveData = MutableLiveData<String>()

  fun setIdType(idType: String) {
    idTypeLiveData.value = idType
  }

  fun setGender(gender: String) {
    genderLiveData.value = gender
  }

  fun setLocation(gender: String) {
    locationLiveData.value = gender
  }

  fun createUser(aadharRequest: AadharRequest) =
    performNwOperation { aadharRepo.postUser(aadharRequest) }
}