package com.example.myapplication.core.data.api.response

data class DataResponse<T>(
    val status : String,
    val code : Int,
    val message : String,

    val data : T
)
