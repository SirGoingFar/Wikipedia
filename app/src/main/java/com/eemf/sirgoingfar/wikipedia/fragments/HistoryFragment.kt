package com.eemf.sirgoingfar.wikipedia.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.eemf.sirgoingfar.wikipedia.R
import com.eemf.sirgoingfar.wikipedia.activities.ArticleDetailActivity
import com.eemf.sirgoingfar.wikipedia.activities.MainActivity
import com.eemf.sirgoingfar.wikipedia.adapters.HistoryArticleRecyclerViewAdapter
import com.eemf.sirgoingfar.wikipedia.models.WikiResult
import kotlinx.android.synthetic.main.fragment_history.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

/**
 * A simple [Fragment] subclass.
 *
 */
class HistoryFragment : BaseFragment(), HistoryArticleRecyclerViewAdapter.OnHistoryArticleClickListener {

    private lateinit var mainActivity: MainActivity
    private val adapter = HistoryArticleRecyclerViewAdapter(mainActivity, ArrayList(), this@HistoryFragment)

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
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_history_list.setHasFixedSize(true)
        rv_history_list.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        rv_history_list.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_history_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.action_clear) {
            context?.getString(R.string.prompt_text_delete_history)?.let {
                activity?.alert(it, context?.getString(R.string.text_confirm)) {
                    yesButton {
                        wikiManager?.clearHistory()
                        context?.getString(R.string.text_history_cleared)?.let { activity?.toast(it) }
                    }
                }?.show()
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        doAsync {
            val historyPages = wikiManager?.getHistory()
            activity?.runOnUiThread {
                historyPages?.let { adapter.swapData(it) }
            }
        }
    }

    override fun onHistoryArticleClick(position: Int, article: WikiResult.WikiPage) {
        val intent = Intent(context, ArticleDetailActivity::class.java)
        intent.putExtra(ArticleDetailActivity.KEY_WIKI_PAGE, article)
        startActivity(intent)
    }
}
