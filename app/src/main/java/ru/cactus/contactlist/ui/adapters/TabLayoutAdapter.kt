package ru.cactus.contactlist.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.cactus.contactlist.Departments
import ru.cactus.contactlist.ui.fragments.UsersListFragment

class TabLayoutAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return Departments.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return UsersListFragment.newInstance()
    }

}