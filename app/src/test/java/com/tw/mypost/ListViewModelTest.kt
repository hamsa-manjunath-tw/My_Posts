package com.tw.mypost

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tw.mypost.model.MyPosts
import com.tw.mypost.model.PostsService
import com.tw.mypost.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    // Mocking service
    @Mock
    lateinit var postsService: PostsService

    @InjectMocks
    var listViewModel = ListViewModel()

    private var testSingle: Single<List<MyPosts>>? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getPostsSuccess() {
        val myPost1 = MyPosts(1, 1, "title1", "description1")

        val myPosts = arrayListOf<MyPosts>()
        myPosts.add(myPost1)

        testSingle = Single.just(myPosts)
        `when`(postsService.getPosts()).thenReturn(testSingle)

        listViewModel.refresh()

        val result = listViewModel.posts.value?.size

        assertEquals(1, result)
        assertEquals(false, listViewModel.loadingError.value)
        assertEquals(false, listViewModel.postsLoadError.value)

    }


    @Test
    fun getPostsError() {

        testSingle = Single.error(Throwable())

        `when`(postsService.getPosts()).thenReturn(testSingle)

        listViewModel.refresh()

        assertEquals(false, listViewModel.loadingError.value)
        assertEquals(true, listViewModel.postsLoadError.value)


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