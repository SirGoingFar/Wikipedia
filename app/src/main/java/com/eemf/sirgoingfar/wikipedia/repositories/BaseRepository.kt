package com.eemf.sirgoingfar.wikipedia.repositories

import com.eemf.sirgoingfar.wikipedia.models.WikiResult
import com.google.gson.Gson
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser

open class BaseRepository(private val tableName: String, private val dbHelper: ArticleDatabaseOpenHelper) {

    public fun addWikiPage(wikiPage: WikiResult.WikiPage) {
        dbHelper.use {
            insert(
                tableName,
                COLUMN_ID to wikiPage.pageid,
                COLUMN_TITLE to wikiPage.title,
                COLUMN_URL to wikiPage.fullurl,
                COLUMN_THUMBNAIL_JSON to Gson().toJson(wikiPage.thumbnail)
            )
        }
    }

    public fun addWikiPage(wikiPageList: ArrayList<WikiResult.WikiPage>) {
        wikiPageList.forEach { addWikiPage(it) }
    }

    public fun removePage(pageId: Int) {
        dbHelper.use {
            delete(tableName, "$COLUMN_ID = {pageId}", "pageId" to pageId)
        }
    }

    public fun removePages(wikiPages: ArrayList<WikiResult.WikiPage>){
        wikiPages.forEach { it.pageid?.let { it1 -> removePage(it1) } }
    }

    public fun getAllWikiPage(): ArrayList<WikiResult.WikiPage> {
        val wikiPages: ArrayList<WikiResult.WikiPage> = ArrayList()

        val articleRowParser = rowParser { id: Int, title: String, url: String, thumbnailJson: String ->
            val wikiPage = WikiResult.WikiPage()

            wikiPage.pageid = id
            wikiPage.title = title
            wikiPage.fullurl = url
            wikiPage.thumbnail = Gson().fromJson(thumbnailJson, WikiResult.WikiThumbnail::class.java)

            wikiPages.add(wikiPage)
        }

        return wikiPages
    }

    public fun isArticleFavorite(pageId: Int): Boolean {

        if (tableName != TABLE_NAME_FAVORITE)
            return false

        val wikiPages = getAllWikiPage()

        return wikiPages.any { it.pageid == pageId }
    }

    companion object {
        //Table Name
        const val TABLE_NAME_FAVORITE = "favorite"
        const val TABLE_NAME_HISTORY = "history"

        //Column Name
        const val COLUMN_ID = "_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_URL = "url"
        const val COLUMN_THUMBNAIL_JSON = "thumbnailJson"
    }
}