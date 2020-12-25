package com.goyals.mercuryscannerapp.arch.di

import com.goyals.mercuryscannerapp.model.Urls
import com.goyals.mercuryscannerapp.model.api.AadharApi
import com.goyals.mercuryscannerapp.model.api.LoginApi
import com.goyals.mercuryscannerapp.model.repo.AadharRepo
import com.goyals.mercuryscannerapp.model.repo.LoginRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
  @Singleton @Provides
  fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(Urls.BASE_URL)
      .addConverterFactory(MoshiConverterFactory.create())
      .build()

  @Singleton @Provides fun providesHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient().newBuilder()
      .addInterceptor(loggingInterceptor)
      .connectTimeout(30, TimeUnit.SECONDS)
      .writeTimeout(30, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .build()
  }

  @Provides fun providesAadharApi(retrofit: Retrofit): AadharApi =
    retrofit.create(AadharApi::class.java)

  @Provides fun provideAadharRepo(aadharApi: AadharApi) = AadharRepo(aadharApi)

  @Provides fun providesLoginApi(retrofit: Retrofit): LoginApi =
    retrofit.create(LoginApi::class.java)

  @Provides fun provideLoginRepo(loginApi: LoginApi) = LoginRepo(loginApi)
}