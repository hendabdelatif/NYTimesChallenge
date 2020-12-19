package com.hend.nytimeschallenge.network.api.articles

import com.hend.nytimeschallenge.network.response.ApiResponse
import com.hend.nytimeschallenge.persistance.entities.ResultResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Service API to fetch Article list
 */
interface ArticlesListService {

    @GET("all-sections/7.json")
    fun fetchArticlesList(@Query("api-key") apiKey: String): Flow<ApiResponse<ResultResponse>>
}