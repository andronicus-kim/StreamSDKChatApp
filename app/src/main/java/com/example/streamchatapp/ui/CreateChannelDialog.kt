package com.example.streamchatapp.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.streamchatapp.databinding.DialogCreateChannelBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Created by Andronicus Kim on 6/24/22
 */
class CreateChannelDialog: DialogFragment() {

    private val viewmodel: ChannelViewModel by activityViewModels()

    private var _binding: DialogCreateChannelBinding? = null
    private val binding: DialogCreateChannelBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogCreateChannelBinding.inflate(layoutInflater)
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Creat Channel")
            .setView(binding.root)
            .setPositiveButton("Create") { _,_ ->
                viewmodel.createChannel(binding.etChannelName.text.toString().trim())
            }
            .setNegativeButton("Cancel") {dialog, _ ->
                dialog.cancel()
            }.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}