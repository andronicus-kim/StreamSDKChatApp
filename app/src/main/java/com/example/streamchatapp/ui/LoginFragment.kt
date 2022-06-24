package com.example.streamchatapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.streamchatapp.R
import com.example.streamchatapp.databinding.FragmentLoginBinding
import com.example.streamchatapp.domain.AuthEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * Created by Andronicus Kim on 6/23/22
 */
@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>() {

    private val viewModel: AuthViewModel by viewModels()

    override val fragmentBindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentLoginBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener {
            setUIState(true)
            viewModel.signInUser(binding.etUsername.text.toString().trim())
        }

        binding.etUsername.addTextChangedListener {
            // clear error when text changes
            binding.etUsername.error = null
        }

        subscribeToEvents()
    }

    private fun setUIState(isLoading: Boolean) {
       if (isLoading) {
           binding.progressBar.isVisible = true
           binding.btnSubmit.isEnabled = false
       }else {
           binding.progressBar.isVisible = false
           binding.btnSubmit.isEnabled = true
       }
    }

    private fun subscribeToEvents(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.authEvent.collect { event ->
                setUIState(false)
                when(event) {
                    is AuthEvent.Success -> {
                        // Navigate to Channels List
                        findNavController().navigate(R.id.action_loginFragment_to_channelFragment)
                    }
                    is AuthEvent.ApiError -> {
                        Toast.makeText(requireContext(),event.error,Toast.LENGTH_LONG).show()
                    }
                    AuthEvent.UserError -> {
                        binding.etUsername.error = getString(R.string.invalid_username)
                    }
                }
            }
        }
    }
}