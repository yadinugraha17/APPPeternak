package com.example.myapplication.core.data.api.response.item

data class TernakItem(
    val id : Int,
    val no_regis : String,
    val nama_ternak : String,
    val status_melahirkan : String,
    val umur : String,
    val rumpun_id : Int,
    val rumpun: RumpunItem,
    val user_id : Int,
    val photo : String,
    val jenis_kelamin : String,
)
