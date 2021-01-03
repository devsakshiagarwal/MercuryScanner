package com.goyals.mercuryscannerapp.model

object Urls {
  const val BASE_URL = "http://15.206.82.84:3000/"
  private const val CUSTOMER = "customer/"
  const val CUSTOMER_LIST = "${CUSTOMER}listing"
  const val CUSTOMER_ACTION = "${CUSTOMER}create"
  const val CUSTOMER_UPDATE = "${CUSTOMER}update/{user_id}"
  private const val USER = "user/"
  const val LOGIN = "${USER}validate"
}