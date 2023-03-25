package io.codetheworld.viewmodelunittestdemo

import kotlinx.coroutines.delay

class UserRepository {
    /**
     * @throws IllegalAccessException
     */
    suspend fun login(username: String, password: String): LoginResponse {
        delay(3000)

        if (username == "admin" && password == "admin") {
            return LoginResponse("accessToken")
        }

        throw IllegalAccessException("Username or password is not correct!")
    }

    data class LoginResponse(
        val accessToken: String
    )
}
