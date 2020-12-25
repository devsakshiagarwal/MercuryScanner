package com.goyals.mercuryscannerapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MercuryApp : Application() {
  override fun onCreate() {
    super.onCreate()
  }
}