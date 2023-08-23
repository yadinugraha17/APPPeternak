package com.example.myapplication.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.core.data.api.network.State
import com.example.myapplication.core.data.api.request.LoginRequest
import com.example.myapplication.core.data.api.response.item.LoginItem
import com.example.myapplication.core.session.SessionRepository
import com.example.myapplication.core.session.SessionViewModel
import com.example.myapplication.core.utils.ViewModelUserFactory
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.ui.HomeActivity
import com.example.myapplication.ui.login.LoginActivity.Companion.TOKEN_KEY
import com.inyongtisto.myhelper.base.BaseFragment
import com.inyongtisto.myhelper.extension.toastError
import com.inyongtisto.myhelper.extension.toastSuccess
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    private var root: View? = null
    private val viewModel: LoginViewModel by viewModel()
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore("settings")
    private lateinit var sessionViewModel: SessionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        root = binding?.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionViewModel = ViewModelProvider(
            this, ViewModelUserFactory(
                SessionRepository.getInstance(requireContext().datastore)
            )
        )[SessionViewModel::class.java]
        binding?.login?.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val hp = binding?.nohp?.text
        val pass = binding?.pass?.text
        if (hp.isNullOrEmpty() || pass.isNullOrEmpty()) {
            // Tampilkan pesan bahwa nomor HP dan password harus diisi
            toastError("Nomor HP dan password harus diisi")
            return
        }
        val data = LoginRequest(hp.toString(), pass.toString())
        viewModel.login(data).observe(viewLifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    val respon = it.data
                    val token = respon!![0].token
                    TOKEN_KEY = token
                    val login = LoginItem(token, respon[0].user_id, true)
                    val sessionRepository =
                        SessionRepository.getInstance(requireContext().datastore)
                    lifecycleScope.launch {
                        sessionRepository.saveUser(login)
                    }

                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    startActivity(intent)
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