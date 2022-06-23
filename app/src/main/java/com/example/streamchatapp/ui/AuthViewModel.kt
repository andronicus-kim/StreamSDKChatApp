package com.example.streamchatapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamchatapp.domain.AuthEvent
import com.example.streamchatapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.await
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Andronicus Kim on 6/23/22
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val chatClient: ChatClient
): ViewModel() {

    private val _authEvent = MutableSharedFlow<AuthEvent>()
    val authEvent = _authEvent.asSharedFlow()

    private fun isValidUsername(username: String) = username.length >= Constants.MIN_USERNAME_LENGTH

    fun signInUser(username: String){
        viewModelScope.launch {
            if (isValidUsername(username)){
                // Make Network Call
                val result = chatClient.connectGuestUser(username,username).await()
                // Handle Network Response
                if (result.isError){
                    _authEvent.emit(AuthEvent.ApiError(result.error().message ?: "An error occurred!"))
                    return@launch
                }
                _authEvent.emit(AuthEvent.Success)
            }else {
                _authEvent.emit(AuthEvent.UserError)
            }
        }
    }
}