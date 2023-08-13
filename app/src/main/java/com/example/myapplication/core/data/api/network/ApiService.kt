package com.example.myapplication.core.data.api.network

import com.example.myapplication.core.data.api.request.FindRequest
import com.example.myapplication.core.data.api.request.InseminasiRequest
import com.example.myapplication.core.data.api.request.LoginRequest
import com.example.myapplication.core.data.api.request.ProfileRequest
import com.example.myapplication.core.data.api.request.RegisterRequest
import com.example.myapplication.core.data.api.response.DataResponse
import com.example.myapplication.core.data.api.response.LoginRespon
import com.example.myapplication.core.data.api.response.RegisterResponse
import com.example.myapplication.core.data.api.response.item.BookItem
import com.example.myapplication.core.data.api.response.item.DistrictItem
import com.example.myapplication.core.data.api.response.item.FindItem
import com.example.myapplication.core.data.api.response.item.HistoryItem
import com.example.myapplication.core.data.api.response.item.InseminasiItem
import com.example.myapplication.core.data.api.response.item.NotifItem
import com.example.myapplication.core.data.api.response.item.NotifikasiItem
import com.example.myapplication.core.data.api.response.item.PendItem
import com.example.myapplication.core.data.api.response.item.ProfileItem
import com.example.myapplication.core.data.api.response.item.ProvinceItem
import com.example.myapplication.core.data.api.response.item.RegencyItem
import com.example.myapplication.core.data.api.response.item.RumpunItem
import com.example.myapplication.core.data.api.response.item.TernakItem
import com.example.myapplication.core.data.api.response.item.VillageItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("login")
    suspend fun login(@Body login: LoginRequest): Response<LoginRespon>

    @POST("daftar-peternak")
    suspend fun register(@Body register: RegisterRequest): Response<RegisterResponse>

    @GET("provinsi")
    suspend fun province(): Response<DataResponse<List<ProvinceItem>>>

    @GET("kabupaten/{id}")
    suspend fun regency(@Path("id") id: Int): Response<DataResponse<List<RegencyItem>>>

    @GET("kecamatan/{id}")
    suspend fun district(@Path("id") id: Int): Response<DataResponse<List<DistrictItem>>>

    @GET("desa/{id}")
    suspend fun village(@Path("id") id: Int): Response<DataResponse<List<VillageItem>>>

    @GET("pendidikan")
    suspend fun study(): Response<DataResponse<List<PendItem>>>

    @GET("detail-peternak")
    suspend fun profile(@Header("Authorization") token: String): Response<DataResponse<List<ProfileItem>>>

    @PUT("update/peternak")
    suspend fun editprofile(@Header("Authorization") token: String, @Body profile: ProfileRequest): Response<DataResponse<List<ProfileItem>>>

    @GET("rumpun")
    suspend fun rumpun(): Response<DataResponse<List<RumpunItem>>>

    @GET("ternak")
    suspend fun ternak(@Header("Authorization") token: String): Response<DataResponse<List<TernakItem>>>

    @POST("ternak")
    @Multipart
    suspend fun addternak(
        @Header("Authorization") token: String,
        @Part partFile: MultipartBody.Part,
        @Part("nama_ternak") namaternak: RequestBody,
        @Part("status_melahirkan") statusmelahirkan: RequestBody,
        @Part("umur") umur: RequestBody,
        @Part("rumpun") rumpun: RequestBody,
        @Part("jenis_kelamin") jenis_kelamin: RequestBody,
    ): Response<DataResponse<List<TernakItem>>>

    @POST("ternak/{id}")
    @Multipart
    suspend fun editternak(
        @Header("Authorization") token: String,
        @Part partFile: MultipartBody.Part?,
        @Path("id") id: Int,
        @Part("nama_ternak") namaternak: RequestBody,
        @Part("status_melahirkan") statusmelahirkan: RequestBody,
        @Part("umur") umur: RequestBody,
        @Part("rumpun") rumpun: RequestBody,
        @Part("jenis_kelamin") jenis_kelamin: RequestBody,
    ): Response<DataResponse<TernakItem>>

    @DELETE("delete-ternak/{id}")
    suspend fun delternak(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<DataResponse<List<TernakItem>>>

    @POST("find/insiminator")
    suspend fun findib(
        @Header("Authorization") token: String,
        @Body findRequest: FindRequest
    ): Response<DataResponse<List<FindItem>>>

    @POST("insiminasi")
    suspend fun inseminasi(
        @Header("Authorization") token: String,
        @Body inseminasiRequest: InseminasiRequest
    ): Response<DataResponse<List<InseminasiItem>>>

    @GET("riwayat/pengajuan/peternak")
    suspend fun history(@Header("Authorization") token: String): Response<DataResponse<List<HistoryItem>>>

    @GET("jumlah/notifikasi")
    suspend fun countnotif(@Header("Authorization") token: String): Response<NotifItem>

    @GET("notifikasi")
    suspend fun notifikasi(@Header("Authorization") token: String): Response<DataResponse<List<NotifikasiItem>>>

    @PUT("update/notifikasi/{id}")
    suspend fun upnotif(@Header("Authorization") token: String, @Path("id") id: Int): Response<NotifItem>

    @GET("book")
    suspend fun book(@Header("Authorization") token: String): Response<DataResponse<List<BookItem>>>
}