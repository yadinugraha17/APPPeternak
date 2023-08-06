package com.example.myapplication.ui.submission

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.core.adapter.InseminatorAdapter
import com.example.myapplication.core.data.api.network.State
import com.example.myapplication.core.data.api.request.FindRequest
import com.example.myapplication.core.data.api.response.item.FindItem
import com.example.myapplication.databinding.ActivityPengajuanBinding
import com.example.myapplication.ui.login.LoginActivity
import com.example.myapplication.ui.login.LoginViewModel
import com.google.gson.Gson
import com.inyongtisto.myhelper.base.BaseActivity
import com.inyongtisto.myhelper.extension.toastError
import org.koin.androidx.viewmodel.ext.android.viewModel

class PengajuanActivity : BaseActivity() {
    private var _binding: ActivityPengajuanBinding? = null
    private val binding get() = _binding
    private var root: View? = null
    private val viewModel: LoginViewModel by viewModel()
    private var rumpunid = 0
    private var rumpunname = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPengajuanBinding.inflate(layoutInflater)
        root = binding?.root
        setContentView(root)
        getRumpun()
        binding?.searchTernak?.setOnClickListener {
            findib()
        }
    }

    private fun getRumpun() {
        val arrayString = ArrayList<String>()
        arrayString.add("Rumpun")
        viewModel.rumpun().observe(this@PengajuanActivity) {
            when (it.state) {
                State.SUCCESS -> {
                    val response = it.data
                    response?.forEach { rumpun ->
                        arrayString.add(rumpun.nama)
                    }
                    val arrayAdapter = ArrayAdapter<String>(
                        this@PengajuanActivity,
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
                                    rumpunname = rumpun?.nama ?: ""
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

    private fun findib() {
        if (rumpunid == 0 ) {
            // Tampilkan pesan bahwa waktu dan waktu harus diisi
            toastError("Pilih Jenis Ternak Terlebih Dahulu")
            return
        }
        val data = FindRequest(rumpunid)
        viewModel.findib("Bearer ${LoginActivity.TOKEN_KEY}", data)
            .observe(this) {
                when (it.state) {
                    State.SUCCESS -> {
                        progress.dismiss()
                        val response = it.data
                        val ternakAdapter = InseminatorAdapter()
                        ternakAdapter.setData(response)
                        with(binding?.rvInseminator) {
                            this?.adapter = ternakAdapter
                            this?.layoutManager = LinearLayoutManager(
                                this@PengajuanActivity,
                                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                                false
                            )
                            this?.setHasFixedSize(true)
                        }
                        ternakAdapter.setOnItemClickCallback((object :
                            InseminatorAdapter.OnItemClickCallback {
                            override fun onItemClicked(news: FindItem) {
                                val json = Gson().toJson(news)
                                val intent = Intent(
                                    this@PengajuanActivity,
                                    MengajukanIbActivity::class.java
                                )
                                intent.putExtra("ternak", json)
                                intent.putExtra("inseminarator_id", news.id)
                                intent.putExtra("rumpun", rumpunname)
                                intent.putExtra("rumpunId", rumpunid)
                                startActivity(intent)

                            }

                        }))

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