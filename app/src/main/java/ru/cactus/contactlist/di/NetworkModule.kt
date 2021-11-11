package ru.cactus.contactlist.di

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.cactus.contactlist.Const
import ru.cactus.contactlist.data.api.ApiService

val networkModule = module {
    single { provideRetrofit() }
    single { provideApi<ApiService>(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(getBaseUrl())
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun getBaseUrl(): String {
    return Const.BASE_URL
}

inline fun <reified T> provideApi(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}