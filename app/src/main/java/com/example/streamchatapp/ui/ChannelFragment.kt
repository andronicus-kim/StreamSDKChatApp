package com.example.streamchatapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.streamchatapp.databinding.FragmentChannelBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Andronicus Kim on 6/23/22
 */
@AndroidEntryPoint
class ChannelFragment: BaseFragment<FragmentChannelBinding>() {

    private val viewModel: ChannelViewModel by activityViewModels()

    override val fragmentBindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentChannelBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = viewModel.getCurrentUser()
        if (user == null){
            findNavController().popBackStack()
            return
        }
    }
}