package com.eemf.sirgoingfar.wikipedia.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eemf.sirgoingfar.wikipedia.R
import com.eemf.sirgoingfar.wikipedia.activities.ArticleDetailActivity
import com.eemf.sirgoingfar.wikipedia.activities.MainActivity
import com.eemf.sirgoingfar.wikipedia.adapters.ArticleRecyclerViewAdapter
import com.eemf.sirgoingfar.wikipedia.models.WikiResult
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.jetbrains.anko.doAsync

class FavoritesFragment : BaseFragment(), ArticleRecyclerViewAdapter.OnArticleClickListener {

    private lateinit var mainActivity: MainActivity
    private val adapter = ArticleRecyclerViewAdapter(mainActivity, ArrayList(), this@FavoritesFragment)

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
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_favorites_list.setHasFixedSize(true)
        rv_favorites_list.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        rv_favorites_list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        doAsync {
            val favPages = wikiManager?.getFavorite()
            activity?.runOnUiThread {
                favPages?.let { adapter.swapData(it) }
            }
        }
    }

    override fun onArticleClick(position: Int, article: WikiResult.WikiPage) {
        val intent = Intent(context, ArticleDetailActivity::class.java)
        intent.putExtra(ArticleDetailActivity.KEY_WIKI_PAGE, article)
        startActivity(intent)
    }

}
