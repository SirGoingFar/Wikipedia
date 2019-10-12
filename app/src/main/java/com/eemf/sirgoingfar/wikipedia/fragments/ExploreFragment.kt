package com.eemf.sirgoingfar.wikipedia.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eemf.sirgoingfar.wikipedia.R
import com.eemf.sirgoingfar.wikipedia.activities.ArticleDetailActivity
import com.eemf.sirgoingfar.wikipedia.activities.MainActivity
import com.eemf.sirgoingfar.wikipedia.activities.SearchActivity
import com.eemf.sirgoingfar.wikipedia.adapters.ArticleRecyclerViewAdapter
import com.eemf.sirgoingfar.wikipedia.models.WikiResult
import com.eemf.sirgoingfar.wikipedia.providers.ArticleDataProvider
import kotlinx.android.synthetic.main.fragment_explore.*

/**
 * A simple [Fragment] subclass.
 *
 */
class ExploreFragment : BaseFragment(), ArticleRecyclerViewAdapter.OnArticleClickListener {

    private lateinit var mainActivity: MainActivity
    private val mArticleProvider: ArticleDataProvider = ArticleDataProvider()
    private val adapter: ArticleRecyclerViewAdapter =
        ArticleRecyclerViewAdapter(mainActivity, ArrayList(), this@ExploreFragment)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity)
            mainActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cv_search_box.setOnClickListener {
            mainActivity.startActivity(Intent(mainActivity, SearchActivity::class.java))
        }

        rv_article_list.setHasFixedSize(true)
        rv_article_list.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        rv_article_list.adapter = adapter

        sr_refresher.setOnRefreshListener { getRandomArticle() }

        //get Random Articles
        getRandomArticle()
    }

    override fun onArticleClick(position: Int, article: WikiResult.WikiPage) {
        val intent = Intent(context, ArticleDetailActivity::class.java)
        intent.putExtra(ArticleDetailActivity.KEY_WIKI_PAGE, article)
        startActivity(intent)
    }

    private fun getRandomArticle() {
        sr_refresher.isRefreshing = true

        wikiManager?.getRandom(15) { wikiResult ->
            activity?.runOnUiThread {
                sr_refresher.isRefreshing = false
                adapter.swapData(wikiResult.query!!.pages as ArrayList<WikiResult.WikiPage>)
            }
        }
    }
}