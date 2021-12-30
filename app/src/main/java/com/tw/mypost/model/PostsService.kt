package com.tw.mypost.model

import com.tw.mypost.di.DaggerApiComponent
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class PostsService {

    @Inject
    lateinit var api: PostsApi

    init{
        DaggerApiComponent.create().inject(this)
    }

    fun getPosts(): Single<List<MyPosts>> {
        return api.getPosts()
    }


    fun addPosts(myPost: MyPosts): Observable<Response<MyPosts>> {
        return api.addPosts(myPost)
    }
}