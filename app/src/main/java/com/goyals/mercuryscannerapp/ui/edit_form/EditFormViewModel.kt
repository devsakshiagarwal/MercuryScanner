package com.goyals.mercuryscannerapp.ui.edit_form

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goyals.mercuryscannerapp.arch.performNwOperation
import com.goyals.mercuryscannerapp.model.repo.LoginRepo

class EditFormViewModel @ViewModelInject constructor(private val loginRepo: LoginRepo) :
  ViewModel() {

  val idTypeLiveData = MutableLiveData<String>()

  fun setIdType(idType: String) {
    idTypeLiveData.value = idType
  }

  fun loginUser(login: String,
    password: String) =
    performNwOperation { loginRepo.doLogin(login, password) }
}