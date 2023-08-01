package com.example.myapplication.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.myapplication.core.session.SessionRepository
import com.example.myapplication.core.session.SessionViewModel
import com.example.myapplication.core.utils.ViewModelUserFactory
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.ui.HomeActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class LoginActivity : AppCompatActivity() {
    private var adapter: ViewPageAdapter? = null
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding
    private var root: View? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        root = binding?.root
        setContentView(root)




        binding?.tabLayout?.addTab(binding?.tabLayout?.newTab()!!.setText("Login"))
        binding?.tabLayout?.addTab(binding?.tabLayout?.newTab()!!.setText("Signup"))
        val fragmentManager: FragmentManager = supportFragmentManager
        adapter = ViewPageAdapter(fragmentManager, lifecycle)
        binding?.viewPager?.adapter = adapter
        binding?.tabLayout?.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding?.viewPager?.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        binding?.viewPager?.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding?.tabLayout?.selectTab(binding?.tabLayout?.getTabAt(position))
            }
        })

    }

    private fun intent() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    companion object {
        var TOKEN_KEY = "token"

    }

}