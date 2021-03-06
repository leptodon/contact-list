package ru.cactus.contactlist.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.cactus.contactlist.data.repository.UsersRepository
import ru.cactus.contactlist.data.response_models.User
import ru.cactus.contactlist.domain.entities.Departments
import ru.cactus.contactlist.domain.entities.SortedBy
import ru.cactus.contactlist.utils.DateHelper
import ru.cactus.contactlist.utils.asLiveData

class MainViewModel(
    private val repo: UsersRepository
) : ViewModel() {

    private val _isProgress = MutableLiveData<Boolean>()
    val isProgress: LiveData<Boolean> = _isProgress

    private val _mapOfUsersByDepartment = MutableLiveData<Map<String, List<User>>>()
    val mapOfUsersByDepartment = _mapOfUsersByDepartment

    private val _networkError = MutableLiveData<Boolean>()
    val networkError = _networkError.asLiveData()

    var isSortedBy: SortedBy? = null
        set(value) {
            field = value
            value ?: return
            usersList.value?.forEach { it.isSortedBy = value }
            usersList.value?.let { sortedBy(it) }
        }

    private val _usersList = MutableLiveData<List<User>>()
    private var usersList = _usersList.asLiveData()

    private fun loadData() = viewModelScope.launch {
        loadUsersList()
            .onStart { _isProgress.postValue(true) }
            .onCompletion { _isProgress.postValue(false) }
            .catch {
                _networkError.postValue(true)
                _isProgress.postValue(false)
            }
            .collect {
                _networkError.postValue(false)
                return@collect
            }
    }

    fun loadRawData() {
        when (isSortedBy) {
            SortedBy.ALPHABET -> loadData()
            SortedBy.BIRTHDAY -> {
                sortedBy(usersList.value!!)
                _isProgress.postValue(false)
            }
            else -> loadData()
        }
    }

    private fun loadUsersList() = repo.getResponse().map { response ->
        val items = response?.users ?: emptyList()
        _isProgress.postValue(true)
        return@map if (items.isNullOrEmpty()) {
            _isProgress.postValue(false)
            null
        } else {
            _isProgress.postValue(false)
            _usersList.value = items
            setUsersToMap(items)
        }
    }

    private fun filterUsers(list: List<User>, departments: Departments) =
        list.filter { it.department == departments }


    private fun setUsersToMap(list: List<User>) {
        val mapOfUsers = mutableMapOf<String, List<User>>()
        Departments.values().forEach { depart ->
            when (depart) {
                Departments.ALL -> mapOfUsers[depart.name] = list
                else -> mapOfUsers[depart.name] = filterUsers(list, depart)
            }
        }
        _mapOfUsersByDepartment.postValue(mapOfUsers)
    }

    private fun sortedBy(listUsers: List<User>) {
        if (listUsers.isNullOrEmpty()) {
            _usersList.postValue(emptyList())
            return
        }

        val sortedList = isSortedBy?.let {
            when (it) {
                SortedBy.ALPHABET -> {
                    return@let listUsers.sortedBy { user -> user.firstName }
                }
                SortedBy.BIRTHDAY -> {
                    return@let getBirthdaysByTwoLists(listUsers)
                }
            }
        }

        _usersList.postValue(sortedList ?: emptyList())
        setUsersToMap(sortedList ?: emptyList())
    }

    fun searchUsers(text: String) {
        val tempList = arrayListOf<User>()
        text.let {
            usersList.value?.forEach { user ->
                if (user.firstName.contains(text, true) ||
                    user.lastName.contains(text, true) ||
                    user.userTag.contains(text, true)
                ) {
                    tempList.add(user)
                }
            }

        }
        sortedBy(tempList)
    }

    private fun getBirthdaysByTwoLists(list: List<User>): List<User> {
        val fullList = mutableListOf<User>()
        val listBefore = mutableListOf<User>()
        val listAfter = mutableListOf<User>()

        list.forEach { user ->
            if (DateHelper.compareWithCurrentDay(user.birthday)) {
                listAfter.add(user)
            } else {
                listBefore.add(user)
            }
        }

        fullList.addAll(listAfter.sortedBy { DateHelper.getSortFormat(it.birthday) })
        fullList.addAll(listBefore.sortedBy { DateHelper.getSortFormat(it.birthday) })

        return fullList
    }

}