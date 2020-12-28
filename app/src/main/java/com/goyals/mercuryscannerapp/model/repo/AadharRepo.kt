package com.goyals.mercuryscannerapp.model.repo

import com.goyals.mercuryscannerapp.arch.BaseDataSource
import com.goyals.mercuryscannerapp.model.api.AadharApi
import com.goyals.mercuryscannerapp.model.schema.AadharRequest
import com.goyals.mercuryscannerapp.model.schema.ListRequest
import javax.inject.Inject

class AadharRepo @Inject constructor(private val aadharApi: AadharApi) :
  BaseDataSource() {
  suspend fun postUser(aadharRequest: AadharRequest) =
    getResult { aadharApi.postCustomer(aadharRequest) }

  suspend fun getUsers(listRequest: ListRequest) = getResult { aadharApi
    .getCustomers(listRequest) }
}