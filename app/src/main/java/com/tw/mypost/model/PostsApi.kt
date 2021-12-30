package com.tw.mypost.model

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface PostsApi {
    @GET("posts")
    fun getPosts(): Single<List<MyPosts>>

    @POST("posts")
    fun addPosts(@Body myPost: MyPosts): Observable<Response<MyPosts>>

}