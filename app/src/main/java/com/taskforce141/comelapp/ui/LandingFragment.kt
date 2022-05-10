package com.taskforce141.comelapp.ui

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.taskforce141.comelapp.R
import com.taskforce141.comelapp.databinding.FragmentLandingBinding

class LandingFragment : Fragment() {
    private lateinit var binding: FragmentLandingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLandingBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.getStartedButton.setOnClickListener{
            it.findNavController().navigate(
                R.id.action_landingFragment_to_registerFragment
            )
        }

        binding.toLogin.setOnClickListener{
            it.findNavController().navigate(
                R.id.action_landingFragment_to_loginFragment
            )
        }
    }
}