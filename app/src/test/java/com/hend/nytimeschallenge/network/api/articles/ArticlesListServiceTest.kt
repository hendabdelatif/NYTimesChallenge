package com.hend.nytimeschallenge.network.api.articles

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hend.nytimeschallenge.MainCoroutinesRule
import com.hend.nytimeschallenge.network.response.ApiResponse
import com.hend.nytimeschallenge.network.response.ApiSuccessResponse
import com.hend.nytimeschallenge.network.response.calladapter.FlowCallAdapterFactory
import com.hend.nytimeschallenge.persistance.entities.ResultResponse
import com.hend.nytimeschallenge.utils.API_KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ArticlesListServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    lateinit var mockWebServer: MockWebServer
    private lateinit var service: ArticlesListService

    @Throws(IOException::class)
    @Before
    fun initService() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        service = createService(ArticlesListService::class.java)
    }

    @Test
    fun fetchArticleListFromNetworkTest() = runBlockingTest {
        val response = service.fetchArticlesList(API_KEY)
        val job = launch {
            response.collect { value: ApiResponse<ResultResponse> ->
                val responseBody =
                    requireNotNull((value as ApiSuccessResponse<ResultResponse>).body)
                MatcherAssert.assertThat(responseBody.results[0].title, CoreMatchers.`is`("FKA twigs Sues Shia LaBeouf, Citing ‘Relentless’ Abusive Relationship"))
                MatcherAssert.assertThat(responseBody.results[1].title, CoreMatchers.`is`("Trump Allies Eye Long-Shot Election Reversal in Congress, Testing Pence"))
            }
        }
        job.cancel()
    }

    @Throws(IOException::class)
    @After
    fun stopServer() {
        mockWebServer.shutdown()
    }

    private fun createService(clazz: Class<ArticlesListService>): ArticlesListService {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("all-sections/7.json"))
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .build()
            .create(clazz)
    }
}