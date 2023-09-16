package com.example.myapplication.ui.ternak

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.R
import com.example.myapplication.core.data.api.network.State
import com.example.myapplication.core.data.api.response.item.TernakItem
import com.example.myapplication.databinding.ActivityEditTernakBinding
import com.example.myapplication.ui.HomeActivity
import com.example.myapplication.ui.login.LoginActivity
import com.example.myapplication.ui.login.LoginViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import com.inyongtisto.myhelper.base.BaseActivity
import com.inyongtisto.myhelper.extension.toMultipartBody
import com.inyongtisto.myhelper.extension.toRequestBody
import com.inyongtisto.myhelper.extension.toastError
import com.inyongtisto.myhelper.extension.toastSuccess
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class EditTernakActivity : BaseActivity() {
    private var _binding: ActivityEditTernakBinding? = null
    private val binding get() = _binding
    private var root: View? = null
    private val viewModel: LoginViewModel by viewModel()
    private var rumpunid = 0
    private var idTernak: Int = 0

    //    private var ternakItem: TernakItem? = null
    private var file: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditTernakBinding.inflate(layoutInflater)
        root = binding?.root
        setContentView(root)
        getRumpun()
        fillFormWithData()
        binding?.upImg?.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }
        // Buat array adapter dengan opsi Jantan dan Betina
        val jenisKelaminOptions = arrayOf("Pilih Jenis Kelamin", "Jantan", "Betina")
        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, jenisKelaminOptions)

        // Atur tata letak dropdown
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding?.jenisKelamin?.setOnClickListener {
            binding?.spJeniskelamin?.performClick()
        }
        // Terapkan adapter pada spinner
        binding?.spJeniskelamin?.adapter = arrayAdapter
        binding?.spJeniskelamin?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        val jenisKelamin = jenisKelaminOptions[position]
                        binding?.jenisKelamin?.setText(jenisKelamin)

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

        binding?.edit?.setOnClickListener {
            ternak()
        }
    }

    private fun fillFormWithData() {
        val json = intent?.getStringExtra("detail")
        val ternak = Gson().fromJson(json, TernakItem::class.java)
        // Mengisi data pada formulir
        binding?.nameTernak?.setText(ternak.nama_ternak)
        binding?.umur?.setText(ternak.umur)
        binding?.jenisKelamin?.setText(ternak.jenis_kelamin)

        // Menetapkan status melahirkan berdasarkan data ternak
        binding?.lahir?.isChecked = ternak.status_melahirkan == "1"
        binding?.belumlahir?.isChecked = ternak.status_melahirkan == "0"

        // Mengisi spinner dengan rumpun yang sesuai
        binding?.etRumpun?.setText(ternak.rumpun.nama)
        rumpunid = ternak.rumpun.id
        idTernak = ternak.id
    }

//
//    private fun getRumpunIndex(rumpun: String): Int {
//        val arrayAdapter = binding?.spRumpun?.adapter as ArrayAdapter<*>
//        for (i in 0 until arrayAdapter.count) {
//            if (arrayAdapter.getItem(i) == rumpun) {
//                return i
//            }
//        }
//        return 0
//    }

    private fun getRumpun() {
        val arrayString = ArrayList<String>()
        arrayString.add("Rumpun")
        viewModel.rumpun().observe(this@EditTernakActivity) {
            when (it.state) {
                State.SUCCESS -> {
                    val response = it.data
                    response?.forEach { rumpun ->
                        arrayString.add(rumpun.nama)
                    }
                    val arrayAdapter = ArrayAdapter<String>(
                        this@EditTernakActivity,
                        android.R.layout.simple_spinner_dropdown_item,
                        arrayString
                    )

                    binding?.spRumpun?.adapter = arrayAdapter
                    binding?.etRumpun?.setOnClickListener {
                        binding?.spRumpun?.performClick()
                    }
                    binding?.spRumpun?.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (position != 0) {
                                    val rumpun = response?.get(position - 1)
                                    binding?.etRumpun?.setText(rumpun?.nama)
                                    rumpunid = rumpun?.id ?: 0
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

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                file = File(fileUri.path.toString())

                Picasso.get().load(fileUri)
                    .error(R.drawable.ternak)
                    .into(binding?.ivImage)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private fun ternak() {
        val nama = binding?.nameTernak?.text
        if (nama.isNullOrEmpty()) {
            // nama harus di isi
            toastError( "Nama Ternak harus di Isi")
            return
        }
        val umur = binding?.umur?.text
        if (umur.isNullOrEmpty()) {
            // umur harus di isi
            toastError( "Umur Ternak harus di Isi")
            return
        }
        if (rumpunid == 0) {
            // jk harus di isi
            Toast.makeText(this@EditTernakActivity, "Jenis Ternak harus di Isi", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val jk = binding?.jenisKelamin?.text
        if (umur.isNullOrEmpty()) {
            // umur harus di isi
            toastError("Silahkan pilih jenis kelamin ternak")
            return
        }
        var status = ""
        binding?.apply {
            status = if (lahir.isActivated) {
                "1"
            } else {
                "0"
            }
            if (status.isBlank()) {
                // jk harus di isi
                Toast.makeText(
                    this@EditTernakActivity,
                    "Silahkan pilih status lahir ternak",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
        }
        if (file != null) {

            viewModel.editternak(
                "Bearer ${LoginActivity.TOKEN_KEY}",
                file.toMultipartBody("photo"),
                idTernak,
                nama.toString().toRequestBody(),
                status.toRequestBody(),
                umur.toString().toRequestBody(),
                rumpunid.toString().toRequestBody(),
                jk.toString().toRequestBody()
            )
                .observe(this) {
                    when (it.state) {
                        State.SUCCESS -> {
                            progress.dismiss()
                            toastSuccess("Ternak Berhasil DiUbah")
                            onBackPressed()
                            finish()

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
        } else {
            viewModel.editternak(
                "Bearer ${LoginActivity.TOKEN_KEY}",
                null,
                idTernak,
                nama.toString().toRequestBody(),
                status.toRequestBody(),
                umur.toString().toRequestBody(),
                rumpunid.toString().toRequestBody(),
                jk.toString().toRequestBody()
            )
                .observe(this) {
                    when (it.state) {
                        State.SUCCESS -> {
                            progress.dismiss()
                            toastSuccess("Ternak Berhasil DiUbah")
                            val intent = Intent (this@EditTernakActivity, TernakActivity::class.java)
                            startActivity(intent)
                            finish()
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
    override fun onBackPressed() {
        val intent = Intent(this, TernakActivity::class.java)
        startActivity(intent)
        finish()
    }

}