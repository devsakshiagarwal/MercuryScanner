package com.goyals.mercuryscannerapp.model.api

import com.goyals.mercuryscannerapp.model.Urls
import com.goyals.mercuryscannerapp.model.schema.Login
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {
  @FormUrlEncoded @POST(Urls.LOGIN) suspend fun doLogin(@Field(
    "login") login: String,
    @Field("password") password: String): Response<Login>
}