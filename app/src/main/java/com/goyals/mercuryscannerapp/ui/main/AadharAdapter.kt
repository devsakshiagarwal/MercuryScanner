package com.goyals.mercuryscannerapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.goyals.mercuryscannerapp.R
import com.goyals.mercuryscannerapp.model.schema.AadharInfo
import com.goyals.mercuryscannerapp.ui.main.AadharAdapter.AadharViewHolder
import com.goyals.mercuryscannerapp.utils.AppUtils

class AadharAdapter(private val aadharAdapterInterface: AadharAdapterInterface?) :
  RecyclerView.Adapter<AadharViewHolder>() {
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
    holder.bind(aadharInfo[position], aadharAdapterInterface)
  }

  override fun getItemCount(): Int = aadharInfo.size

  class AadharViewHolder(inflater: LayoutInflater,
    parent: ViewGroup) : RecyclerView.ViewHolder(
    inflater.inflate(R.layout.item_aadhar, parent, false)) {
    private var tvName: AppCompatTextView = itemView.findViewById(R.id.tv_name)
    private var tvUserId: AppCompatTextView =
      itemView.findViewById(R.id.tv_user_id)
    private var tvId: AppCompatTextView = itemView.findViewById(R.id.tv_id)
    private var tvIdType: AppCompatTextView =
      itemView.findViewById(R.id.tv_id_type)
    private var tvAddress: AppCompatTextView =
      itemView.findViewById(R.id.tv_address)
    private var tvPhone: AppCompatTextView =
      itemView.findViewById(R.id.tv_phone)
    private var tvGender: AppCompatTextView =
      itemView.findViewById(R.id.tv_gender)
    private var tvResult: AppCompatTextView =
      itemView.findViewById(R.id.tv_result)
    private var tvDate: AppCompatTextView =
      itemView.findViewById(R.id.tv_date)

    fun bind(aadharInfo: AadharInfo,
      aadharAdapterInterface: AadharAdapterInterface?) {
      tvName.text = aadharInfo.name
      tvUserId.text = "Customer ID : ${aadharInfo.userId}"
      tvId.text = aadharInfo.proofNumber
      tvAddress.text = aadharInfo.address
      tvPhone.text = "Phone : ${aadharInfo.phone}"
      val age: Int =
        if (aadharInfo.yearOfBirth != null && aadharInfo.yearOfBirth.isNotEmpty()) {
          AppUtils.getAge(aadharInfo.yearOfBirth.toInt())
        } else {
          0
        }
      tvGender.text = "${aadharInfo.gender}, $age years"
      tvIdType.text = "${aadharInfo.proofType} :"
      if (aadharInfo.covidResult == 1) {
        tvResult.setTextColor(
          tvResult.context.resources.getColor(R.color.colorError))
      } else {
        tvResult.setTextColor(
          tvResult.context.resources.getColor(R.color.color00))
      }
      tvResult.text = "Test Result: ${aadharInfo.covidResultToShow}"
      tvDate.text = "Added on: ${aadharInfo.createdDateToShow}"
      itemView.setOnClickListener {
        aadharAdapterInterface?.onClick(aadharInfo)
      }
    }
  }

  interface AadharAdapterInterface {
    fun onClick(aadharInfo: AadharInfo)
  }
}