package ru.cactus.contactlist.ui.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.cactus.contactlist.Departments
import ru.cactus.contactlist.ui.fragments.PageFragment

private const val ARG_OBJECT = "DEPARTMENTS"

class TabLayoutAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return Departments.values().size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = PageFragment()
        fragment.arguments = Bundle().apply {
            putString(ARG_OBJECT, Departments.values()[position].name)
        }
        return fragment
    }
}