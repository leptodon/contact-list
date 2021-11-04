package ru.cactus.contactlist.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import ru.cactus.contactlist.Departments
import ru.cactus.contactlist.R
import ru.cactus.contactlist.databinding.MainFragmentBinding
import ru.cactus.contactlist.ui.adapters.TabLayoutAdapter

val testDepartmentArray = arrayOf(
    "All",
    "Android",
    "iOS"
)

class MainFragment : Fragment(R.layout.main_fragment) {
    val binding by viewBinding<MainFragmentBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        setupObservers()

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = TabLayoutAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(Departments.values()[position].resTitle)
        }.attach()
    }

    private fun setupViews(){

    }

    private fun setupObservers(){

    }
}