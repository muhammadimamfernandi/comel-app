package com.taskforce141.comelapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.taskforce141.comelapp.R
import com.taskforce141.comelapp.databinding.FragmentCommunityBinding

class CommunityFragment : Fragment() {

    private lateinit var binding: FragmentCommunityBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.homeNavbar.setOnClickListener{
            it.findNavController().navigate(
                R.id.action_communityFragment_to_homeFragment
            )
        }

        binding.settingsNavbar.setOnClickListener{
            it.findNavController().navigate(
                R.id.action_communityFragment_to_settingsFragment
            )
        }

        binding.searchBar.clearFocus()
    }
}