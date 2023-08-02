package com.example.myapplication.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.core.data.api.network.State
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.book.InseminasiBookActivity
import com.example.myapplication.ui.history.HistoryActivity
import com.example.myapplication.ui.login.LoginActivity.Companion.TOKEN_KEY
import com.example.myapplication.ui.login.LoginViewModel
import com.example.myapplication.ui.submission.MengajukanIbActivity
import com.example.myapplication.ui.submission.PengajuanActivity
import com.example.myapplication.ui.ternak.TernakActivity
import com.inyongtisto.myhelper.base.BaseFragment
import com.inyongtisto.myhelper.extension.toastError
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private var root: View? = null
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        root = binding?.root
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.llTernak?.setOnClickListener {
            val intent = Intent (requireContext(), TernakActivity::class.java)
            startActivity(intent)
        }
        binding?.llPengajuan?.setOnClickListener {
            val intent = Intent (requireContext(), PengajuanActivity::class.java)
            startActivity(intent)
        }
        binding?.llHistory?.setOnClickListener{
            val intent = Intent (requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }
        binding?.llInseminasi?.setOnClickListener{
            val intent = Intent (requireContext(), InseminasiBookActivity::class.java)
            startActivity(intent)
        }
        profile()
    }
    private fun profile() {
        viewModel.profile("Bearer $TOKEN_KEY")
            .observe(viewLifecycleOwner) {
                when (it.state) {
                    State.SUCCESS -> {
                        val respons = it.data
                        binding?.name?.text = respons?.get(0)?.nama
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
}