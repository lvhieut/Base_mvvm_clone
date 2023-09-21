package com.hieunv.mvvmarch.base.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post(

        @Json(name = "id")
        val id: Long,

        @Json(name = "title")
        val title: String,

        @Json(name = "image")
        val image: String,

        @Json(name = "publish_date")
        val publishAt: String

)
