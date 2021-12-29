package com.tw.mypost.model

import io.reactivex.Single
import retrofit2.http.GET

interface PostsApi {
    @GET("posts")
    fun getPosts(): Single<List<MyPosts>>

}