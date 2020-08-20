package com.fauzan.githubuserfinal.ui

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan.githubuserfinal.R
import com.fauzan.githubuserfinal.adapter.FavoriteUserAdapter
import com.fauzan.githubuserfinal.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.fauzan.githubuserfinal.helper.MappingHelper
import com.fauzan.githubuserfinal.model.UserList
import kotlinx.android.synthetic.main.activity_favorite_user.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_user)

        //Set Up Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar_fav)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            title = "Favorite"
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
            toolbar.setNavigationOnClickListener { finish() }
        }

        val handlerTherad = HandlerThread("DataObserve")
        handlerTherad.start()
        val handler = Handler(handlerTherad.looper)
        val mObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadFavoriteAsync()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, mObserver)

        //Show Fav List
        showFavoriteList()
    }

    override fun onResume() {
        super.onResume()
        showFavoriteList()
        loadFavoriteAsync()
    }

    private fun showFavoriteList() {
        rv_favorite.layoutManager = LinearLayoutManager(this)
        rv_favorite.setHasFixedSize(true)
        adapter = FavoriteUserAdapter()
        rv_favorite.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallbak {
            override fun onItemClicked(data: UserList) {

            }
        })

    }

    private fun loadFavoriteAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progressBarFav.visibility = View.VISIBLE
            val deferredFavorite = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

            progressBarFav.visibility = View.INVISIBLE
            val favorite = deferredFavorite.await()
            if (favorite.size > 0) {
                adapter.listFavUser = favorite
            }
            else {
                adapter.listFavUser = ArrayList()
                Toast.makeText(this@FavoriteUserActivity, "No Favorite User", Toast.LENGTH_SHORT).show()
            }
        }
    }
}