package com.example.myapplication.ui.history

import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.core.data.api.response.item.HistoryItem
import com.example.myapplication.core.data.api.response.item.TernakItem
import com.example.myapplication.databinding.ActivityDetailHistoryBinding
import com.example.myapplication.databinding.ActivityHistoryBinding
import com.example.myapplication.ui.login.LoginViewModel
import com.google.gson.Gson
import com.inyongtisto.myhelper.base.BaseActivity
import com.inyongtisto.myhelper.extension.toastSuccess
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailHistoryActivity : BaseActivity() {
    private var _binding: ActivityDetailHistoryBinding? = null
    private val binding get() = _binding
    private var root: View? = null
    private val viewModel: LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        root = binding?.root
        setContentView(root)
        detailhistory()
        binding?.copy?.setOnClickListener {
            copyTextToClipboard(binding?.tvNohp?.text.toString())
            toastSuccess("No HP Berhasil Disalin")
        }
    }
    private fun detailhistory () {
        val json = intent?.getStringExtra("history")
        val ternak = Gson().fromJson(json, HistoryItem::class.java)
        binding?.tvNoregis?.text = ternak.ternak.no_regis
        binding?.tvJenisSemen?.text = ternak.rumpun.nama
        binding?.tvTglBirahi?.text = ternak.waktu_birahi
        binding?.tvJamBirahi?.text = ternak.jam_birahi
        if (ternak.tgl_ib != null) {
            binding?.tvTglIb?.text = ternak.tgl_ib
        } else {
            binding?.tvTglIb?.text = "Belum Diinseminasi"
        }
        if (ternak.status_kebuntingan == "0") {
            binding?.tvBunting?.text = "Belum Bunting"
        } else if (ternak.status_kebuntingan == "1") {
            binding?.tvBunting?.text = "Bunting"
        } else {
            // Jika nilainya bukan "0" atau "1", tampilkan nilai sebenarnya
            binding?.tvBunting?.text = ternak.status_kebuntingan
        }
        // Tampilkan teks "Belum" jika status kelahiran adalah "0", jika "1" tampilkan "Sudah"
        if (ternak.status_lahir == "0") {
            binding?.tvLahir?.text = "Belum Lahir"
        } else if (ternak.status_lahir == "1") {
            binding?.tvLahir?.text = "Lahir"
        } else {
            // Jika nilainya bukan "0" atau "1", tampilkan nilai sebenarnya
            binding?.tvLahir?.text = ternak.status_lahir
        }
        binding?.tvInseminator?.text = ternak.insiminator.nama
        binding?.tvNohp?.text = ternak.insiminator.no_hp
    }
    private fun copyTextToClipboard(text: String) {
        val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = android.content.ClipData.newPlainText("No Hp Tersalin", text)
        clipboard.setPrimaryClip(clip)
    }
}