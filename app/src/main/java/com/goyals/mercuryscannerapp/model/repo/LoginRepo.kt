package com.goyals.mercuryscannerapp.model.repo

import com.goyals.mercuryscannerapp.arch.BaseDataSource
import com.goyals.mercuryscannerapp.model.api.LoginApi
import javax.inject.Inject

class LoginRepo @Inject constructor(private val loginApi: LoginApi) :
  BaseDataSource() {
  suspend fun doLogin(user: String,
    password: String) = getResult { loginApi.doLogin(user, password) }
}