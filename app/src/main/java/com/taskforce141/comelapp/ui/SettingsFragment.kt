package com.taskforce141.comelapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.taskforce141.comelapp.R
import com.taskforce141.comelapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding : FragmentSettingsBinding
    private lateinit var firebaseAuth: FirebaseAuth

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
        binding.logoutBtn2.setOnClickListener{
            firebaseAuth.signOut()
            findNavController().navigate(
                R.id.action_settingsFragment_to_loginFragment
            )
        }
        //navbar bottom
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
    private fun checkUser() {
        //user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            //user logged in
            val email = firebaseUser.email
        }else{
            //user not logged in
            findNavController().navigate(
                R.id.action_settingsFragment_to_loginFragment
            )
        }
    }

}