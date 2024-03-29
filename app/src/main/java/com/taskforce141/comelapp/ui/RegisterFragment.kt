package com.taskforce141.comelapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.taskforce141.comelapp.LoadingDialog
import com.taskforce141.comelapp.R
import com.taskforce141.comelapp.databinding.FragmentRegisterBinding
import com.taskforce141.comelapp.models.User
import kotlinx.coroutines.*

class RegisterFragment : Fragment() {
    //view binding
    private lateinit var binding: FragmentRegisterBinding
    //firebase auth
    private lateinit var auth: FirebaseAuth
    //firebase Database
    private lateinit var database: FirebaseDatabase
    //Data User
    private lateinit var name: String
    private lateinit var username: String
    private lateinit var email: String
    private lateinit var password: String

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
        name = binding.nameTxt.text.toString().trim()
        username = binding.usernameTxt.text.toString().trim()
        email = binding.emailTxt.text.toString().trim()
        password = binding.passwordTxt.text.toString().trim()

        if(TextUtils.isEmpty(name)){
            binding.nameTxt.error = "Nama anda kosong"
        }else if(TextUtils.isEmpty(username)){
            binding.usernameTxt.error = "Username anda kosong"
        }else if(TextUtils.isEmpty(email)){
            binding.emailTxt.error = "Email anda kosong"
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailTxt.error = "Format Email anda tidak sesuai"
        }else if(password.length < 6){
            binding.passwordTxt.error = "Password harus 6 karakter atau lebih"
        }else if(TextUtils.isEmpty(password)){
            binding.passwordTxt.error = "Harap masukkan password anda"
        }
        else{
            //if validated
            firebaseSignUp(name,username,email)
        }
    }
    private fun firebaseSignUp(_name: String, _username: String, _email: String) {
        //loading dialog
        val loading = LoadingDialog(requireActivity())
        //start loading
        loading.startLoading()
        //create account
        auth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                    // close loading
                    loading.isDismiss()
                    database = FirebaseDatabase.getInstance(
                        "https://comel-app-972f6-default-rtdb.asia-southeast1.firebasedatabase.app")
                    val firebaseUser = auth.currentUser
                    val email = firebaseUser!!.email
                    val uidUser = firebaseUser.uid
                    val user =  User(_name,_username,_email,"null")
                    val setData = database.getReference("Users")
                        setData.child(uidUser).setValue(user).addOnCompleteListener {
                            Toast.makeText(requireActivity(),
                                "Pengguna Berhasil Ditambahkan!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }.addOnFailureListener { send ->
                            Toast.makeText(requireActivity(),
                                "Pengguna Gagal Ditambahkan! Error : ${send.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    Toast.makeText(
                        requireActivity(),
                        "Akun berhasil dibuat! " + "Selamat Datang $email",
                        Toast.LENGTH_SHORT).show()
                    findNavController().navigate(
                        R.id.action_registerFragment_to_homeFragment
                    )
            }
            .addOnFailureListener{send->
                loading.isDismiss()
                Toast.makeText(requireActivity(),"Pendaftaran gagal error : ${send.message}",
                    Toast.LENGTH_SHORT).show()
            }
    }
}
