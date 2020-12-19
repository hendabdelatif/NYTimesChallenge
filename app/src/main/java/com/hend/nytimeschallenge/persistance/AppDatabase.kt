package com.hend.nytimeschallenge.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hend.nytimeschallenge.persistance.dao.ArticlesDao
import com.hend.nytimeschallenge.persistance.entities.Article
import com.hend.nytimeschallenge.persistance.entities.Media
import com.hend.nytimeschallenge.persistance.entities.MediaMetaData
import com.hend.nytimeschallenge.persistance.entities.ResultResponse

/**
 * Abstract class for Room Database and entities included
 */
@Database(entities = [ResultResponse::class, Article::class, Media::class, MediaMetaData::class], version = 1, exportSchema = false)
@TypeConverters(value = [TypeResponseConverter::class])
abstract class AppDatabase : RoomDatabase() {
  abstract fun articlesDao(): ArticlesDao
}

