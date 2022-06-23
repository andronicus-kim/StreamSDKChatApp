package com.example.streamchatapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Created by Andronicus Kim on 6/23/22
 */

/*
* Abstract class to be implemented by child fragments for
* easier viewbinding management
* */
abstract class BaseFragment<out T: ViewBinding> : Fragment() {

    protected abstract val fragmentBindingInflater: (LayoutInflater) -> ViewBinding

    private var _binding: ViewBinding? = null
    @Suppress("UNCHECKED_CAST")
    protected val binding: T 
        get() = _binding as T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = fragmentBindingInflater(inflater)
        return _binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}