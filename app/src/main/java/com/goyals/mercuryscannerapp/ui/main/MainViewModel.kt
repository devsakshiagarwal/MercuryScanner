package com.goyals.mercuryscannerapp.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.goyals.mercuryscannerapp.arch.performNwOperation
import com.goyals.mercuryscannerapp.model.repo.AadharRepo
import com.goyals.mercuryscannerapp.model.schema.ListRequest

class MainViewModel @ViewModelInject constructor(private val aadharRepo: AadharRepo) :
  ViewModel() {
  fun getCustomers(listRequest: ListRequest) = performNwOperation {
    aadharRepo.getUsers(listRequest) }
}