package com.goyals.mercuryscannerapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.goyals.mercuryscannerapp.R
import com.goyals.mercuryscannerapp.model.schema.AadharInfo
import com.goyals.mercuryscannerapp.ui.main.AadharAdapter.AadharViewHolder

class AadharAdapter() : RecyclerView.Adapter<AadharViewHolder>() {
  private lateinit var aadharInfo: List<AadharInfo>

  fun setAddress(aadharInfo: List<AadharInfo>) {
    this.aadharInfo = aadharInfo
  }

  override fun onCreateViewHolder(parent: ViewGroup,
    viewType: Int): AadharViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return AadharViewHolder(inflater, parent)
  }

  override fun onBindViewHolder(holder: AadharViewHolder,
    position: Int) {
    holder.bind(aadharInfo[position])
  }

  override fun getItemCount(): Int = aadharInfo.size

  class AadharViewHolder(inflater: LayoutInflater,
    parent: ViewGroup) : RecyclerView.ViewHolder(
    inflater.inflate(R.layout.item_aadhar, parent, false)) {
    private var tvName: AppCompatTextView = itemView.findViewById(R.id.tv_name)
    private var tvAge: AppCompatTextView = itemView.findViewById(R.id.tv_age)
    private var tvId: AppCompatTextView = itemView.findViewById(R.id.tv_id)
    private var tvAddress: AppCompatTextView =
      itemView.findViewById(R.id.tv_address)
    private var tvPhone: AppCompatTextView =
      itemView.findViewById(R.id.tv_phone)
    private var tvGender: AppCompatTextView =
      itemView.findViewById(R.id.tv_gender)
    private var tvResult: AppCompatTextView =
      itemView.findViewById(R.id.tv_result)

    fun bind(aadharInfo: AadharInfo) {
      tvName.text = aadharInfo.customerName
      tvAge.text = aadharInfo.customerDob
      tvId.text = aadharInfo.customerUid
      tvAddress.text = aadharInfo.customerAddress
      tvPhone.text = aadharInfo.customerPhone
      tvGender.text = aadharInfo.customerGender
      //tvResult.text = aadharInfo.res
    }
  }
}