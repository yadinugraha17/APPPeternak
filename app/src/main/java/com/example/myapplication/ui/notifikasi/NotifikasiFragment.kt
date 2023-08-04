package com.example.myapplication.ui.notifikasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.core.adapter.NotifikasiAdapter
import com.example.myapplication.core.data.api.network.State
import com.example.myapplication.core.data.api.response.item.NotifikasiItem
import com.example.myapplication.databinding.FragmentNotifikasiBinding
import com.example.myapplication.ui.HomeActivity
import com.example.myapplication.ui.login.LoginActivity
import com.example.myapplication.ui.login.LoginViewModel
import com.inyongtisto.myhelper.base.BaseFragment
import com.inyongtisto.myhelper.extension.toastError
import com.inyongtisto.myhelper.extension.toastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotifikasiFragment : BaseFragment() {
    private var _binding: FragmentNotifikasiBinding? = null
    private val binding get() = _binding
    private var root: View? = null
    private val viewModel: LoginViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotifikasiBinding.inflate(layoutInflater)
        root = binding?.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notifikasi()
    }

    private fun refreshActivityContent() {
        // Call the refreshActivityContent() method of the parent Activity
        (activity as? HomeActivity)?.refreshActivityContent()
    }

    // Call this method when you need to refresh the Activity
    fun performRefresh() {

        refreshActivityContent()}
    private fun notifikasi() {
        viewModel.notifikasi("Bearer ${LoginActivity.TOKEN_KEY}")
            .observe(viewLifecycleOwner) {
                when (it.state) {
                    State.SUCCESS -> {
                        progress.dismiss()
                        val response = it.data
                        val notifikasiAdapter = NotifikasiAdapter()
                        notifikasiAdapter.setData(response)
                        with(binding?.rvNotif) {
                            this?.adapter = notifikasiAdapter
                            this?.layoutManager = LinearLayoutManager(
                                requireContext(),
                                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                                false
                            )
                            this?.setHasFixedSize(true)
                        }
                        notifikasiAdapter.setOnItemClickCallback((object :
                            NotifikasiAdapter.OnItemClickCallback {
                            override fun onItemClicked(news: NotifikasiItem) {
                                viewModel.upnotif("Bearer ${LoginActivity.TOKEN_KEY}", news.id)
                                    .observe(viewLifecycleOwner) {
                                        when (it.state) {
                                            State.SUCCESS -> {
                                                notifikasi()
                                                performRefresh()
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