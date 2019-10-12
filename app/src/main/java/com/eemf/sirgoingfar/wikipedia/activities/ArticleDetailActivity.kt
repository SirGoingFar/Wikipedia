package com.eemf.sirgoingfar.wikipedia.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.eemf.sirgoingfar.wikipedia.R
import com.eemf.sirgoingfar.wikipedia.models.WikiResult
import kotlinx.android.synthetic.main.activity_article_detail.*
import org.jetbrains.anko.toast

class ArticleDetailActivity : BaseActivity() {

    private var wikiPage: WikiResult.WikiPage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //extract Extra
        if (intent.hasExtra(KEY_WIKI_PAGE)) {

            wikiPage = intent.getParcelableExtra(KEY_WIKI_PAGE)
            //setup WebView
            wv_article_detail.loadUrl(wikiPage?.fullurl)
            wv_article_detail.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    return true
                }
            }

            //add to History
            wikiManager?.addHistory(wikiPage!!)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_article_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item!!.itemId == android.R.id.home) {
            finish()
            return true
        } else if (item.itemId == R.id.action_favorite) {
            if (wikiPage?.pageid?.let { wikiManager?.isFavorite(it) }!!) {
                wikiPage?.pageid?.let { wikiManager?.removeFavorite(it) }
                toast("Page removed from favorite")
            } else {
                wikiManager?.addFavorite(wikiPage!!)
                toast("Page added as favorite")
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val KEY_WIKI_PAGE = "key_wiki_page"
    }
}
