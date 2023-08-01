package com.example.myapplication.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.myapplication.core.data.api.request.FindRequest
import com.example.myapplication.core.data.api.request.InseminasiRequest
import com.example.myapplication.core.data.api.request.LoginRequest
import com.example.myapplication.core.data.api.request.RegisterRequest
import com.example.myapplication.core.repository.CoreRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class LoginViewModel (private val coreRepository: CoreRepository):ViewModel() {
    fun login (data:LoginRequest)= coreRepository.login(data).asLiveData()
    fun register (data:RegisterRequest)= coreRepository.register(data).asLiveData()
    fun profile (token:String)= coreRepository.profile(token).asLiveData()
    fun editprofile (token:String)= coreRepository.editprofil(token).asLiveData()
    fun province ()= coreRepository.province().asLiveData()
    fun regency (id: Int)= coreRepository.regency(id).asLiveData()
    fun district (id: Int)= coreRepository.district(id).asLiveData()
    fun village (id: Int)= coreRepository.village(id).asLiveData()
    fun study ()= coreRepository.study().asLiveData()
    fun rumpun ()= coreRepository.rumpun().asLiveData()
    fun ternak (token: String)= coreRepository.ternak(token).asLiveData()
    fun addternak (token: String, photo: MultipartBody.Part,
                   namaternak: RequestBody,
                   statusmelahirkan: RequestBody,
                   umur: RequestBody,
                   rumpun: RequestBody,
                   jeniskelamin: RequestBody
    )=coreRepository.addternak(token, photo, namaternak, statusmelahirkan, umur, rumpun, jeniskelamin).asLiveData()
    fun editternak (token: String, photo: MultipartBody.Part?,
                   id: Int,
                   namaternak: RequestBody,
                   statusmelahirkan: RequestBody,
                   umur: RequestBody,
                   rumpun: RequestBody,
                   jeniskelamin: RequestBody
    )=coreRepository.editternak(token, photo, id, namaternak, statusmelahirkan, umur, rumpun, jeniskelamin).asLiveData()
    fun delternak (token: String, id: Int)= coreRepository.delternak(token, id).asLiveData()
    fun findib(token: String, data: FindRequest)=coreRepository.findib(token,data).asLiveData()
    fun inseminasi(token: String, data: InseminasiRequest)=coreRepository.inseminasi(token,data).asLiveData()
    fun history(token: String)=coreRepository.history(token).asLiveData()
    fun notifikasi(token: String)=coreRepository.notifikasi(token).asLiveData()
}