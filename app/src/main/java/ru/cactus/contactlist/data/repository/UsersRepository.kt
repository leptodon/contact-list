package ru.cactus.contactlist.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.cactus.contactlist.data.api.ApiService
import ru.cactus.contactlist.data.response_models.UsersList

class UsersRepository(private val apiService: ApiService) {

    fun getResponse(): Flow<UsersList?> = flow {
        val response = apiService.getResponseList()
        emit(response)
    }.flowOn(Dispatchers.IO)
}