package com.eemf.sirgoingfar.wikipedia.fragments

import android.content.Context
import android.support.v4.app.Fragment
import com.eemf.sirgoingfar.wikipedia.manager.WikiManager
import com.eemf.sirgoingfar.wikipedia.utils.WikiApplication

abstract class BaseFragment : Fragment() {

    protected var wikiManager: WikiManager? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wikiManager = WikiApplication.getWikiManager()
    }
}