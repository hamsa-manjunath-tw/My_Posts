package com.tw.mypost.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tw.mypost.model.MyPosts
import com.tw.mypost.model.PostsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel : ViewModel() {

    private val postsService = PostsService()
    private val disposable = CompositeDisposable()

    val posts = MutableLiveData<List<MyPosts>>()
    val postsLoadError = MutableLiveData<Boolean>()
    val loadingError = MutableLiveData<Boolean>()

    fun refresh() {
        fetchPosts()
    }

    private fun fetchPosts() {
        loadingError.value = true
        disposable.add(
            postsService.getPosts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<MyPosts>>() {
                    override fun onSuccess(value: List<MyPosts>?) {
                        posts.value = value
                        postsLoadError.value = false
                        loadingError.value = false
                    }

                    override fun onError(e: Throwable?) {
                        postsLoadError.value = true
                        loadingError.value = false
                    }

                })
        )
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}