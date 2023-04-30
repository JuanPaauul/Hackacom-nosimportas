package com.moresoft.usercases

import com.moresoft.domain.ConfidenceUser
import com.moresoft.domain.ConstantsRestApi
import retrofit2.Call
import retrofit2.http.GET

interface EndPointApi {
    @GET(ConstantsRestApi.POSTS)
    fun getAllPost(): Call<List<ConfidenceUser>>
}
