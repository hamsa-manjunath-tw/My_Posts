package com.tw.mypost.di

import com.tw.mypost.model.PostsService
import dagger.Component

@Component(modules =[ApiModule::class])
interface ApiComponent {
    fun inject(service: PostsService)
}
