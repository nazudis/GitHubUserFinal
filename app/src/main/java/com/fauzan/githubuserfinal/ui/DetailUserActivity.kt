package com.fauzan.githubuserfinal.ui

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fauzan.githubuserfinal.R
import com.fauzan.githubuserfinal.db.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.fauzan.githubuserfinal.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.fauzan.githubuserfinal.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.fauzan.githubuserfinal.db.DatabaseContract.UserColumns.Companion.USER_ID
import com.fauzan.githubuserfinal.helper.MappingHelper
import com.fauzan.githubuserfinal.holder.loadImage
import com.fauzan.githubuserfinal.model.UserList
import com.fauzan.githubuserfinal.ui.fragment.FollowersFragment
import com.fauzan.githubuserfinal.ui.fragment.FollowingFragment
import com.fauzan.githubuserfinal.viewmodel.DetailUserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_detail_user.*

class DetailUserActivity : AppCompatActivity() {

    private var statusFavorite = false
    private lateinit var detailViewModel: DetailUserViewModel

    companion object {
        const val EXTRA_LOGIN = "extra_login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        loading(true)

        //Get Login/Username
        val user = intent.getParcelableExtra<UserList>(EXTRA_LOGIN)

        //Set Up Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar_detail)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            title = user?.login
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
            toolbar.setNavigationOnClickListener { finish() }
        }

        //Get ViewModel
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailUserViewModel::class.java)

        detailViewModel.setDataUser(user?.login)

        //Observe Change
        detailViewModel.getDataUser().observe(this, Observer { username ->
            setViewDetailUser(username)
            loading(false)
        })

        //Check Favorite
        isFavoriteUser(user?.id)
        setStatusFavorite(statusFavorite)

        //Set Up Floating ActionButton
        val fab: FloatingActionButton = findViewById(R.id.fab_fav)
        fab.setOnClickListener {
            if (!statusFavorite) {
                statusFavorite = true

                //Insert database
                addToFavorite(user)
                Toast.makeText(this, "Success add to favorite", Toast.LENGTH_SHORT).show()
                setStatusFavorite(statusFavorite)

            } else {
                statusFavorite = false

                //Delete database
                deletedFromFavorite(user?.id)
                Toast.makeText(this, "Deleted from favorite", Toast.LENGTH_SHORT).show()
                setStatusFavorite(statusFavorite)
            }
        }

        moveToFollowActivity()
    }

    private fun setStatusFavorite(statusFav: Boolean) {
        if (statusFav) {
            fab_fav.setImageResource(R.drawable.ic_baseline_favorite_true)
        } else {
            fab_fav.setImageResource(R.drawable.ic_baseline_favorite_border)
        }
    }

    private fun addToFavorite(user: UserList?) {
        val values = ContentValues().apply {
            put(USER_ID, user?.id)
            put(USERNAME, user?.login)
            put(AVATAR_URL, user?.avatar)
        }
        contentResolver.insert(CONTENT_URI, values)
    }

    private fun isFavoriteUser(userId: Int?) {
        val uri = Uri.parse("$CONTENT_URI/$userId")
        val cursor = contentResolver.query(uri, null, null, null, null)
        val favorite = MappingHelper.mapCursorToArrayList(cursor)
        for (data in favorite) {
            if (userId == data.id) statusFavorite = true
            cursor?.close()
        }
    }

    private fun deletedFromFavorite(userId: Int?) {
        val uri = Uri.parse("$CONTENT_URI/$userId")
        contentResolver.delete(uri, null, null)
    }

    //Set View Detail User
    private fun setViewDetailUser(detailUser: UserList?) {
        if (detailUser != null) {
            tv_login_detail.text = detailUser.login
            tv_name.text = detailUser.name
            tv_company_user.text = detailUser.company
            tv_location.text = detailUser.location
            tv_repo.text = detailUser.repo.toString()
            tv_followers.text = detailUser.followers.toString()
            tv_following.text = detailUser.following.toString()

            tv_avatar_detail.loadImage(detailUser.avatar)
        }

        val nothing = getString(R.string.nothing)
        if (detailUser?.login == null) tv_login_detail.text = nothing
        if (detailUser?.name == null) tv_name.text = nothing
        if (detailUser?.company == null) tv_company_user.text = nothing
        if (detailUser?.location == null) tv_location.text = nothing

        //Set Up Profile Info Title
        tv_followersTitle.text = getString(R.string.followers)
        tv_followingTitle.text = getString(R.string.following)
        tv_repoTitle.text = getString(R.string.repos)
        tv_company.text = getString(R.string.company)
    }

    //Set Intent to Followers and Following List
    private fun moveToFollowActivity() {
        //Get Username to Send to the FollowActivity
        val user = intent.getParcelableExtra<UserList>(EXTRA_LOGIN)

        followers_btn.setOnClickListener { view ->
            val move = Intent(view.context.applicationContext, FollowActivity::class.java)
            move.putExtra(FollowActivity.EXTRA_LOGIN, user?.login)
            move.putExtra(FollowersFragment.EXTRA_LOGIN, user?.login)
            move.putExtra(FollowingFragment.EXTRA_LOGIN, user?.login)
            view.context.startActivity(move)

        }

        following_btn.setOnClickListener { view ->
            val defaultPage = 1
            val move = Intent(view.context.applicationContext, FollowActivity::class.java)
            move.putExtra(FollowActivity.EXTRA_LOGIN, user?.login)
            move.putExtra(FollowActivity.EXTRA_PAGE, defaultPage)
            move.putExtra(FollowersFragment.EXTRA_LOGIN, user?.login)
            move.putExtra(FollowingFragment.EXTRA_LOGIN, user?.login)
            view.context.startActivity(move)

        }

    }

    private fun loading(state: Boolean) {
        if (state) progressBarDetail.visibility = View.VISIBLE
        else progressBarDetail.visibility = View.GONE
    }

}