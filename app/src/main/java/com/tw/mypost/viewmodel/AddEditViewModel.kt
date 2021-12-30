package com.tw.mypost.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tw.mypost.model.MyPosts
import com.tw.mypost.model.PostsService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import io.reactivex.disposables.Disposable

class AddEditViewModel : ViewModel() {

    private val postsService = PostsService()
    private var post: MutableLiveData<MyPosts> = MutableLiveData<MyPosts>()
    private val disposable = CompositeDisposable()

    var isSuccess = MutableLiveData<Boolean>()

    fun refresh(myPost: MyPosts) {
        addPost(myPost)
    }

    private fun addPost(myPost: MyPosts) {

        post.value = myPost
        val response: Observable<Response<MyPosts>> = postsService.addPosts(myPost)
        response.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response<MyPosts>> {

                override fun onError(e: Throwable) {
                    isSuccess.value = false
                }

                override fun onNext(value: Response<MyPosts>?) {
                }

                override fun onComplete() {

                    isSuccess.value = true
                }

                override fun onSubscribe(d: Disposable?) {
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}