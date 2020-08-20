package com.fauzan.favoriteuserapp.ui

import android.content.Intent
import android.database.ContentObserver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan.favoriteuserapp.R
import com.fauzan.favoriteuserapp.adapter.FavoriteUserAdapter
import com.fauzan.favoriteuserapp.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.fauzan.favoriteuserapp.helper.MappingHelper
import com.fauzan.favoriteuserapp.model.UserList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteUserAdapter
    private lateinit var uriWithId: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set Up Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) title = null

        showFavoriteList()

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val mObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadFavoriteAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, mObserver)
    }



    //Set up Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about -> {
                val moveToAbout = Intent(this, AboutActivity::class.java)
                startActivity(moveToAbout)
                true
            }
            else -> true
        }
    }

    override fun onResume() {
        super.onResume()
        loadFavoriteAsync()
        showFavoriteList()
    }

    private fun showFavoriteList() {
        adapter = FavoriteUserAdapter()

        rv_favorite.layoutManager = LinearLayoutManager(this)
        rv_favorite.setHasFixedSize(true)
        rv_favorite.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserList) {
                Toast.makeText(this@MainActivity, "${data.login} is your Favorite user", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onDeleteClick(data: UserList) {
                uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + data.id)
                showAlertDialog(data.login)
            }
        })
    }

    private fun loadFavoriteAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            val deferredFavorite = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

            progressBar.visibility = View.INVISIBLE
            val favorite = deferredFavorite.await()
            if (favorite.size > 0) {
                adapter.listFavUser = favorite
            }
            else {
                adapter.listFavUser = ArrayList()
                Toast.makeText(this@MainActivity, "No Favorite User", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun showAlertDialog(username: String?) {
        val dialogMessage = "$username is your favorite user. You want to delete?"
        val dialogTitle = "GitHub user : "
        val title = "$dialogTitle $username"

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(title)
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya") { _, _ ->
                contentResolver.delete(uriWithId, null, null)
                onResume()
            }
            .setNegativeButton("No") { dialog, _ -> dialog.cancel()}

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
