package com.tw.mypost.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tw.mypost.di.DaggerApiComponent
import com.tw.mypost.model.MyPosts
import com.tw.mypost.model.PostsService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class AddEditViewModel : ViewModel() {

    @Inject
    lateinit var postsService: PostsService

    init {
        DaggerApiComponent.create().inject(this)
    }

    var post: MutableLiveData<MyPosts> = MutableLiveData<MyPosts>()
    private val disposable = CompositeDisposable()

    var isSuccess: Boolean = false

    fun refresh(myPost: MyPosts, action: String) {
        if (action.equals("ADD")) {
            addPost(myPost)
        } else if (action.equals("UPDATE")) {
            editPost(myPost)
        }

    }

    private fun addPost(myPost: MyPosts) {

        post.value = myPost
        val response: Observable<Response<MyPosts>> = postsService.addPosts(myPost)
        response.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response<MyPosts>> {

                override fun onError(e: Throwable) {
                    isSuccess = false
                }

                override fun onNext(value: Response<MyPosts>?) {
                    if (value?.isSuccessful == true) {
                        isSuccess = true
                    }
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable?) {
                }
            })
    }

    private fun editPost(myPost: MyPosts) {
        if (myPost != null) {

            post.value = myPost
            val response: Observable<Response<MyPosts>> = postsService.editPosts(myPost)
            response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Response<MyPosts>> {

                    override fun onError(e: Throwable) {
                        isSuccess = false
                    }

                    override fun onNext(value: Response<MyPosts>?) {
                        if (value?.isSuccessful == true) {
                            isSuccess = true
                        }
                    }

                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }
                })

        }

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}