package com.eemf.sirgoingfar.wikipedia.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.eemf.sirgoingfar.wikipedia.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(search_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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
        Toast.makeText(this, text ?: "Submit", Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onQueryTextChange(text: String?): Boolean {
        Toast.makeText(this, text ?: "Change", Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item!!.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}
