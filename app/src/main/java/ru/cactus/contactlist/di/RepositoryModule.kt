package ru.cactus.contactlist.di

import org.koin.dsl.module
import ru.cactus.contactlist.data.repository.UsersRepository

val repositoryModule = module {
    single { UsersRepository(get()) }
}