package com.hend.nytimeschallenge.di

import androidx.lifecycle.SavedStateHandle
import com.hend.nytimeschallenge.repository.ArticlesRepository
import com.hend.nytimeschallenge.ui.articles.ArticleListViewModel
import com.hend.nytimeschallenge.ui.details.ArticleDetailsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Module that provides instances of ViewModels
 */
@ExperimentalCoroutinesApi
@Module
@InstallIn(ActivityRetainedComponent::class)
object ViewModelModule {

    @Provides
    @ActivityRetainedScoped
    fun provideArticleListViewModel(articlesRepository: ArticlesRepository) : ArticleListViewModel {
        return ArticleListViewModel(articlesRepository)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideDetailsViewModel(articlesRepository: ArticlesRepository) : ArticleDetailsViewModel {
        return ArticleDetailsViewModel(articlesRepository, SavedStateHandle())
    }

}
