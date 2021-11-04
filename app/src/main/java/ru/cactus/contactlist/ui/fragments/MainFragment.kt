package ru.cactus.contactlist.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.cactus.contactlist.R
import ru.cactus.contactlist.databinding.MainFragmentBinding

class MainFragment : Fragment(R.layout.main_fragment) {
    val binding by viewBinding<MainFragmentBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        setupObservers()
    }

    private fun setupViews(){

    }

    private fun setupObservers(){

    }
}