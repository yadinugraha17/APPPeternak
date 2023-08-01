package com.example.myapplication.core.repository

import com.example.myapplication.core.data.api.RemoteDataSource
import com.example.myapplication.core.data.api.network.Resource
import com.example.myapplication.core.data.api.request.FindRequest
import com.example.myapplication.core.data.api.request.InseminasiRequest
import com.example.myapplication.core.data.api.request.LoginRequest
import com.example.myapplication.core.data.api.request.RegisterRequest
import com.example.myapplication.core.data.api.response.DataResponse
import com.example.myapplication.core.data.api.response.LoginRespon
import com.inyongtisto.myhelper.extension.getErrorBody
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception

class CoreRepository (private val remoteDataSource: RemoteDataSource)  {
    fun login (data : LoginRequest)= flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.login(data).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                }
                else {
                    emit(Resource.error(it.getErrorBody(LoginRespon::class.java)?.message ?: "",null))
                }
            }
        }
        catch (e: Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan",null))}
    }
    fun register (data: RegisterRequest)= flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.register(data).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                }
                else {
                    emit(Resource.error(it.getErrorBody(LoginRespon::class.java)?.message ?:"", null))
                }
            }
        }
        catch (e: Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan", null))}
    }
    fun province () = flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.province().let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                }
                else {
                    emit(Resource.error(it.getErrorBody(DataResponse::class.java)?.message ?:"", null))
                }
            }
        }
        catch (e: Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan", null))}
    }
    fun regency (id: Int) = flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.regency(id).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                    }
                else {
                    emit(Resource.error(it.getErrorBody(DataResponse::class.java)?.message ?:"", null))
                }
            }
        }
        catch (e:Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan", null))}
    }
    fun district (id: Int) = flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.district(id).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                }
                else {
                    emit(Resource.error(it.getErrorBody(DataResponse::class.java)?.message ?:"", null))
                }
            }
        }
        catch (e:Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan", null))}
    }
    fun village (id: Int) = flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.village(id).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                }
                else {
                    emit(Resource.error(it.getErrorBody(DataResponse::class.java)?.message ?:"", null))
                }
            }
        }
        catch (e:Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan", null))}
    }
    fun study () = flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.study().let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                }
                else {
                    emit(Resource.error(it.getErrorBody(DataResponse::class.java)?.message ?:"", null))
                }
            }
        }
        catch (e: Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan", null))}
    }
    fun profile (token:String)= flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.profile(token).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                }
                else {
                    emit(Resource.error(it.getErrorBody(LoginRespon::class.java)?.message ?:"", null))
                }
            }
        }
        catch (e: Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan", null))}
    }
    fun editprofil (token:String)= flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.profile(token).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                }
                else {
                    emit(Resource.error(it.getErrorBody(LoginRespon::class.java)?.message ?:"", null))
                }
            }
        }
        catch (e: Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan", null))}
    }

    fun rumpun () = flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.rumpun().let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                }
                else {
                    emit(Resource.error(it.getErrorBody(DataResponse::class.java)?.message ?:"", null))
                }
            }
        }
        catch (e: Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan", null))}
    }

    fun ternak (token: String)= flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.ternak(token).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                }
                else {
                    emit(Resource.error(it.getErrorBody(LoginRespon::class.java)?.message ?:"", null))
                }
            }
        }
        catch (e: Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan", null))}
    }

    fun addternak (token: String,
                   photo: MultipartBody.Part,
                   namaternak: RequestBody,
                   statusmelahirkan: RequestBody,
                   umur: RequestBody,
                   rumpun: RequestBody,
                   jeniskelamin: RequestBody
    )= flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.addternak(token, photo, namaternak, statusmelahirkan, umur, rumpun, jeniskelamin).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                }
                else {
                    emit(Resource.error(it.getErrorBody(LoginRespon::class.java)?.message ?:"", null))
                }
            }
        }
        catch (e: Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan", null))}
    }

    fun editternak (token: String,
                   photo: MultipartBody.Part?,
                   id: Int,
                   namaternak: RequestBody,
                   statusmelahirkan: RequestBody,
                   umur: RequestBody,
                   rumpun: RequestBody,
                   jeniskelamin: RequestBody
    )= flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.editternak(token, photo, id, namaternak, statusmelahirkan, umur, rumpun, jeniskelamin).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                }
                else {
                    emit(Resource.error(it.getErrorBody(LoginRespon::class.java)?.message ?:"", null))
                }
            }
        }
        catch (e: Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan", null))}
    }

    fun delternak (token: String, id: Int) = flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.delternak(token, id).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                }
                else {
                    emit(Resource.error(it.getErrorBody(DataResponse::class.java)?.message ?:"", null))
                }
            }
        }
        catch (e:Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan", null))}
    }
    fun findib (token: String, data: FindRequest) = flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.findib(token, data).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                }
                else {
                    emit(Resource.error(it.getErrorBody(DataResponse::class.java)?.message ?:"", null))
                }
            }
        }
        catch (e:Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan", null))}
    }
    fun inseminasi (token: String, data: InseminasiRequest) = flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.inseminasi(token, data).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                }
                else {
                    emit(Resource.error(it.getErrorBody(DataResponse::class.java)?.message ?:"", null))
                }
            }
        }
        catch (e:Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan", null))}
    }
    fun history (token: String) = flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.history(token).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                }
                else {
                    emit(Resource.error(it.getErrorBody(DataResponse::class.java)?.message ?:"", null))
                }
            }
        }
        catch (e:Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan", null))}
    }
    fun notifikasi (token: String) = flow {
        emit(Resource.loading(null))
        try {
            remoteDataSource.notifikasi(token).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    emit(Resource.success(user))
                }
                else {
                    emit(Resource.error(it.getErrorBody(DataResponse::class.java)?.message ?:"", null))
                }
            }
        }
        catch (e:Exception){emit(Resource.error(e.message?:"Terjadi Kesalahan", null))}
    }
}