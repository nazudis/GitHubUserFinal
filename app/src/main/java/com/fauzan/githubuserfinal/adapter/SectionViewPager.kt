package com.fauzan.githubuserfinal.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fauzan.githubuserfinal.ui.fragment.FollowersFragment
import com.fauzan.githubuserfinal.ui.fragment.FollowingFragment

class SectionViewPager(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    private val fragment: ArrayList<Fragment> = arrayListOf(
        FollowersFragment(),
        FollowingFragment()
    )

    override fun getItemCount(): Int = fragment.size

    override fun createFragment(position: Int): Fragment = fragment[position]
}