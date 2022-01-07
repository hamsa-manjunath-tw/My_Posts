package com.tw.mypost.model

import com.tw.mypost.di.DaggerApiComponent
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import javax.inject.Inject

class PostsService {

    @Inject
    lateinit var api: PostsApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getPosts(): Single<List<MyPosts>> {
        return api.getPosts()
    }

    fun addPosts(myPost: MyPosts): Observable<Response<MyPosts>> {
        return api.addPosts(myPost)
    }

    fun editPosts(myPost: MyPosts): Observable<Response<MyPosts>> {
        return api.editPosts(myPost, myPost.id.toLong())
    }

    fun deletePosts(id: Long): Observable<Response<ResponseBody>> {
        return api.deletePosts(id)
    }
}