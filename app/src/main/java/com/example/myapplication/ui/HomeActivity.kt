package com.example.myapplication.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.ui.home.HomeFragment
import com.example.myapplication.ui.notifikasi.NotifikasiFragment
import com.example.myapplication.ui.profile.ProfileFragment

class HomeActivity : AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding
    private var root: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        root = binding?.root
        setContentView(root)

        binding?.menu?.setItemSelected(R.id.home)
        openMainFragment()
        binding?.menu?.setOnItemSelectedListener {
            when (it) {

                R.id.home -> {
                    openMainFragment()
                }

                R.id.notifikasi -> {
                    val notifikasiFragment = NotifikasiFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, notifikasiFragment).commit()

                }

                R.id.profile -> {
                    val profileFragment = ProfileFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, profileFragment).commit()
                }
            }
        }

    }

    private fun openMainFragment() {
        val homeFragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, homeFragment)
        transaction.commit()
    }
}