package com.tw.mypost.model

import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface PostsApi {
    @GET("posts")
    fun getPosts(): Single<List<MyPosts>>

    @POST("posts")
    fun addPosts(@Body myPost: MyPosts): Observable<Response<MyPosts>>

    @PUT("posts/{id}")
    fun editPosts(@Body myPost: MyPosts, @Path("id") id: Long): Observable<Response<MyPosts>>

    @DELETE("posts/{id}")
    fun deletePosts(@Path("id") id: Long): Observable<Response<ResponseBody>>
}