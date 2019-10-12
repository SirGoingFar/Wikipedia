package com.eemf.sirgoingfar.wikipedia.utils

import android.app.Application
import com.eemf.sirgoingfar.wikipedia.manager.WikiManager
import com.eemf.sirgoingfar.wikipedia.providers.ArticleDataProvider
import com.eemf.sirgoingfar.wikipedia.repositories.ArticleDatabaseOpenHelper
import com.eemf.sirgoingfar.wikipedia.repositories.FavoriteRepository
import com.eemf.sirgoingfar.wikipedia.repositories.HistoryRepository

class WikiApplication : Application() {

    private var dbHelper: ArticleDatabaseOpenHelper? = null
    private var dataProvider: ArticleDataProvider? = null
    private var favoriteRepository: FavoriteRepository? = null
    private var historyRepository: HistoryRepository? = null

    override fun onCreate() {
        super.onCreate()

        dbHelper = ArticleDatabaseOpenHelper(applicationContext)
        dataProvider = ArticleDataProvider()
        favoriteRepository = FavoriteRepository(dbHelper!!)
        historyRepository = HistoryRepository(dbHelper!!)
        wikiManager = WikiManager(dataProvider!!, favoriteRepository!!, historyRepository!!)
    }

    companion object{

        private var wikiManager: WikiManager? = null

        fun getWikiManager(): WikiManager? {
            return wikiManager
        }
    }
}