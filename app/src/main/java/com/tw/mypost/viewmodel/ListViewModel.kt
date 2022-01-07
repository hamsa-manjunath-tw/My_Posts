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
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ListViewModel : ViewModel() {

    @Inject
    lateinit var postsService: PostsService

    //private val postsService = PostsService()
    private val disposable = CompositeDisposable()

    init {
        DaggerApiComponent.create().inject(this)
    }

    val posts = MutableLiveData<List<MyPosts>>()
    val postsLoadError = MutableLiveData<Boolean>()
    val loadingError = MutableLiveData<Boolean>()

    fun refresh() {
        fetchPosts()
    }

    fun deletePosts(id: Long) {

        val response: Observable<Response<ResponseBody>> = postsService.deletePosts(id)

        response.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response<ResponseBody>> {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(value: Response<ResponseBody>?) {
                }

                override fun onError(e: Throwable?) {
                }

                override fun onComplete() {
                }

            })

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