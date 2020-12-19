package com.hend.nytimeschallenge.repository

import com.hend.nytimeschallenge.network.NetworkHelper
import com.hend.nytimeschallenge.network.api.articles.ArticlesListService
import com.hend.nytimeschallenge.network.response.Resource
import com.hend.nytimeschallenge.network.response.networkBoundResource
import com.hend.nytimeschallenge.persistance.dao.ArticlesDao
import com.hend.nytimeschallenge.persistance.entities.ResultResponse
import com.hend.nytimeschallenge.utils.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Used to get data from API service and directly save them to database in order to allow working offline
 */
class ArticlesRepository @Inject constructor(private val networkHelper: NetworkHelper,
                                             private val articlesListService: ArticlesListService,
                                             private val articlesDao: ArticlesDao
) {

    @ExperimentalCoroutinesApi
    fun getResults(): Flow<Resource<ResultResponse>> {
        return networkBoundResource(
            fetchFromLocal = { articlesDao.loadAllArticlesFlow() },
            shouldFetchFromRemote = { networkHelper.isNetworkConnected() },
            fetchFromRemote = { articlesDao.deleteCache()
                articlesListService.fetchArticlesList(API_KEY) },
            saveRemoteData = {
                articlesDao.insertArticlesMedia(it) },
            onFetchFailed = { _, _ -> }
        ).flowOn(Dispatchers.IO)
    }

    /**
     * Get Dao to use outside the repository
     */
    fun getArticlesDao() : ArticlesDao{
        return articlesDao
    }
}