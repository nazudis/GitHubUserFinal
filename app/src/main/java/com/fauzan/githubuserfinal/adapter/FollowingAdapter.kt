package com.fauzan.githubuserfinal.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fauzan.githubuserfinal.R
import com.fauzan.githubuserfinal.holder.FollowingHolder
import com.fauzan.githubuserfinal.model.UserList
import com.fauzan.githubuserfinal.ui.DetailUserActivity

class FollowingAdapter : RecyclerView.Adapter<FollowingHolder>() {

    private val followingList = ArrayList<UserList>()
    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    fun setData(item: ArrayList<UserList>) {
        followingList.clear()
        followingList.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_following, parent, false)
        return FollowingHolder(view)
    }

    override fun getItemCount(): Int = followingList.size

    override fun onBindViewHolder(holder: FollowingHolder, position: Int) {
        val list = followingList[position]

        holder.bind(list)
        holder.itemView.setOnClickListener { view ->
            onItemClickCallBack.onItemClick(list)
            val moveToDetailUser =
                Intent(view.context.applicationContext, DetailUserActivity::class.java)
            moveToDetailUser.putExtra(DetailUserActivity.EXTRA_LOGIN, list)
            view.context.startActivity(moveToDetailUser)
        }
    }

    interface OnItemClickCallBack {
        fun onItemClick(data: UserList)
    }
}