package com.example.myapplication.core.data.api.response.item

data class ProfileItem(
    val id: Int,
    val alamat: String,
    val district: District,
    val district_id: String,
    val lama_beternak: String,
    val nama: String,
    val nik: String,
    val no_hp: String,
    val pendidikan: Pendidikan,
    val pendidikan_id: String,
    val province: Province,
    val province_id: String,
    val regency: Regency,
    val regency_id: String,
    val village: Village,
    val village_id: String
)