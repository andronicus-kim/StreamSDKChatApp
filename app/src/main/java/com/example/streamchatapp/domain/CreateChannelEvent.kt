package com.example.streamchatapp.domain

/**
 * Created by Andronicus Kim on 6/24/22
 */
sealed class CreateChannelEvent {
    object Success: CreateChannelEvent()
    data class ApiError(val error: String): CreateChannelEvent()
}