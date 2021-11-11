package ru.cactus.contactlist.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.cactus.contactlist.R
import ru.cactus.contactlist.databinding.FilterDialogBinding
import ru.cactus.contactlist.domain.entities.SortedBy

class FilterDialog(
    private val selectedSortedBy: SortedBy?,
    private val sortedBy: (SortedBy) -> Unit
) : BottomSheetDialogFragment() {

    private val binding by viewBinding<FilterDialogBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.filter_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        with(binding) {
            when (selectedSortedBy) {
                SortedBy.ALPHABET -> rbFilterAlphabet.isChecked = true
                SortedBy.BIRTHDAY -> rbFilterBirthday.isChecked = true
                else -> {}
            }

            rbFilterAlphabet.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sortedBy.invoke(SortedBy.ALPHABET)
                    rbFilterBirthday.isChecked = false
                }
            }

            rbFilterBirthday.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sortedBy.invoke(SortedBy.BIRTHDAY)
                    rbFilterAlphabet.isChecked = false
                }
            }
        }
    }
}