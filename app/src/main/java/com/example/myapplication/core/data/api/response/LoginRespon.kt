package com.example.myapplication.core.data.api.response

import com.example.myapplication.core.data.api.response.item.LoginItem

data class LoginRespon(
    val status : String,
    val code : Int,
    val message : String,
    val data : List <LoginItem>
)
