package com.eemf.sirgoingfar.wikipedia.manager

import com.eemf.sirgoingfar.wikipedia.models.WikiResult
import com.eemf.sirgoingfar.wikipedia.providers.ArticleDataProvider
import com.eemf.sirgoingfar.wikipedia.repositories.FavoriteRepository
import com.eemf.sirgoingfar.wikipedia.repositories.HistoryRepository

class WikiManager(
    private val dataProvider: ArticleDataProvider,
    private val favoriteRepository: FavoriteRepository,
    private val historyRepository: HistoryRepository
) {
    private var favoriteCache: ArrayList<WikiResult.WikiPage>? = null
    private var historyCache: ArrayList<WikiResult.WikiPage>? = null

    fun searchQuery(term: String, skip: Int, take: Int, responseHandler: (result: WikiResult) -> Unit?) {
        return dataProvider.searchQuery(term, skip, take, responseHandler)
    }

    fun getRandom(take: Int, responseHandler: (result: WikiResult) -> Unit?) {
        return dataProvider.getRandom(take, responseHandler)
    }

    fun getFavorite(): ArrayList<WikiResult.WikiPage> {
        if (favoriteCache == null)
            favoriteCache = favoriteRepository.getAllWikiPage()

        return favoriteCache as ArrayList<WikiResult.WikiPage>
    }

    fun addFavorite(wikiPage: WikiResult.WikiPage) {
        favoriteCache?.add(wikiPage)
        favoriteRepository.addWikiPage(wikiPage)
    }

    fun removeFavorite(pageId: Int) {
        favoriteRepository.removePage(pageId)
        favoriteCache = favoriteCache?.filter { it.pageid != pageId } as ArrayList<WikiResult.WikiPage>
    }

    fun isFavorite(pageId: Int): Boolean {
        return favoriteRepository.isArticleFavorite(pageId)
    }

    fun addHistory(wikiPage: WikiResult.WikiPage) {
        historyRepository.addWikiPage(wikiPage)
        historyCache?.add(wikiPage)
    }

    fun getHistory(): ArrayList<WikiResult.WikiPage> {
        if (historyCache == null)
            historyCache = historyRepository.getAllWikiPage()

        return historyCache as ArrayList<WikiResult.WikiPage>
    }

    fun clearHistory() {
        historyCache?.clear()
        historyRepository.removePages(historyRepository.getAllWikiPage())
    }
}