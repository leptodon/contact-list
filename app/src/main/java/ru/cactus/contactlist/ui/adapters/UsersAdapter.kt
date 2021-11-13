package ru.cactus.contactlist.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.cactus.contactlist.R
import ru.cactus.contactlist.data.response_models.User
import ru.cactus.contactlist.databinding.UsersListItemBinding
import ru.cactus.contactlist.domain.entities.SortedBy
import ru.cactus.contactlist.utils.DateHelper
import java.util.*

class UsersAdapter(private val onClick: (User) -> Unit) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private val items = mutableListOf<User>()

    @SuppressLint("NotifyDataSetChanged")
    fun showList(list: List<User>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemBinding =
            UsersListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(itemBinding, onClick)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    inner class UserViewHolder(
        private val binding: UsersListItemBinding,
        private val onClick: (User) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: User) {
            if (data.firstName == "") {
                with(binding) {
                    llUserName.background =
                        AppCompatResources.getDrawable(root.context, R.drawable.blank_name)
                    tvDepartment.background =
                        AppCompatResources.getDrawable(root.context, R.drawable.blank_department)
                    ivAvatar.load(R.drawable.empty_avatar)
                }
            } else {
                with(binding) {
                    llUserName.background = null
                    tvDepartment.background = null
                    tvFirstName.text = data.firstName
                    tvLastName.text = data.lastName
                    tvUserTag.text = data.userTag.lowercase(Locale.getDefault())
                    tvDepartment.text = root.resources.getString(data.department.resTitle)
                    ivAvatar.load(data.avatarUrl)
                    itemView.setOnClickListener {
                        onClick.invoke(data)
                    }
                    root.setOnClickListener {
                        onClick.invoke(data)
                    }
                    when (data.isSortedBy ?: SortedBy.ALPHABET) {
                        SortedBy.BIRTHDAY -> {
                            tvBirthdayDate.text = DateHelper.toMonthAndDayFormat(data.birthday)
                            tvBirthdayDate.visibility = View.VISIBLE
                        }
                        else -> tvBirthdayDate.visibility = View.GONE
                    }
                }
            }

        }
    }

}