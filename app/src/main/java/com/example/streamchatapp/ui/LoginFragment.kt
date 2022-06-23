package com.example.streamchatapp.ui

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.example.streamchatapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Andronicus Kim on 6/23/22
 */
@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>() {

    override val fragmentBindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentLoginBinding::inflate
}