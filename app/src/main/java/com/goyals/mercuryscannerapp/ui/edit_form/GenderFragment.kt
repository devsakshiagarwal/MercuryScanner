package com.goyals.mercuryscannerapp.ui.edit_form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.goyals.mercuryscannerapp.R
import kotlinx.android.synthetic.main.fragment_gender.*

class GenderFragment : BottomSheetDialogFragment() {
  private lateinit var editFormViewModel: EditFormViewModel

  companion object {
    @JvmStatic fun newInstance() = GenderFragment()
  }

  override fun onCreateView(inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_gender, container, false)
  }

  override fun onViewCreated(view: View,
    savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    editFormViewModel =
      ViewModelProvider(requireActivity()).get(EditFormViewModel::class.java)
    initView()
  }

  private fun initView() {
    tv_male.setOnClickListener {
      editFormViewModel.setGender("Male")
      dismiss()
    }
    tv_female.setOnClickListener {
      editFormViewModel.setGender("Female")
      dismiss()
    }
    tv_other.setOnClickListener {
      editFormViewModel.setGender("Other")
      dismiss()
    }
  }
}