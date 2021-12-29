package com.tw.mypost.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PostsService {

    private val base_url = "https://jsonplaceholder.typicode.com"
    private val api: PostsApi
    init{
        api = Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PostsApi::class.java)
    }

    fun getPosts(): Single<List<MyPosts>> {
        return api.getPosts()
    }
}