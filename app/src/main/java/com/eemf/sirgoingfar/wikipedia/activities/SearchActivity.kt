package com.eemf.sirgoingfar.wikipedia.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.eemf.sirgoingfar.wikipedia.R
import com.eemf.sirgoingfar.wikipedia.adapters.ArticleRecyclerViewAdapter
import com.eemf.sirgoingfar.wikipedia.models.WikiResult
import com.eemf.sirgoingfar.wikipedia.providers.ArticleDataProvider
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity(), SearchView.OnQueryTextListener,
    ArticleRecyclerViewAdapter.OnArticleClickListener {

    private val mArticleProvider: ArticleDataProvider = ArticleDataProvider()
    private val mRecyclerAdapter: ArticleRecyclerViewAdapter = ArticleRecyclerViewAdapter(this, ArrayList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(search_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        rv_search_result.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_search_result.setHasFixedSize(true)
        rv_search_result.adapter = mRecyclerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_search_activity, menu)

        val searchItem = menu!!.findItem(R.id.action_search)
        val searchView = searchItem!!.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setIconifiedByDefault(false)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.requestFocus()
        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(text: String?): Boolean {
        text?.let { searchQuery(it) }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item!!.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun searchQuery(queryString: String) {

        if (TextUtils.isEmpty(queryString))
            return

        wikiManager?.searchQuery(queryString, 0, 20) { wikiResult ->
            runOnUiThread {
                mRecyclerAdapter.swapData(wikiResult.query!!.pages as ArrayList<WikiResult.WikiPage>)
            }
        }
    }

    override fun onArticleClick(position: Int, article: WikiResult.WikiPage) {
        val intent = Intent(this, ArticleDetailActivity::class.java)
        intent.putExtra(ArticleDetailActivity.KEY_WIKI_PAGE, article.fullurl)
        startActivity(intent)
    }

}
