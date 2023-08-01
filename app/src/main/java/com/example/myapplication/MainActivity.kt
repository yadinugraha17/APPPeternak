package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.core.session.SessionRepository
import com.example.myapplication.core.session.SessionViewModel
import com.example.myapplication.core.utils.ViewModelUserFactory
import com.example.myapplication.ui.HomeActivity
import com.example.myapplication.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore("settings")
    private lateinit var sessionViewModel: SessionViewModel
    var state = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionViewModel = ViewModelProvider(
            this, ViewModelUserFactory(
                SessionRepository.getInstance(datastore)
            )
        )[SessionViewModel::class.java]

        lifecycleScope.launchWhenCreated {
            sessionViewModel.getUser().collect {
                LoginActivity.TOKEN_KEY = it.token
                state = it.state
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (!state) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)

    }

}