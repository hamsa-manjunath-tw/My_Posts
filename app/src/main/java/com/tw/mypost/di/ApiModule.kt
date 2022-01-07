package com.tw.mypost.di

import com.tw.mypost.model.PostsApi
import com.tw.mypost.model.PostsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val base_url = "https://jsonplaceholder.typicode.com"

    @Provides
    fun provideMyPostsApi(): PostsApi {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PostsApi::class.java)
    }

    @Provides
    fun provideMyPostsService(): PostsService {
        return PostsService()
    }

}