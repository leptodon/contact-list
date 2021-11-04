package ru.cactus.contactlist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.cactus.contactlist.R
import ru.cactus.contactlist.databinding.UsersListFragmentBinding

class UsersListFragment : Fragment(R.layout.users_list_fragment) {
    private val binding by viewBinding<UsersListFragmentBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.users_list_fragment, container, false)

    companion object {
            fun newInstance(): UsersListFragment {
            val fragment = UsersListFragment()
            return fragment
        }
    }
}