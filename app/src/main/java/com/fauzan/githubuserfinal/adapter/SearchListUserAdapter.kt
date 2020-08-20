package com.fauzan.githubuserfinal.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fauzan.githubuserfinal.ui.DetailUserActivity
import com.fauzan.githubuserfinal.R
import com.fauzan.githubuserfinal.holder.SearchListHolder
import com.fauzan.githubuserfinal.model.UserList

class SearchListUserAdapter : RecyclerView.Adapter<SearchListHolder>() {

    private val listUser = ArrayList<UserList>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(item: ArrayList<UserList>) {
        listUser.clear()
        listUser.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_gituser, parent, false)
        return SearchListHolder(view)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: SearchListHolder, position: Int) {
        val list = listUser[position]

        holder.bind(list)

        holder.itemView.setOnClickListener { view ->
            onItemClickCallback.onItemClicked(list)
            val moveToDetailUser =
                Intent(view.context.applicationContext, DetailUserActivity::class.java)
            moveToDetailUser.putExtra(DetailUserActivity.EXTRA_LOGIN, list)
            view.context.startActivity(moveToDetailUser)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserList)
    }
}