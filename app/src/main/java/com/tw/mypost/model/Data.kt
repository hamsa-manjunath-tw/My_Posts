package com.tw.mypost.model

import com.google.gson.annotations.SerializedName

data class MyPosts(
    @SerializedName("userId")
    val userId: Long,
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String?
)