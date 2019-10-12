package com.eemf.sirgoingfar.wikipedia.adapters

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.eemf.sirgoingfar.wikipedia.R
import com.eemf.sirgoingfar.wikipedia.models.WikiResult

class ArticleRecyclerViewAdapter(
    private val context: Context,
    private var articleList: ArrayList<WikiResult.WikiPage>,
    private val listener: OnArticleClickListener
) :
    RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_article_card, container, false))
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(itemViewHolder: ViewHolder, p1: Int) {
        val currentItem = itemViewHolder.getCurrentItem()

        itemViewHolder.articleName.text = currentItem.title
        itemViewHolder.container.setOnClickListener {
            listener.onArticleClick(
                itemViewHolder.adapterPosition,
                currentItem
            )
        }
        if (!TextUtils.isEmpty(currentItem.thumbnail?.source))
            Glide.with(itemViewHolder.itemView.context).load(currentItem.thumbnail?.source).into(itemViewHolder.articleImage)
    }

    fun swapData(newData: ArrayList<WikiResult.WikiPage>){
        if(newData == null)
            return

        articleList = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val articleImage: ImageView = itemView.findViewById(R.id.iv_article_icon)
        val articleName: TextView = itemView.findViewById(R.id.tv_article_name)
        val container: ConstraintLayout = itemView.findViewById(R.id.container)

        fun getCurrentItem(): WikiResult.WikiPage {
            return articleList[adapterPosition]
        }
    }

    interface OnArticleClickListener {
        fun onArticleClick(position: Int, article: WikiResult.WikiPage)
    }
}