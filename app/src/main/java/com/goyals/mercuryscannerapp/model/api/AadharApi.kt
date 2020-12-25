package com.goyals.mercuryscannerapp.model.api

import com.goyals.mercuryscannerapp.model.Urls
import com.goyals.mercuryscannerapp.model.schema.AadharInfo
import com.goyals.mercuryscannerapp.model.schema.AadharRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AadharApi {
  @POST(Urls.CUSTOMER_ACTION)
  suspend fun postCustomer(@Body aadharRequest: AadharRequest): Response<AadharInfo>

  @GET(Urls.CUSTOMER_LIST)
  suspend fun getCustomers(): Response<List<AadharInfo>>
}