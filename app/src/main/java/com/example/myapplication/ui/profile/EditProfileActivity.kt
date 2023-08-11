package com.example.myapplication.ui.profile

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.myapplication.core.data.api.network.State
import com.example.myapplication.core.data.api.request.ProfileRequest
import com.example.myapplication.core.data.api.request.RegisterRequest
import com.example.myapplication.core.data.api.response.item.ProfileItem
import com.example.myapplication.core.data.api.response.item.TernakItem
import com.example.myapplication.databinding.ActivityEditProfileBinding
import com.example.myapplication.ui.login.LoginActivity
import com.example.myapplication.ui.login.LoginViewModel
import com.google.gson.Gson
import com.inyongtisto.myhelper.base.BaseActivity
import com.inyongtisto.myhelper.extension.toastError
import com.inyongtisto.myhelper.extension.toastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileActivity : BaseActivity() {
    private var _binding: ActivityEditProfileBinding? = null
    private val binding get() = _binding
    private var root: View? = null
    private val viewModel: LoginViewModel by viewModel()
    private var provinceid = 0
    private var pendid = 0
    private var regencyid = 0
    private var distictid = 0
    private var villageid:Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditProfileBinding.inflate(layoutInflater)
        root = binding?.root
        setContentView(root)
        getProvince()
        getPend()
        profile()
        binding?.btSave?.setOnClickListener {
            editprofil()
        }
    }

    private fun profile() {
        viewModel.profile("Bearer ${LoginActivity.TOKEN_KEY}")
            .observe(this) {
                when (it.state) {
                    State.SUCCESS -> {
                        val respons = it.data
                        binding?.etNik?.setText(respons!![0].nik)
                        binding?.etName?.setText(respons!![0].nama)
                        binding?.etNohp?.setText(respons!![0].no_hp)
                        binding?.etPend?.setText(respons!![0].pendidikan.nama)
                        binding?.etAddress?.setText(respons!![0].alamat)
                        binding?.etBreed?.setText(respons!![0].lama_beternak)
                        binding?.etProv?.setText(respons!![0].province.name)
                        binding?.etRegency?.setText(respons!![0].regency.name)
                        binding?.etDistrict?.setText(respons!![0].district.name)
                        binding?.etVillage?.setText(respons!![0].village.name)
                        progress.dismiss()
                        pendid = respons!![0].pendidikan.id
                        provinceid = respons[0].province.id
                        regencyid = respons[0].regency.id
                        distictid = respons[0].district.id
                        villageid = respons[0].village.id

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
    private fun getProvince() {
        val arrayString = ArrayList<String>()
        arrayString.add("Provinsi")
        viewModel.province().observe(this@EditProfileActivity) {
            when (it.state) {
                State.SUCCESS -> {
                    val response = it.data
                    response?.forEach { province ->
                        arrayString.add(province.name)
                    }
                    val arrayAdapter = ArrayAdapter<String>(
                        this,
                        R.layout.simple_spinner_dropdown_item,
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
        viewModel.regency(provinceid).observe(this@EditProfileActivity) {
            when (it.state) {
                State.SUCCESS -> {
                    val response = it.data
                    response?.forEach { regency ->
                        arrayString.add(regency.name)
                    }
                    val arrayAdapter = ArrayAdapter<String>(
                        this,
                        R.layout.simple_spinner_dropdown_item,
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
        viewModel.district(id).observe(this@EditProfileActivity) {
            when (it.state) {
                State.SUCCESS -> {
                    val response = it.data
                    response?.forEach { district ->
                        arrayString.add(district.name)
                    }
                    val arrayAdapter = ArrayAdapter(
                        this,
                        R.layout.simple_spinner_dropdown_item,
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
        viewModel.village(distictid).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val arrayString = ArrayList<String>()
                    arrayString.add("Desa")
                    val response = it.data
                    for (village in response!!) {
                        arrayString.add(village.name)
                    }
                    val arrayAdapter = ArrayAdapter(
                        this,
                        R.layout.simple_spinner_dropdown_item,
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
        viewModel.study().observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val response = it.data
                    response?.forEach { study ->
                        arrayString.add(study.nama)
                    }
                    val arrayAdapter = ArrayAdapter<String>(
                        this,
                        R.layout.simple_spinner_dropdown_item,
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

    private fun editprofil(){
        val nik = binding?.etNik?.text
        val nama = binding?.etName?.text
        val nohp = binding?.etNohp?.text
        val alamat = binding?.etAddress?.text
        val lb = binding?.etBreed?.text

        if (nik.isNullOrEmpty() || nama.isNullOrEmpty() || nohp.isNullOrEmpty() || alamat.isNullOrEmpty() || pendid == 0 || lb.isNullOrEmpty() || provinceid == 0 || regencyid == 0 || distictid == 0 || villageid == 0L ) {
            // data harus di isi
            toastError( "Semua data harus di Isi")
            return
        }
        val data = ProfileRequest(
            nik.toString(),
            nama.toString(),
            nohp.toString(),
            alamat.toString(),
            pendid,
            lb.toString(),
            provinceid,
            regencyid,
            distictid,
            villageid.toString()
        )
        viewModel.editprofile("Bearer ${LoginActivity.TOKEN_KEY}", data)
            .observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    toastSuccess("Data Profile Berhasil DiUbah")
                    onBackPressed()
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