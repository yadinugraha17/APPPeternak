package com.example.myapplication.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.myapplication.core.data.api.network.State
import com.example.myapplication.core.data.api.request.RegisterRequest
import com.example.myapplication.databinding.FragmentSingupBinding
import com.inyongtisto.myhelper.base.BaseFragment
import com.inyongtisto.myhelper.extension.toastError
import com.inyongtisto.myhelper.extension.toastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

class SingupFragment : BaseFragment() {
    private var _binding: FragmentSingupBinding? = null
    private val binding get() = _binding
    private var root: View? = null
    private val viewModel: LoginViewModel by viewModel()
    private var provinceid = 0
    private var pendid = 0
    private var regencyid = 0
    private var distictid = 0
    private var villageid:Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSingupBinding.inflate(layoutInflater)
        root = binding?.root
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProvince()
        getPend()
        binding?.btRegist?.setOnClickListener {
            singin()
        }
    }

    private fun getProvince() {
        val arrayString = ArrayList<String>()
        arrayString.add("Provinsi")
        viewModel.province().observe(viewLifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> {
                    val response = it.data
                    response?.forEach { province ->
                        arrayString.add(province.name)
                    }
                    val arrayAdapter = ArrayAdapter<String>(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        arrayString
                    )

                    binding?.spProv?.adapter = arrayAdapter
                    binding?.etProv?.setOnClickListener {
                        binding?.spProv?.performClick()
                    }
                    binding?.spProv?.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (position != 0) {
                                    val prov = response?.get(position - 1)
                                    binding?.etProv?.setText(prov?.name)
                                    provinceid = prov?.id ?: 0
                                    getKabupaten()
                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }

                        }
                }

                else -> {}
            }
        }
    }

    private fun getKabupaten() {
        val arrayString = ArrayList<String>()
        arrayString.add("Kabupaten")
        viewModel.regency(provinceid).observe(viewLifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> {
                    val response = it.data
                    response?.forEach { regency ->
                        arrayString.add(regency.name)
                    }
                    val arrayAdapter = ArrayAdapter<String>(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        arrayString
                    )

                    binding?.spRegency?.adapter = arrayAdapter
                    binding?.etRegency?.setOnClickListener {
                        binding?.spRegency?.performClick()
                    }
                    binding?.spRegency?.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (position != 0) {
                                    val regency = response?.get(position - 1)
                                    binding?.etRegency?.setText(regency?.name)
                                    regencyid = regency?.id ?: 0
                                    getKecamatan(regency?.id ?: 0)
                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }

                        }
                }

                else -> {}
            }
        }
    }

    private fun getKecamatan(id: Int) {
        val arrayString = ArrayList<String>()
        arrayString.add("Kacamatan")
        viewModel.district(id).observe(viewLifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> {
                    val response = it.data
                    response?.forEach { district ->
                        arrayString.add(district.name)
                    }
                    val arrayAdapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        arrayString
                    )

                    binding?.spDistrict?.adapter = arrayAdapter
                    binding?.etDistrict?.setOnClickListener {
                        binding?.spDistrict?.performClick()
                    }
                    binding?.spDistrict?.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (position != 0) {
                                    val district = response!![position - 1]
                                    binding?.etDistrict?.setText(district.name)
                                    distictid = district.id
                                    getDesa()
                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }

                        }
                }

                else -> {}
            }
        }
    }

    private fun getDesa() {
        viewModel.village(distictid).observe(viewLifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> {
                    val arrayString = ArrayList<String>()
                    arrayString.add("Desa")
                    val response = it.data
                    for (village in response!!) {
                        arrayString.add(village.name)
                    }
                    val arrayAdapter = ArrayAdapter(
                        requireActivity(),
                        android.R.layout.simple_spinner_dropdown_item,
                        arrayString
                    )

                    binding?.spVillage?.adapter = arrayAdapter
                    binding?.etVillage?.setOnClickListener {
                        binding?.spVillage?.performClick()
                    }
                    binding?.spVillage?.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (position != 0) {
                                    val village = response[position - 1]
                                    binding?.etVillage?.setText(village.name)
                                    villageid = village.id
                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }

                        }
                }

                else -> {}
            }
        }
    }

    private fun getPend() {
        val arrayString = ArrayList<String>()
        arrayString.add("Pendidikan")
        viewModel.study().observe(viewLifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> {
                    val response = it.data
                    response?.forEach { study ->
                        arrayString.add(study.nama)
                    }
                    val arrayAdapter = ArrayAdapter<String>(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        arrayString
                    )

                    binding?.spPend?.adapter = arrayAdapter
                    binding?.etPend?.setOnClickListener {
                        binding?.spPend?.performClick()
                    }
                    binding?.spPend?.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (position != 0) {
                                    val pend = response?.get(position - 1)
                                    binding?.etPend?.setText(pend?.nama)
                                    pendid = pend?.id ?: 0
                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }

                        }
                }

                else -> {}
            }
        }
    }

    private fun singin() {
        val nik = binding?.etNik?.text
        val nama = binding?.etName?.text
        val nohp = binding?.etNohp?.text
        val alamat = binding?.etAddress?.text
        val lb = binding?.etBreed?.text
        val pass = binding?.etPass?.text

        if (nik.isNullOrEmpty() || nama.isNullOrEmpty() || nohp.isNullOrEmpty() || alamat.isNullOrEmpty() || pendid == 0 || lb.isNullOrEmpty() || pass.isNullOrEmpty() || provinceid == 0 || regencyid == 0 || distictid == 0 || villageid == 0L ) {
            // data harus di isi
            Toast.makeText(requireContext(), "Semua data harus di Isi", Toast.LENGTH_SHORT).show()
            return
        }

        val data = RegisterRequest(
            nik.toString(),
            nama.toString(),
            nohp.toString(),
            alamat.toString(),
            pendid,
            lb.toString(),
            provinceid,
            regencyid,
            distictid,
            villageid.toString(),
            pass.toString()
        )
        viewModel.register(data).observe(viewLifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    toastSuccess("Register berhasil!!")
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
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
}