package com.fauzan.githubuserfinal.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.fauzan.githubuserfinal.R
import com.fauzan.githubuserfinal.adapter.SectionViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FollowActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LOGIN = "extra_login"
        const val EXTRA_PAGE = "extra_page"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follow)

        //Get Login/Username from DetailActivity
        val name = intent.getStringExtra(EXTRA_LOGIN)

        //Set Up Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar_follow)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            title = name
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
            toolbar.setNavigationOnClickListener { finish() }
        }

        initViewPagerFragment()
    }

    private fun initViewPagerFragment() {
        //ViewPager2 initial
        val viewPager: ViewPager2 = findViewById(R.id.view_pagers)
        val adapter = SectionViewPager(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        //Set specific Tab, so if user tap the following
        //it will be spesific go to the FollowingFragment (ViewPager Page 1)
        val page = intent.getIntExtra(EXTRA_PAGE, 0)
        viewPager.currentItem = page

        //TabLayout
        val title: ArrayList<String> = arrayListOf("Followers", "Following")
        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = title[position]
        }.attach()

    }
}