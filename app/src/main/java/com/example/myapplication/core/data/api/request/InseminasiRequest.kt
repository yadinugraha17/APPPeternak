package com.example.myapplication.core.data.api.request

data class InseminasiRequest(
    val ternak_id : Int,
    val rumpun_id : Int,
    val waktu_birahi : String,
    val jam_birahi : String,
    val insiminator_id : Int,
    val kordinat : String
)
