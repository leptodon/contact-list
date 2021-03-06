package ru.cactus.contactlist.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.cactus.contactlist.R
import ru.cactus.contactlist.data.response_models.User
import ru.cactus.contactlist.databinding.PageFragmentBinding
import ru.cactus.contactlist.ui.adapters.UsersAdapter
import ru.cactus.contactlist.ui.dialogs.UserProfileDialog
import ru.cactus.contactlist.ui.viewmodels.MainViewModel
import ru.cactus.contactlist.utils.Utils

class PageFragment : Fragment(R.layout.page_fragment) {

    private val binding by viewBinding<PageFragmentBinding>()
    private val viewModel by sharedViewModel<MainViewModel>()

    private val adapter = UsersAdapter {
        showUserProfile(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        setupObserver()
    }

    private fun setupViews() {
        with(binding) {
            recyclerView.adapter = adapter

            adapter.showList(Utils.getBlankUsersList())

            swipeContainer.setOnRefreshListener {
                viewModel.loadRawData()
            }
        }
    }

    private fun setupObserver() {
        with(viewModel) {
            mapOfUsersByDepartment.observe(viewLifecycleOwner) {
                it[arguments?.get("DEPARTMENTS")]?.let { userList ->
                    adapter.showList(userList)
                }
            }

            isProgress.observe(viewLifecycleOwner) {
                binding.swipeContainer.isRefreshing = it
                when (it) {
                    true -> {
                        binding.shimmerViewContainer.startShimmer()
                    }
                    false -> {
                        binding.shimmerViewContainer.stopShimmer()
                        binding.shimmerViewContainer.hideShimmer()
                    }
                }
            }


        }
    }

    private fun showUserProfile(user: User) {
        UserProfileDialog(user).show(childFragmentManager, "signature")
    }

    /** ?????? ???????????????????? ???????????????????? ???????????? ???? ???????? ?????? ???????????? ?????????????? ?????????????? ?????????????????????????????? ?????????? onPause() **/
    override fun onPause() {
        super.onPause()
        viewModel.loadRawData()
    }
}