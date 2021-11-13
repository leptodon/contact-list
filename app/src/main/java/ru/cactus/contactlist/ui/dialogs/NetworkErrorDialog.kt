package ru.cactus.contactlist.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.cactus.contactlist.R
import ru.cactus.contactlist.databinding.NetworkErrorDialogBinding
import ru.cactus.contactlist.ui.viewmodels.MainViewModel
import kotlin.properties.Delegates

class NetworkErrorDialog : DialogFragment(R.layout.network_error_dialog) {

    private val binding by viewBinding<NetworkErrorDialogBinding>()
    private val viewModel by sharedViewModel<MainViewModel>()
    private var isError by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        isCancelable = false
        return inflater.inflate(R.layout.network_error_dialog, container, false)
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        binding.ibNetworkErrorBtn.setOnClickListener {
            viewModel.loadRawData()
            if (!isError) {
                dismiss()
            }
        }
    }

    private fun setupObservers() {
        viewModel.networkError.observe(viewLifecycleOwner) {
            isError = it
        }
    }
}