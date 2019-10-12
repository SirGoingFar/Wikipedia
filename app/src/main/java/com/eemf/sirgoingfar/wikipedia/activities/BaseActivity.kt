package com.eemf.sirgoingfar.wikipedia.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.eemf.sirgoingfar.wikipedia.manager.WikiManager
import com.eemf.sirgoingfar.wikipedia.utils.WikiApplication

abstract class BaseActivity : AppCompatActivity() {

    protected var wikiManager: WikiManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wikiManager = WikiApplication.getWikiManager()
    }
}