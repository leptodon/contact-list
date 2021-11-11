package ru.cactus.contactlist.data.response_models

import com.google.gson.annotations.SerializedName
import ru.cactus.contactlist.domain.entities.Departments
import ru.cactus.contactlist.domain.entities.SortedBy
import ru.cactus.contactlist.utils.DateHelper

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("avatarUrl") val avatarUrl: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("userTag") val userTag: String,
    @SerializedName("department") val department: Departments,
    @SerializedName("position") val position: String,
    @SerializedName("birthday") val birthday: String,
    @SerializedName("phone") val phone: String,
    var isSortedBy: SortedBy = SortedBy.ALPHABET
) {
    fun getLongDate(): Long {
        return DateHelper.toDateLong(birthday)
    }
}