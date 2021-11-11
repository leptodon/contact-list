package ru.cactus.contactlist.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.cactus.contactlist.R
import ru.cactus.contactlist.databinding.MainFragmentBinding
import ru.cactus.contactlist.domain.entities.Departments
import ru.cactus.contactlist.domain.entities.SortedBy
import ru.cactus.contactlist.ui.adapters.TabLayoutAdapter
import ru.cactus.contactlist.ui.dialogs.FilterDialog
import ru.cactus.contactlist.ui.dialogs.NetworkErrorDialog
import ru.cactus.contactlist.ui.viewmodels.MainViewModel

class MainFragment : Fragment(R.layout.main_fragment) {

    private val binding by viewBinding<MainFragmentBinding>()
    private val viewModel by sharedViewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        setupViewPager()
        setupObservers()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupViews() {
        with(binding) {
            llMain.setOnTouchListener { _, _ ->
                binding.etSearchField.clearFocus()
                return@setOnTouchListener true
            }

            etSearchField.setOnFocusChangeListener { _, hasFocus ->
                tvCancelBtn.isVisible = hasFocus
            }

            tvCancelBtn.setOnClickListener {
                etSearchField.clearFocus()
            }

            etSearchField.doOnTextChanged { text, _, _, _ ->
                if (text?.length!! >= 2) viewModel.searchUsers(text.toString())
            }
            ivFilterButton.setOnClickListener {
                showFilterDialog()
            }
        }
    }

    private fun setupViewPager() {
        with(binding) {
            val adapter = TabLayoutAdapter(childFragmentManager, lifecycle)
            viewPager.adapter = adapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text =
                    getString(Departments.values()[position].resTitle)
            }.attach()
        }
    }

    private fun setupObservers() {
        viewModel.isProgress.observe(viewLifecycleOwner) {

        }
        viewModel.networkError.observe(viewLifecycleOwner) {
            if (it) showNetworkErrorDialog()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showFilterDialog() {
        FilterDialog(viewModel.isSortedBy) {
            viewModel.isSortedBy = it
            when (it) {
                SortedBy.ALPHABET -> binding.ivFilterButton
                    .setImageDrawable(context?.getDrawable(R.drawable.icon_filter))
                SortedBy.BIRTHDAY -> binding.ivFilterButton
                    .setImageDrawable(context?.getDrawable(R.drawable.icon_filter_pressed))
            }
        }.show(childFragmentManager, null)
    }

    private fun showNetworkErrorDialog() {
        NetworkErrorDialog().show(childFragmentManager, "signature")
    }
}