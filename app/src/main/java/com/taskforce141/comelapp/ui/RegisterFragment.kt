package com.taskforce141.comelapp.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.taskforce141.comelapp.R
import com.taskforce141.comelapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    //view binding
    private lateinit var binding: FragmentRegisterBinding
    //firebase initialize
    private lateinit var auth: FirebaseAuth
    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.backButton.setOnClickListener{
            it.findNavController().navigate(
                R.id.action_registerFragment_to_landingFragment
            )
        }
        binding.registerButton.setOnClickListener{
            it.findNavController().navigate(
                R.id.action_registerFragment_to_homeFragment
            )
        }
    }
}