package com.example.myapplication.ui.submission

import android.R
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myapplication.core.data.api.network.State
import com.example.myapplication.core.data.api.request.InseminasiRequest
import com.example.myapplication.databinding.ActivityMengajukanIbBinding
import com.example.myapplication.ui.dialog.DatePickerFragment
import com.example.myapplication.ui.dialog.TimePickerFragment
import com.example.myapplication.ui.history.HistoryActivity
import com.example.myapplication.ui.login.LoginActivity
import com.example.myapplication.ui.login.LoginViewModel
import com.inyongtisto.myhelper.base.BaseActivity
import com.inyongtisto.myhelper.extension.toastError
import com.inyongtisto.myhelper.extension.toastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class MengajukanIbActivity : BaseActivity(), TimePickerFragment.DialogTimeListener {
    private var _binding: ActivityMengajukanIbBinding? = null
    private val binding get() = _binding
    private var root: View? = null
    private val viewModel: LoginViewModel by viewModel()
    private var rumpunid = 0
    private var ternakid = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMengajukanIbBinding.inflate(layoutInflater)
        root = binding?.root
        setContentView(root)
        getTernak()
        binding?.btAjukan?.setOnClickListener {
            ajukan()
        }
        val rumpun = intent.getStringExtra("rumpun")
        binding?.etRumpun?.setText(rumpun)

        binding?.ibDate?.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            val mFragmentManager = supportFragmentManager
            mFragmentManager.setFragmentResultListener(
                "REQUEST_KEY",
                this@MengajukanIbActivity

            ) { resultKey, bundle ->
                if (resultKey == "REQUEST_KEY") {
                    val date = bundle.getString("SELECTED_DATE")
                    binding?.tvDate?.setText(date)
                }

            }
            datePickerFragment.show(
                mFragmentManager,
                "com.example.myapplication.ui.dialog.DatePickerFragment"
            )
        }
        binding?.ibTime?.setOnClickListener {
            val timePickerFragmentOne = TimePickerFragment()
            timePickerFragmentOne.show(supportFragmentManager, TIME_PICKER_ONCE_TAG)
        }
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {

        // Siapkan time formatter-nya terlebih dahulu
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        // Set text dari textview berdasarkan tag
        when (tag) {
            TIME_PICKER_ONCE_TAG -> binding?.tvTime?.setText(dateFormat.format(calendar.time))
            else -> {
            }
        }
    }

    companion object {

        private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
    }

    private fun getTernak() {
        val arrayString = ArrayList<String>()
        arrayString.add("Ternak")
        viewModel.ternak("Bearer ${LoginActivity.TOKEN_KEY}").observe(this@MengajukanIbActivity) {
            when (it.state) {
                State.SUCCESS -> {
                    val response = it.data
                    response?.forEach { ternak ->
                        arrayString.add(ternak.nama_ternak)
                    }
                    val arrayAdapter = ArrayAdapter<String>(
                        this@MengajukanIbActivity,
                        R.layout.simple_spinner_dropdown_item,
                        arrayString
                    )

                    binding?.spTernak?.adapter = arrayAdapter
                    binding?.etTernak?.setOnClickListener {
                        binding?.spTernak?.performClick()
                    }
                    binding?.spTernak?.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (position != 0) {
                                    val ternak = response?.get(position - 1)
                                    binding?.etTernak?.setText(ternak?.nama_ternak)
                                    ternakid = ternak?.id ?: 0
                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }

                        }
                }

                else -> {}
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun ajukan() {
        rumpunid = intent.getIntExtra("rumpunId", 0)
        val inseminaratorId = intent.getIntExtra("inseminarator_id", 0)
        val waktu = binding?.tvDate?.text
        val time = binding?.tvTime?.text

        if (ternakid == 0 || waktu.isNullOrEmpty() || time.isNullOrEmpty()) {
            // Tampilkan pesan bahwa waktu dan waktu harus diisi
            Toast.makeText(this, "Semua data harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val date = LocalDate.parse(waktu, inputFormatter)
        val outputDateString = date.format(outputFormatter)

        val data = InseminasiRequest(
            ternakid,
            rumpunid,
            outputDateString.toString(),
            time.toString(),
            inseminaratorId,
            "0.0_0.0"
        )
        viewModel.inseminasi("Bearer ${LoginActivity.TOKEN_KEY}", data)
            .observe(this) {
                when (it.state) {
                    State.SUCCESS -> {
                        progress.dismiss()
                        toastSuccess("Pengajuan Berhasil!")
                        val intent = Intent(this@MengajukanIbActivity, HistoryActivity::class.java)
                        startActivity(intent)
                    }

                    State.LOADING -> {
                        progress.show()
                    }

                    State.ERROR -> {
                        progress.dismiss()
                        toastSuccess("Pengajuan Berhasil!")
                        val intent = Intent(this@MengajukanIbActivity, HistoryActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
    }
}