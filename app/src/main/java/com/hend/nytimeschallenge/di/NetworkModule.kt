package com.hend.nytimeschallenge.di

import com.hend.nytimeschallenge.network.api.HttpRequestInterceptor
import com.hend.nytimeschallenge.network.api.articles.ArticlesListService
import com.hend.nytimeschallenge.network.response.calladapter.FlowCallAdapterFactory
import com.hend.nytimeschallenge.utils.BASE_URL
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Module that provides Network related functions
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpRequestInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: Lazy<OkHttpClient>): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .callFactory { client.get().newCall(it) }
            .build()
    }

    @Provides
    @Singleton
    fun provideArticlesListApi(retrofit: Retrofit): ArticlesListService {
        return retrofit.create(ArticlesListService::class.java)
    }

}
