package com.example.myapplication.core.data.api.response.item

data class NotifikasiItem(
    val id : Int,
    val notif : String,
    val user_id : Int,
    val insiminasi : Int,
    val status : String,
    val create_at : String,
    val update_at : String,
    var isRead : Boolean
)
