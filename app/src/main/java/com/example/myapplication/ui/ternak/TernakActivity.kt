package com.example.myapplication.ui.ternak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.core.adapter.TernakAdapter
import com.example.myapplication.core.data.api.network.State
import com.example.myapplication.core.data.api.response.item.TernakItem
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.databinding.ActivityTernak2Binding
import com.example.myapplication.ui.login.LoginActivity
import com.example.myapplication.ui.login.LoginViewModel
import com.example.myapplication.ui.login.ViewPageAdapter
import com.google.gson.Gson
import com.inyongtisto.myhelper.base.BaseActivity
import com.inyongtisto.myhelper.extension.toastError
import okhttp3.internal.wait
import org.koin.androidx.viewmodel.ext.android.viewModel

class TernakActivity : BaseActivity() {
    private var _binding: ActivityTernak2Binding? = null
    private val binding get() = _binding
    private var root: View? = null
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTernak2Binding.inflate(layoutInflater)
        root = binding?.root
        setContentView(root)


        binding?.btTambah?.setOnClickListener {
            val intent = Intent (this@TernakActivity, AddTernakActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getTernak() {
        viewModel.ternak("Bearer ${LoginActivity.TOKEN_KEY}")
            .observe(this){
                when (it.state) {
                    State.SUCCESS -> {
                        progress.dismiss()
                        val response = it.data
                        val ternakAdapter = TernakAdapter()
                        ternakAdapter.setData(response)
                        with(binding?.rvTernak) {
                            this?.adapter = ternakAdapter
                            this?.layoutManager = LinearLayoutManager(
                                this@TernakActivity,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            this?.setHasFixedSize(true)
                            }
                        ternakAdapter.setOnItemClickCallback((object :
                            TernakAdapter.OnItemClickCallback {
                            override fun onItemClicked(news: TernakItem) {
                                val json = Gson().toJson(news)
                                val intent = Intent(
                                    this@TernakActivity,
                                    DetailTernakActivity::class.java
                                )
                                intent.putExtra("ternak", json)
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

    override fun onResume() {
        super.onResume()
        getTernak()
    }
}