package com.goyals.mercuryscannerapp.model.repo

import com.goyals.mercuryscannerapp.arch.BaseDataSource
import com.goyals.mercuryscannerapp.model.api.AadharApi
import com.goyals.mercuryscannerapp.model.schema.AadharRequest
import javax.inject.Inject

class AadharRepo @Inject constructor(private val aadharApi: AadharApi) :
  BaseDataSource() {
  suspend fun postUser(aadharRequest: AadharRequest) =
    getResult { aadharApi.postCustomer(aadharRequest) }

  suspend fun getUsers() = getResult { aadharApi.getCustomers() }
}