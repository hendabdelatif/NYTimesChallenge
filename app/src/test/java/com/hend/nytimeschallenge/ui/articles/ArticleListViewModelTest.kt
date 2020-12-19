package com.hend.nytimeschallenge.ui.articles

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.hend.nytimeschallenge.MainCoroutinesRule
import com.hend.nytimeschallenge.network.NetworkHelper
import com.hend.nytimeschallenge.network.api.articles.ArticlesListService
import com.hend.nytimeschallenge.network.response.Resource
import com.hend.nytimeschallenge.persistance.dao.ArticlesDao
import com.hend.nytimeschallenge.persistance.entities.ResultResponse
import com.hend.nytimeschallenge.repository.ArticlesRepository
import com.hend.nytimeschallenge.utils.MockUtil
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArticleListViewModelTest{
    private lateinit var articleListViewModel: ArticleListViewModel
    private lateinit var articlesRepository: ArticlesRepository
    private val articlesListService : ArticlesListService = mock()
    private val articleDao: ArticlesDao = mock()
    private val networkHelper : NetworkHelper = mock()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        articlesRepository = ArticlesRepository(networkHelper, articlesListService, articleDao)
        articleListViewModel = ArticleListViewModel(articlesRepository)
    }

    @Test
    fun fetchArticleListTest() = runBlockingTest {
        val mockData = MockUtil.mockResultResponse()
        whenever(articleDao.loadAllArticlesFlow().asLiveData().value).thenReturn(mockData)

        val observer: Observer<Resource<ResultResponse>> = mock()
        val observerList: Observer<ResultResponse> = mock()

        val fetchedData: LiveData<Resource<ResultResponse>> =
            articlesRepository.getResults().asLiveData()
        fetchedData.observeForever(observer)

        articleListViewModel.resultsResponse
        delay(500L)

        verify(articleDao, atLeastOnce()).loadAllArticlesFlow()
        verify(observerList).onChanged(mockData)
        fetchedData.removeObserver(observer)
    }
}