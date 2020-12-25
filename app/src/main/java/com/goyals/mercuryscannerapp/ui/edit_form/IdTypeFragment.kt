package com.goyals.mercuryscannerapp.ui.edit_form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.goyals.mercuryscannerapp.R
import kotlinx.android.synthetic.main.fragment_id_type.tv_aadhar
import kotlinx.android.synthetic.main.fragment_id_type.tv_pan
import kotlinx.android.synthetic.main.fragment_id_type.tv_passport
import kotlinx.android.synthetic.main.fragment_id_type.tv_voter

class IdTypeFragment : BottomSheetDialogFragment() {
  private lateinit var editFormViewModel: EditFormViewModel

  companion object {
    @JvmStatic fun newInstance() = IdTypeFragment()
  }

  override fun onCreateView(inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_id_type, container, false)
  }

  override fun onViewCreated(view: View,
    savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    editFormViewModel =
      ViewModelProvider(requireActivity()).get(EditFormViewModel::class.java)
    initView()
  }

  private fun initView() {
    tv_aadhar.setOnClickListener {
      editFormViewModel.setIdType("Aadhar Card")
      dismiss()
    }
    tv_pan.setOnClickListener {
      editFormViewModel.setIdType("Pan Card")
      dismiss()
    }
    tv_passport.setOnClickListener {
      editFormViewModel.setIdType("Passport")
      dismiss()
    }
    tv_voter.setOnClickListener {
      editFormViewModel.setIdType("Voter Id Card")
      dismiss()
    }
  }
}