package com.fauzan.githubuserfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan.githubuserfinal.adapter.SearchListUserAdapter
import com.fauzan.githubuserfinal.model.UserList
import com.fauzan.githubuserfinal.ui.AboutActivity
import com.fauzan.githubuserfinal.ui.FavoriteUserActivity
import com.fauzan.githubuserfinal.ui.SettingActivity
import com.fauzan.githubuserfinal.viewmodel.SearchUserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: SearchListUserAdapter
    private lateinit var searchViewModel: SearchUserViewModel
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set Up Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) title = null

        //Method to get ViewModel SearchListUser
        searchViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(SearchUserViewModel::class.java)

        //Observe Change
        searchViewModel.getSearchResult().observe(this, Observer { user ->
            if (!user.isNullOrEmpty()) {
                adapter.setData(user)
                loading(false)
            } else {
                adapter.setData(user)
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                loading(false)
            }
        })

        //Set up Search Query
        searchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //Send data username
                if (query.isNotEmpty()) {
                    searchViewModel.setSearchUser(query)
                    loading(true)
                    rv_gituser.visibility = View.VISIBLE
                    home_text.visibility = View.GONE
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    rv_gituser.visibility = View.GONE
                    home_text.visibility = View.VISIBLE
                }
                return true
            }

        })

        //Show List USer
        showRecyclerView()
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
            R.id.fav_btn -> {
                val moveToFavUser = Intent(this, FavoriteUserActivity::class.java)
                startActivity(moveToFavUser)
                true
            }
            R.id.setting -> {
                val moveToSetting = Intent(this, SettingActivity::class.java)
                startActivity(moveToSetting)
                true
            }
            else -> true
        }
    }

    //Method for load RecyclerView from adapter
    private fun showRecyclerView() {
        adapter = SearchListUserAdapter()
        adapter.notifyDataSetChanged()

        rv_gituser.layoutManager = LinearLayoutManager(this)
        rv_gituser.adapter = adapter
        rv_gituser.setHasFixedSize(true)

        adapter.setOnItemClickCallback(object : SearchListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserList) {
            }
        })
    }

    //Loading
    private fun loading(state: Boolean) {
        if (state) progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
    }
}