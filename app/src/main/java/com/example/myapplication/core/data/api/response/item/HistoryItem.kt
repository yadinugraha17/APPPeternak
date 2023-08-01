package com.example.myapplication.core.data.api.response.item

data class HistoryItem(
    val id: Int,
    val peternak_id: Int,
    val ternak_id: Int,
    val waktu_birahi: String,
    val jam_birahi: String,
    val status_birahi: String,
    val status: String,
    val kordinat: String,
    val ib: String,
    val tgl_ib: String,
    val status_kebuntingan: String,
    val tgl_pkb: String,
    val status_lahir: String,
    val create_at: String,
    val update_at: String,
    val peternak: ProfileItem,
    val rumpun: RumpunItem,
    val ternak: TernakItem,
    val insiminator: InseminatorItem
)
