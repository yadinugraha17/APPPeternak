package com.example.myapplication.core.data.api

import com.example.myapplication.core.data.api.network.ApiService
import com.example.myapplication.core.data.api.request.FindRequest
import com.example.myapplication.core.data.api.request.InseminasiRequest
import com.example.myapplication.core.data.api.request.LoginRequest
import com.example.myapplication.core.data.api.request.RegisterRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RemoteDataSource (private val apiService : ApiService){
    suspend fun login (data : LoginRequest) = apiService.login(data)
    suspend fun register (data : RegisterRequest) = apiService.register(data)
    suspend fun province () = apiService.province()
    suspend fun regency (id:Int) = apiService.regency(id)
    suspend fun district (id:Int) = apiService.district(id)
    suspend fun village (id:Int) = apiService.village(id)
    suspend fun study () = apiService.study()
    suspend fun profile (token:String) = apiService.profile(token)
    suspend fun editprofil(token: String) = apiService.editprofile(token)
    suspend fun rumpun () = apiService.rumpun()
    suspend fun ternak (token: String) = apiService.ternak(token)
    suspend fun addternak (token: String,
                           photo: MultipartBody.Part,
                           namaternak: RequestBody,
                           statusmelahirkan:RequestBody,
                           umur:RequestBody,
                           rumpun:RequestBody,
                           jeniskelamin:RequestBody) = apiService.addternak(token, photo, namaternak, statusmelahirkan, umur, rumpun, jeniskelamin)

    suspend fun editternak (token: String,
                           photo: MultipartBody.Part?,
                           id: Int,
                           namaternak: RequestBody,
                           statusmelahirkan:RequestBody,
                           umur:RequestBody,
                           rumpun:RequestBody,
                           jeniskelamin:RequestBody) = apiService.editternak(token, photo, id, namaternak, statusmelahirkan, umur, rumpun, jeniskelamin)

    suspend fun delternak(token: String, id: Int) = apiService.delternak(token, id)

    suspend fun findib(token: String, data: FindRequest) = apiService.findib(token, data)

    suspend fun inseminasi(token: String, data: InseminasiRequest) = apiService.inseminasi(token, data)

    suspend fun history(token: String) = apiService.history(token)

    suspend fun notifikasi(token: String) = apiService.notifikasi(token)

}