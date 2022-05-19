package com.taskforce141.comelapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.taskforce141.comelapp.R
import com.taskforce141.comelapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        //navbar bottom
        binding.communityNavbar.setOnClickListener{
            it.findNavController().navigate(
                R.id.action_homeFragment_to_communityFragment
            )
        }
        binding.settingsNavbar.setOnClickListener{
            it.findNavController().navigate(
                R.id.action_homeFragment_to_settingsFragment
            )
        }
    }

    private fun checkUser() {
        //user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            //user logged in
            val email = firebaseUser.email
            binding.usernameTxt.text = email
        }else{
            //user not logged in
            startActivity(Intent(requireActivity(),LoginFragment::class.java))
        }
    }
}