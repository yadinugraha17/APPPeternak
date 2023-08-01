package com.example.myapplication.core.data.api.request

data class RegisterRequest(
    val nik: String,
    val nama: String,
    val no_hp: String,
    val alamat: String,
    val pendidikan: Int,
    val lama_berternak : String,
    val province_id: Int,
    val regency_id: Int,
    val district_id: Int,
    val village_id: String,
    val password: String
)
