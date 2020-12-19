package com.hend.nytimeschallenge.ui.articles

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hend.nytimeschallenge.network.response.Resource
import com.hend.nytimeschallenge.persistance.entities.ResultResponse
import com.hend.nytimeschallenge.repository.ArticlesRepository
import com.hend.nytimeschallenge.utils.cancelIfActive
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

/**
 * ViewModel to get articles list from repository
 */
class ArticleListViewModel @ViewModelInject constructor(articlesRepository: ArticlesRepository)
    : ViewModel(), CoroutineScope, LifecycleObserver {

    /** Coroutine's background job **/
    private val job = Job()

    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    /** Clear our job when the linked activity is destroyed to avoid memory leaks **/
    override fun onCleared() {
        super.onCleared()
        job.cancelIfActive()
    }


    @ExperimentalCoroutinesApi
    val resultsResponse: LiveData<Resource<ResultResponse>> = articlesRepository.getResults().map {
        when (it.status) {
            Resource.Status.LOADING -> {
                Resource.loading(null)
            }
            Resource.Status.SUCCESS -> {
                val resultsResponse = it.data
                Resource.success(resultsResponse)
            }
            Resource.Status.ERROR -> {
                Resource.error(it.message!!, null)
            }
        }
    }.asLiveData(coroutineContext)


}