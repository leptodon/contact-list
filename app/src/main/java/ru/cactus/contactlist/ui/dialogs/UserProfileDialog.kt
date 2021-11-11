package ru.cactus.contactlist.ui.dialogs

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import ru.cactus.contactlist.R
import ru.cactus.contactlist.data.response_models.User
import ru.cactus.contactlist.databinding.UserProfileFragmentBinding
import ru.cactus.contactlist.utils.DateHelper
import java.text.SimpleDateFormat
import java.util.*

class UserProfileDialog(private val data: User) : DialogFragment(R.layout.user_profile_fragment) {

    private val binding by viewBinding<UserProfileFragmentBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_profile_fragment, container, false)
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            ivProfileAvatar.load(data.avatarUrl)
            tvProfileFirstname.text = data.firstName
            tvProfileLastname.text = data.lastName
            tvProfileUserTag.text = data.userTag.lowercase(Locale.getDefault())
            tvProfileDepartment.text = getString(data.department.resTitle)
            tvBirthday.text = DateHelper.toRuFormat(data.birthday)
            tvPhoneNumber.text = data.phone
            tvAge.text = DateHelper.getAge(data.birthday)
            tvPhoneNumber.setOnClickListener {
                makePhoneCall(data.phone)
                dismiss()
            }
            ivBackBtnArrow.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun makePhoneCall(number: String): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:+$number")
            startActivity(intent)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}