package com.example.myapplication.core.data.api.response

import com.example.myapplication.core.data.api.response.item.LoginItem
import com.example.myapplication.core.data.api.response.item.RegisterItem

data class RegisterResponse(
    val status : String,
    val code : Int,
    val message : String,
    val data :  RegisterItem
)
