package com.tw.mypost.di

import com.tw.mypost.model.PostsService
import com.tw.mypost.viewmodel.AddEditViewModel
import com.tw.mypost.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: PostsService)

    fun inject(listViewModel: ListViewModel)

    fun inject(addEditViewModel: AddEditViewModel)
}
