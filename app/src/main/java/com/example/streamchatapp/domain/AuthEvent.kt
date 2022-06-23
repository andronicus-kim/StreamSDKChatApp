package com.example.streamchatapp.domain

/**
 * Created by Andronicus Kim on 6/23/22
 */
sealed class AuthEvent{
    object Success: AuthEvent()
    object UserError: AuthEvent()
    data class ApiError(val error: String): AuthEvent()
}
