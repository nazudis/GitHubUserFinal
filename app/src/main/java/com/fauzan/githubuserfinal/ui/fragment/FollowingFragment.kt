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
import com.fauzan.githubuserfinal.adapter.FollowingAdapter
import com.fauzan.githubuserfinal.model.UserList
import com.fauzan.githubuserfinal.viewmodel.FollowersViewModel
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {

    private lateinit var adapter: FollowingAdapter
    private lateinit var mFollowingViewModel : FollowersViewModel

    companion object {
        const val EXTRA_LOGIN = "extra_login"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get Username/Login
        val name = requireActivity().intent.getStringExtra(EXTRA_LOGIN)

        //GetViewModel
        mFollowingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)

        //Set Value
        loading(true)
        mFollowingViewModel.setFollowing(name)

        //Observe Change
        mFollowingViewModel.getFollowing().observe(viewLifecycleOwner, Observer { followingList ->
            if (!followingList.isNullOrEmpty()) {
                adapter.setData(followingList)
                loading(false)
            } else {
                Toast.makeText(context, "$name dont have any following", Toast.LENGTH_SHORT).show()
                loading(false)
            }
        })

        //Show List Following
        showRecyclerlist()

    }

    private fun showRecyclerlist() {
        adapter = FollowingAdapter()
        adapter.notifyDataSetChanged()

        rv_following.setHasFixedSize(true)
        rv_following.layoutManager = LinearLayoutManager(context)
        rv_following.adapter = adapter

        adapter.setOnItemClickCallBack(object : FollowingAdapter.OnItemClickCallBack {
            override fun onItemClick(data: UserList) {
            }

        })
    }

    private fun loading(state: Boolean) {
        if (state) progressBarFav.visibility = View.VISIBLE
        else progressBarFav.visibility = View.INVISIBLE
    }
}