package com.goyals.mercuryscannerapp.ui.edit_form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.goyals.mercuryscannerapp.R
import kotlinx.android.synthetic.main.fragment_location.*

class LocationFragment : BottomSheetDialogFragment() {
  private lateinit var editFormViewModel: EditFormViewModel

  companion object {
    @JvmStatic fun newInstance() = LocationFragment()
  }

  override fun onCreateView(inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_location, container, false)
  }

  override fun onViewCreated(view: View,
    savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    editFormViewModel =
      ViewModelProvider(requireActivity()).get(EditFormViewModel::class.java)
    initView()
  }

  private fun initView() {
    tv_1.setOnClickListener {
      editFormViewModel.setLocation(tv_1.text.toString())
      dismiss()
    }
    tv_2.setOnClickListener {
      editFormViewModel.setLocation(tv_2.text.toString())
      dismiss()
    }
    tv_3.setOnClickListener {
      editFormViewModel.setLocation(tv_3.text.toString())
      dismiss()
    }
    tv_4.setOnClickListener {
      editFormViewModel.setLocation(tv_4.text.toString())
      dismiss()
    }
    tv_5.setOnClickListener {
      editFormViewModel.setLocation(tv_5.text.toString())
      dismiss()
    }
    tv_6.setOnClickListener {
      editFormViewModel.setLocation(tv_6.text.toString())
      dismiss()
    }
    tv_7.setOnClickListener {
      editFormViewModel.setLocation(tv_7.text.toString())
      dismiss()
    }
    tv_8.setOnClickListener {
      editFormViewModel.setLocation(tv_8.text.toString())
      dismiss()
    }
    tv_9.setOnClickListener {
      editFormViewModel.setLocation(tv_9.text.toString())
      dismiss()
    }
    tv_10.setOnClickListener {
      editFormViewModel.setLocation(tv_10.text.toString())
      dismiss()
    }
    tv_11.setOnClickListener {
      editFormViewModel.setLocation(tv_11.text.toString())
      dismiss()
    }
    tv_12.setOnClickListener {
      editFormViewModel.setLocation(tv_12.text.toString())
      dismiss()
    }
    tv_13.setOnClickListener {
      editFormViewModel.setLocation(tv_13.text.toString())
      dismiss()
    }
    tv_14.setOnClickListener {
      editFormViewModel.setLocation(tv_14.text.toString())
      dismiss()
    }
  }
}