package com.taskforce141.comelapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.taskforce141.comelapp.LoadingDialog
import com.taskforce141.comelapp.R
import com.taskforce141.comelapp.databinding.FragmentRegisterBinding
import kotlinx.coroutines.*

class RegisterFragment : Fragment() {
    //view binding
    private lateinit var binding: FragmentRegisterBinding
    //firebase auth
    private lateinit var auth: FirebaseAuth
//    private var name = ""
//    private var username = ""
    private var email = ""
    private var password = ""
//    private var gender = ""

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
        //initialize firebase auth
        auth = FirebaseAuth.getInstance()
        //handle click, begin signup
        binding.registerButton.setOnClickListener{checkData()}
        //backbutton
        binding.backButton.setOnClickListener{
            it.findNavController().navigate(
                R.id.action_registerFragment_to_landingFragment
            )
        }
    }
    private fun checkData() {
        //validate email and password
        email = binding.emailTxt.text.toString().trim()
        password = binding.passwordTxt.text.toString().trim()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.emailTxt.error = "Format Email anda tidak sesuai!"
        }else if(TextUtils.isEmpty(password)){
            //password isn't entered
            binding.passwordTxt.error = "Harap masukkan password anda"
        }else if(password.length < 6){
            //password length less than 6
            binding.passwordTxt.error = "Password harus 6 karakter atau lebih"
        }else{
            //if validated
            firebaseSignUp()
        }
    }
    private fun firebaseSignUp() {
        //loading dialog
        val loading = LoadingDialog(requireActivity())
        //start loading
        loading.startLoading()
        //create account
        auth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                    // close loading
                    loading.isDismiss()
                    //get current user
                    val firebaseUser = auth.currentUser
                    val email = firebaseUser!!.email
                    Toast.makeText(
                        requireActivity(), "Akun berhasil didaftarkan!",
                        Toast.LENGTH_SHORT
                    ).show()
                    //open home temporary
                    binding.registerButton.setOnClickListener{
                        it.findNavController().navigate(
                            R.id.action_registerFragment_to_homeFragment
                        )
                    }
            }
            .addOnFailureListener{send->
                loading.isDismiss()
                Toast.makeText(requireActivity(),"Pendaftaran gagal error : ${send.message}",
                    Toast.LENGTH_SHORT).show()
            }
    }
}
