package com.taskforce141.comelapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.taskforce141.comelapp.R
import com.taskforce141.comelapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding : FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.homeNavbar.setOnClickListener{
            it.findNavController().navigate(
                R.id.action_settingsFragment_to_homeFragment
            )
        }

        binding.communityNavbar.setOnClickListener{
            it.findNavController().navigate(
                R.id.action_settingsFragment_to_communityFragment
            )
        }
    }

}