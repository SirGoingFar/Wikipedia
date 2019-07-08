package com.eemf.sirgoingfar.wikipedia.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eemf.sirgoingfar.wikipedia.R
import com.eemf.sirgoingfar.wikipedia.activities.MainActivity
import com.eemf.sirgoingfar.wikipedia.adapters.ArticleRecyclerViewAdapter
import com.eemf.sirgoingfar.wikipedia.adapters.HistoryArticleRecyclerViewAdapter
import com.eemf.sirgoingfar.wikipedia.models.Article
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment(), ArticleRecyclerViewAdapter.OnArticleClickListener {

    private lateinit var mainActivity: MainActivity

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
        rv_favorites_list.adapter =
            ArticleRecyclerViewAdapter(mainActivity, ArrayList(), this@FavoritesFragment)
    }

    override fun onArticleClick(position: Int, article: Article) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
