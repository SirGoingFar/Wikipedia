package com.eemf.sirgoingfar.wikipedia.repositories

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class ArticleDatabaseOpenHelper(context: Context): ManagedSQLiteOpenHelper(context, DATABASE_NAME_ARTICLE, null) {
    override fun onCreate(db: SQLiteDatabase?) {
        //create Favorite article table
        createTable(db, BaseRepository.TABLE_NAME_FAVORITE)

        //create History article table
        createTable(db, BaseRepository.TABLE_NAME_HISTORY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //When the database is upgraded
    }

    private fun createTable(db: SQLiteDatabase?, tableName: String) {
        db?.createTable(tableName, true,
            BaseRepository.COLUMN_ID to INTEGER + PRIMARY_KEY,
            BaseRepository.COLUMN_TITLE to TEXT,
            BaseRepository.COLUMN_URL to TEXT,
            BaseRepository.COLUMN_THUMBNAIL_JSON to TEXT)
    }

    companion object{
        const val DATABASE_NAME_ARTICLE = "article_database"
    }
}