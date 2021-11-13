package ru.cactus.contactlist.utils

import ru.cactus.contactlist.data.response_models.User
import ru.cactus.contactlist.domain.entities.Departments
import ru.cactus.contactlist.domain.entities.SortedBy

object Utils {
    private val user = User(
        "01",
        "",
        "",
        "",
        "",
        Departments.ALL,
        "",
        "",
        "",
        SortedBy.ALPHABET,
    )

    fun getBlankUsersList(): List<User> {
        val blankList = mutableListOf<User>()
        for (i in 0..15) {
            blankList.add(user)
        }
        return blankList
    }
}