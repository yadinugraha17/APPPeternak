package com.example.myapplication.ui.profile

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.core.data.api.network.State
import com.example.myapplication.core.session.SessionRepository
import com.example.myapplication.core.session.SessionViewModel
import com.example.myapplication.core.utils.ViewModelUserFactory
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.ui.login.LoginActivity
import com.example.myapplication.ui.login.LoginActivity.Companion.TOKEN_KEY
import com.example.myapplication.ui.login.LoginViewModel
import com.inyongtisto.myhelper.base.BaseFragment
import com.inyongtisto.myhelper.extension.toastError
import com.inyongtisto.myhelper.extension.toastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    private var root: View? = null
    private val viewModel: LoginViewModel by viewModel()
    private lateinit var sessionViewModel: SessionViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

    private lateinit var popupWindow: PopupWindow
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        root = binding?.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionViewModel = ViewModelProvider(
            this,
            ViewModelUserFactory(SessionRepository.getInstance(requireContext().dataStore))
        )[SessionViewModel::class.java]

        val inflater = LayoutInflater.from(requireContext())
        val popupView = inflater.inflate(R.layout.custom_popup_menu, null)
        popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        val backgroundDrawable = ColorDrawable(Color.TRANSPARENT)
        popupWindow.setBackgroundDrawable(backgroundDrawable)
        popupWindow.isOutsideTouchable = true

        val btnEdit = popupView.findViewById<LinearLayout>(R.id.btn_edit)
        val btnLogout = popupView.findViewById<LinearLayout>(R.id.btn_logout)

        btnLogout.setOnClickListener {
            logout()
        }
        btnEdit.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }
        binding?.menu?.setOnClickListener {
            popupWindow.showAsDropDown(binding?.menu)
        }
    }


    private fun profile() {
        viewModel.profile("Bearer $TOKEN_KEY")
            .observe(viewLifecycleOwner) {
                when (it.state) {
                    State.SUCCESS -> {
                        val respons = it.data
                        binding?.tvNik?.text = respons!![0].nik
                        binding?.name?.text = respons[0].nama
                        binding?.tvNohp?.text = respons[0].no_hp
                        binding?.tvPend?.text = respons[0].pendidikan.nama
                        binding?.tvAlamat?.text = respons[0].alamat
                        binding?.tvBerternak?.text = respons[0].lama_beternak
                        binding?.tvProv?.text = respons[0].province.name
                        binding?.tvRegency?.text = respons[0].regency.name
                        binding?.tvDistrict?.text = respons[0].district.name
                        binding?.tvVillage?.text = respons[0].village.name
                        progress.dismiss()
                    }

                    State.LOADING -> {
                        progress.show()
                    }

                    State.ERROR -> {
                        progress.dismiss()
                        toastError(it.message.toString())
                    }
                }
            }
    }

    private fun logout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Ya") { _, _ ->
            sessionViewModel.logout()
            toastSuccess("Logout Berhasil")
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        builder.setNegativeButton("Batal") { _, _ -> }
        builder.setTitle("Anda yakin akan keluar? ")
        builder.setMessage("Klik 'Ya' jika ingin keluar!!")
        builder.create().show()

    }

    override fun onResume() {
        super.onResume()
        profile()
    }
}