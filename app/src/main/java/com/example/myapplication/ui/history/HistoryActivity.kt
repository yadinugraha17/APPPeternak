package com.example.myapplication.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.core.adapter.HistoryAdapter
import com.example.myapplication.core.adapter.TernakAdapter
import com.example.myapplication.core.data.api.network.State
import com.example.myapplication.core.data.api.response.item.HistoryItem
import com.example.myapplication.core.data.api.response.item.TernakItem
import com.example.myapplication.databinding.ActivityHistoryBinding
import com.example.myapplication.ui.login.LoginActivity
import com.example.myapplication.ui.login.LoginViewModel
import com.example.myapplication.ui.ternak.DetailTernakActivity
import com.google.gson.Gson
import com.inyongtisto.myhelper.base.BaseActivity
import com.inyongtisto.myhelper.extension.toastError
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : BaseActivity() {
    private var _binding: ActivityHistoryBinding? = null
    private val binding get() = _binding
    private var root: View? = null
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHistoryBinding.inflate(layoutInflater)
        root = binding?.root
        setContentView(root)
    }
    private fun history() {
        viewModel.history("Bearer ${LoginActivity.TOKEN_KEY}")
            .observe(this){
                when (it.state) {
                    State.SUCCESS -> {
                        progress.dismiss()
                        val response = it.data
                        val historyAdapter = HistoryAdapter()
                        historyAdapter.setData(response)
                        with(binding?.rvHistory) {
                            this?.adapter = historyAdapter
                            this?.layoutManager = LinearLayoutManager(
                                this@HistoryActivity,
                                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                                false
                            )
                            this?.setHasFixedSize(true)
                        }
                        historyAdapter.setOnItemClickCallback((object :
                            HistoryAdapter.OnItemClickCallback {
                            override fun onItemClicked(news: HistoryItem) {
                                val json = Gson().toJson(news)
                                val intent = Intent(
                                    this@HistoryActivity,
                                    DetailHistoryActivity::class.java
                                )
                                intent.putExtra("history", json)
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
        history()
    }
}