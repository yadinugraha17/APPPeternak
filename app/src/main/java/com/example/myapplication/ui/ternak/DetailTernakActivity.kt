package com.example.myapplication.ui.ternak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R
import com.example.myapplication.core.data.api.network.State
import com.example.myapplication.core.data.api.response.item.TernakItem
import com.example.myapplication.databinding.ActivityDetailTernakBinding
import com.example.myapplication.databinding.ActivityTernak2Binding
import com.example.myapplication.ui.login.LoginActivity
import com.example.myapplication.ui.login.LoginViewModel
import com.google.gson.Gson
import com.inyongtisto.myhelper.base.BaseActivity
import com.inyongtisto.myhelper.extension.toastError
import com.inyongtisto.myhelper.extension.toastSuccess
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailTernakActivity : BaseActivity() {
    private var _binding: ActivityDetailTernakBinding? = null
    private val binding get() = _binding
    private var root: View? = null
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailTernakBinding.inflate(layoutInflater)
        root = binding?.root
        setContentView(root)
        detailternak()
        binding?.hapus?.setOnClickListener {
            val dialog = AlertDialog.Builder(this@DetailTernakActivity)
                .setTitle("Hapus Ternak")
                .setMessage("Apakah Anda yakin ingin menghapus ternak ini?")
                .setPositiveButton("Ya") { _, _ ->
                    delTernak()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
            dialog.show()
        }

        binding?.edit?.setOnClickListener {
            val json = intent?.getStringExtra("ternak")
            val ternak = Gson().fromJson(json, TernakItem::class.java)
            val intent = Intent (this@DetailTernakActivity, EditTernakActivity::class.java)
            intent.putExtra("detail", json)
            startActivity(intent)
        }
    }
    private fun detailternak () {
        val json = intent?.getStringExtra("ternak")
        val ternak = Gson().fromJson(json, TernakItem::class.java)
        binding?.tvNamaternak?.text = ternak.nama_ternak
        binding?.tvRegis?.text = ternak.no_regis
        binding?.tvUmur?.text = ternak.umur
        binding?.tvJenis?.text = ternak.rumpun.nama
        binding?.tvJeniskelamin?.text = ternak.jenis_kelamin
        Picasso.get().load(ternak.photo)
            .error(R.drawable.ternak)
            .into(binding?.imgTernak)
    }
    private fun delTernak() {
        val json = intent?.getStringExtra("ternak")
        val ternak = Gson().fromJson(json, TernakItem::class.java)
        viewModel.delternak("Bearer ${LoginActivity.TOKEN_KEY}", ternak.id)
            .observe(this) { result ->
                when (result.state) {
                    State.SUCCESS -> {
                        // Data berhasil dihapus, lakukan tindakan yang diperlukan
                        toastSuccess("Data ternak berhasil dihapus")
                        finish() // Kembali ke halaman sebelumnya atau tutup aktivitas ini
                    }
                    State.LOADING -> {
                        progress.show()
                    }
                    State.ERROR -> {
                        toastError(result.message.toString())
                    }
                }
            }
    }
    override fun onResume() {
        super.onResume()
        detailternak()
    }
}