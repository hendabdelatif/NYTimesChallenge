package com.hend.nytimeschallenge.di

import android.app.Application
import androidx.room.Room
import com.hend.nytimeschallenge.persistance.AppDatabase
import com.hend.nytimeschallenge.persistance.TypeResponseConverter
import com.hend.nytimeschallenge.persistance.dao.ArticlesDao
import com.hend.nytimeschallenge.utils.DATABASE_NAME
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module that provides Database related functions
 */
@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

  @Provides
  @Singleton
  fun provideMoshi(): Moshi {
    return Moshi.Builder().build()
  }

  @Provides
  @Singleton
  fun provideAppDatabase(application: Application, typeResponseConverter: TypeResponseConverter): AppDatabase {
    return Room
      .databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
      .fallbackToDestructiveMigration()
      .addTypeConverter(typeResponseConverter)
      .build()
  }

  @Provides
  @Singleton
  fun provideArticleDao(appDatabase: AppDatabase): ArticlesDao {
    return appDatabase.articlesDao()
  }

  @Provides
  @Singleton
  fun provideTypeResponseConverter(moshi: Moshi): TypeResponseConverter {
    return TypeResponseConverter(moshi)
  }

}
