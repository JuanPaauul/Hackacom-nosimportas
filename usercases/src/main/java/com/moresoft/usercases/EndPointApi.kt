package com.moresoft.usercases

import com.moresoft.domain.ConfidenceUser
import com.moresoft.domain.ConstantsRestApi
import com.moresoft.domain.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface EndPointApi {
    @GET(ConstantsRestApi.POSTS)
    fun getAllPost(): Call<List<User>>
    //fun getAllPost(): Call<List<ConfidenceUser>>
}
