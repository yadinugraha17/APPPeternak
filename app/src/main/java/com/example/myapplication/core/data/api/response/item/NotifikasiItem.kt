package com.example.myapplication.core.data.api.response.item

import com.google.gson.annotations.SerializedName

data class NotifikasiItem(
    val id : Int,
    val notif : String,
    val user_id : Int,
    val insiminasi : Int,
    val status : String,
    val create_at : String,
    val update_at : String,
    @field:SerializedName("status_read")
    var isRead : String
)
