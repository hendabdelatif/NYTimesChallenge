package com.hend.nytimeschallenge.di

import com.hend.nytimeschallenge.network.NetworkHelper
import com.hend.nytimeschallenge.network.api.articles.ArticlesListService
import com.hend.nytimeschallenge.persistance.dao.ArticlesDao
import com.hend.nytimeschallenge.repository.ArticlesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Module that provides an instance of the Repository
 */
@ExperimentalCoroutinesApi
@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideArticlesListRepository(networkHelper: NetworkHelper,
                                        articlesListService: ArticlesListService,
                                        articlesDao: ArticlesDao): ArticlesRepository {
        return ArticlesRepository(networkHelper, articlesListService, articlesDao)
    }
}
