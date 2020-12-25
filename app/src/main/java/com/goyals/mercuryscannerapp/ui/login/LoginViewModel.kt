package com.goyals.mercuryscannerapp.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.goyals.mercuryscannerapp.arch.performNwOperation
import com.goyals.mercuryscannerapp.model.repo.LoginRepo

class LoginViewModel @ViewModelInject constructor(private val loginRepo: LoginRepo) :
  ViewModel() {
  fun loginUser(login: String,
    password: String) =
    performNwOperation { loginRepo.doLogin(login, password) }
}