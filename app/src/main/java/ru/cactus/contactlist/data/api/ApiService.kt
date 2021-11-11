package ru.cactus.contactlist.data.api

import retrofit2.http.GET
import retrofit2.http.Headers
import ru.cactus.contactlist.data.response_models.UsersList


interface ApiService {

    /** Запрос для получения успешного ответа: **/
        @Headers(
        "Content-Type: application/json",
        "Prefer: code=200, example=success"
    )
    @GET("mocks/kode-education/trainee-test/25143926/users")
    suspend fun getResponseList(): UsersList

    /** Запрос для тестирования на разных данных (генерируется автоматически при каждом запросе): **/
/*        @Headers(
        "Content-Type: application/json",
        "Prefer: code=200, dynamic=true"
    )
    @GET("mocks/kode-education/trainee-test/25143926/users")
    suspend fun getResponseList(): UsersList*/

    /** Запрос, который вернет ошибку с http кодом 500: **/
/*    @Headers(
        "Content-Type: application/json",
        "Prefer: code=500, example=error-500"
    )
    @GET("mocks/kode-education/trainee-test/25143926/users")
    suspend fun getResponseList(): UsersList*/
}