package com.fauzan.githubuserfinal.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan.githubuserfinal.R
import com.fauzan.githubuserfinal.adapter.FollowersAdapter
import com.fauzan.githubuserfinal.model.UserList
import com.fauzan.githubuserfinal.viewmodel.FollowersViewModel
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment() {

    private lateinit var adapter: FollowersAdapter
    private lateinit var mFollowersViewModel: FollowersViewModel

    companion object {
        const val EXTRA_LOGIN = "extra_login"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get Username/Login from DetailUserActivity
        val name = requireActivity().intent.getStringExtra(EXTRA_LOGIN)

        //Get ViewModel
        mFollowersViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)

        //Set Value
        loading(true)
        mFollowersViewModel.setFollowers(name)

        //Observe Change
        mFollowersViewModel.getFollowers().observe(viewLifecycleOwner, Observer { followersList ->
            if (!followersList.isNullOrEmpty()) {
                adapter.setData(followersList)
                loading(false)
            } else {
                Toast.makeText(context, "$name dont have any followers", Toast.LENGTH_SHORT).show()
            }
        })

        //Show List Followers
        showFollowersList()
    }

    private fun showFollowersList() {
        adapter = FollowersAdapter()
        adapter.notifyDataSetChanged()

        rv_followers.setHasFixedSize(true)
        rv_followers.layoutManager = LinearLayoutManager(context)
        rv_followers.adapter = adapter

        adapter.setOnItemClickCallBack(object: FollowersAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserList) {

            }

        })
    }

    private fun loading(state: Boolean) {
        if (state) progressBarFollowers.visibility = View.VISIBLE
        else progressBarFollowers.visibility = View.INVISIBLE
    }
}