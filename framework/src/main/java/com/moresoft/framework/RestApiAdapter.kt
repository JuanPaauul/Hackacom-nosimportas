package com.moresoft.framework

import com.moresoft.domain.ConstantsRestApi
import com.moresoft.usercases.EndPointApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestApiAdapter {
    fun connectionApi(): EndPointApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(ConstantsRestApi.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(EndPointApi::class.java)
    }
}
