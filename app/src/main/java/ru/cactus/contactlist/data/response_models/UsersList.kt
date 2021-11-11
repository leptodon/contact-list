package ru.cactus.contactlist.data.response_models

import com.google.gson.annotations.SerializedName

data class UsersList(
    @SerializedName("items") val users : List<User>
)