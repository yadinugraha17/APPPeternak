package com.example.myapplication.ui.book

import android.content.Intent
import android.os.BaseBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.core.adapter.BookAdapter
import com.example.myapplication.core.adapter.TernakAdapter
import com.example.myapplication.core.data.api.network.State
import com.example.myapplication.core.data.api.response.item.BookItem
import com.example.myapplication.core.data.api.response.item.TernakItem
import com.example.myapplication.databinding.ActivityDetailHistoryBinding
import com.example.myapplication.databinding.ActivityInseminasiBookBinding
import com.example.myapplication.ui.login.LoginActivity
import com.example.myapplication.ui.login.LoginViewModel
import com.example.myapplication.ui.ternak.DetailTernakActivity
import com.google.gson.Gson
import com.inyongtisto.myhelper.base.BaseActivity
import com.inyongtisto.myhelper.extension.toastError
import org.koin.androidx.viewmodel.ext.android.viewModel

class InseminasiBookActivity : BaseActivity() {
    private var _binding: ActivityInseminasiBookBinding? = null
    private val binding get() = _binding
    private var root: View? = null
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityInseminasiBookBinding.inflate(layoutInflater)
        root = binding?.root
        setContentView(root)
        getBook()
    }

    private fun getBook() {
        viewModel.book("Bearer ${LoginActivity.TOKEN_KEY}")
            .observe(this){
                when (it.state) {
                    State.SUCCESS -> {
                        progress.dismiss()
                        val response = it.data
                        val bookAdapter = BookAdapter()
                        bookAdapter.setData(response)
                        with(binding?.rvTernak) {
                            this?.adapter = bookAdapter
                            this?.layoutManager = StaggeredGridLayoutManager(
                                2,
                                StaggeredGridLayoutManager.VERTICAL,
                            )
                            this?.setHasFixedSize(true)
                        }
                        bookAdapter.setOnItemClickCallback((object :
                            BookAdapter.OnItemClickCallback {
                            override fun onItemClicked(news: BookItem) {
                                val intent = Intent(
                                    this@InseminasiBookActivity,
                                    WebViewActivity::class.java
                                )
                                intent.putExtra("book",news.file )
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