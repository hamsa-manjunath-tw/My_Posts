package com.tw.mypost

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tw.mypost.model.MyPosts
import com.tw.mypost.model.PostsService
import com.tw.mypost.viewmodel.AddEditViewModel
import com.tw.mypost.viewmodel.ListViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class AddEditViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    // Mocking service
    @Mock
    lateinit var postsService: PostsService

    @InjectMocks
    var addEditViewModel = AddEditViewModel()

    private var testObservable: Observable<Response<MyPosts>>? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun addPostSuccess() {
        val myPost: MyPosts = MyPosts(1, 1, "title1", "description1")
        val response: Response<MyPosts> = Response.success(myPost)

        testObservable = Observable.just(response)
        val action = "ADD"

        Mockito.`when`(postsService.addPosts(myPost)).thenReturn(testObservable)

        addEditViewModel.refresh(myPost, action)

        val result = addEditViewModel.post.value

        assertEquals(myPost, result)
        assertTrue(addEditViewModel.isSuccess)

    }

    @Test
    fun editPostSuccess() {
        val myPost: MyPosts = MyPosts(1, 1, "title1", "description1")
        val response: Response<MyPosts> = Response.success(myPost)

        testObservable = Observable.just(response)
        val action = "UPDATE"

        Mockito.`when`(postsService.editPosts(myPost)).thenReturn(testObservable)

        addEditViewModel.refresh(myPost, action)

        val result = addEditViewModel.post.value
        assertEquals(myPost, result)
        assertTrue(addEditViewModel.isSuccess)

    }

    @Test
    fun addPostError() {
        testObservable = Observable.error(Throwable())
        val myPost: MyPosts = MyPosts(1, 1, "title1", "description1")
        val action = "ADD"

        Mockito.`when`(postsService.addPosts(myPost)).thenReturn(testObservable)

        addEditViewModel.refresh(myPost, action)

        assertFalse(addEditViewModel.isSuccess)

    }

    @Test
    fun editPostError() {
        testObservable = Observable.error(Throwable())
        val myPost: MyPosts = MyPosts(1, 1, "title1", "description1")
        val action = "UPDATE"

        Mockito.`when`(postsService.editPosts(myPost)).thenReturn(testObservable)

        addEditViewModel.refresh(myPost, action)

        assertFalse(addEditViewModel.isSuccess)

    }

    @Before
    fun setUpRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor {
                    it.run()
                })
            }
        }
        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }

    }

}