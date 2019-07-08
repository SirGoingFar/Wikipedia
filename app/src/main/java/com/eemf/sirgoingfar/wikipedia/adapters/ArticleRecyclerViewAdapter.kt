package com.eemf.sirgoingfar.wikipedia.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.eemf.sirgoingfar.wikipedia.R
import com.eemf.sirgoingfar.wikipedia.models.Article

class ArticleRecyclerViewAdapter(
    private val context: Context,
    private val articleList: ArrayList<Article>,
    private val listener: OnArticleClickListener
) :
    RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_article_card, container, false))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val articleImage: ImageView = itemView.findViewById(R.id.iv_article_icon)
        private val articleName: TextView = itemView.findViewById(R.id.tv_article_name)

        fun getCurrentItem(): Article {
            return articleList[adapterPosition]
        }
    }

    public interface OnArticleClickListener {
        fun onArticleClick(position: Int, article: Article)
    }
}