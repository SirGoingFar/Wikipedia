package com.eemf.sirgoingfar.wikipedia.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.eemf.sirgoingfar.wikipedia.R
import com.eemf.sirgoingfar.wikipedia.fragments.ExploreFragment
import com.eemf.sirgoingfar.wikipedia.fragments.FavoritesFragment
import com.eemf.sirgoingfar.wikipedia.fragments.HistoryFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val mExploreFragment: ExploreFragment = ExploreFragment()
    private val mFavoriteFragment: FavoritesFragment = FavoritesFragment()
    private val mHistoryFragment: HistoryFragment = HistoryFragment()

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val txn = supportFragmentManager.beginTransaction()
        txn.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

        when (item.itemId) {
            R.id.nav_explore -> {
                txn.replace(R.id.container, mExploreFragment)
                    .addToBackStack(ExploreFragment::class.java.name)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_favorite -> {
                txn.replace(R.id.container, mFavoriteFragment)
                    .addToBackStack(FavoritesFragment::class.java.name)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_history -> {
                txn.replace(R.id.container, mHistoryFragment)
                    .addToBackStack(HistoryFragment::class.java.name)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, ExploreFragment())
            .addToBackStack(ExploreFragment::class.java.name)
            .commit()
    }
}
