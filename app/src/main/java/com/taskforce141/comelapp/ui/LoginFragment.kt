package com.taskforce141.comelapp.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.PatternMatcher
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.taskforce141.comelapp.LoadingDialog
import com.taskforce141.comelapp.R
import com.taskforce141.comelapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //initialize firebase account
        firebaseAuth = FirebaseAuth.getInstance()
        //back handler button
        binding.backButton.setOnClickListener{
            it.findNavController().navigate(
                R.id.action_loginFragment_to_landingFragment
            )
        }
        // login handler button
        binding.loginButton.setOnClickListener{
            validateData()
        }

    }

    private fun validateData() {
        email = binding.emailTxt.text.toString().trim()
        password = binding.passwordTxt.text.toString().trim()
        // validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.emailTxt.error = "Format Email anda tidak sesuai!"
        }else if(TextUtils.isEmpty(email)){
            //email isn't entered
            //invalid email format
            binding.emailTxt.error = "Harap masukkan email anda"
        } else if(TextUtils.isEmpty(password)){
            //password isn't entered
            binding.passwordTxt.error = "Harap masukkan password anda"
        }else{
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        //loading dialog
        val loading = LoadingDialog(requireActivity())
        //start loading
        loading.startLoading()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //close loading
                loading.isDismiss()
                //get current user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                findNavController().navigate(
                    R.id.action_loginFragment_to_homeFragment
                )
                Toast.makeText(requireActivity(),"Selamat Bercurhat! ${email}",
                    Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{ send ->
                loading.isDismiss()
                Toast.makeText(requireActivity(),"Pendaftaran gagal error : ${send.message}",
                    Toast.LENGTH_SHORT).show()
            }
    }
}