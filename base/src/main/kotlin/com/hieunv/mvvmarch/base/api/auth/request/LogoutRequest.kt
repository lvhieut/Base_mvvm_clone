package com.hieunv.mvvmarch.base.api.auth.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogoutRequest(

        @Json(name = "fcm_token")
        val fcmToken: String

)
