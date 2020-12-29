package com.goyals.mercuryscannerapp.model.api

import com.goyals.mercuryscannerapp.model.Urls
import com.goyals.mercuryscannerapp.model.schema.AadharInfo
import com.goyals.mercuryscannerapp.model.schema.AadharRequest
import com.goyals.mercuryscannerapp.model.schema.AadharResponse
import com.goyals.mercuryscannerapp.model.schema.CreateResponse
import com.goyals.mercuryscannerapp.model.schema.ListRequest
import com.goyals.mercuryscannerapp.model.schema.UpdateCustomerSchema
import com.goyals.mercuryscannerapp.model.schema.UpdateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AadharApi {
  @POST(Urls.CUSTOMER_ACTION)
  suspend fun postCustomer(@Body aadharRequest: AadharRequest): Response<CreateResponse>

  @POST(Urls.CUSTOMER_LIST)
  suspend fun getCustomers(@Body listRequest: ListRequest): Response<AadharResponse>

  @PUT(Urls.CUSTOMER_UPDATE)
  suspend fun updateCustomer(@Body updateCustomerSchema:
  UpdateCustomerSchema, @Path("user_id") userId: Int) :
    Response<UpdateResponse>
}