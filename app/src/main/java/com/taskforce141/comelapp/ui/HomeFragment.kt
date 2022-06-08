package com.taskforce141.comelapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.taskforce141.comelapp.R
import com.taskforce141.comelapp.databinding.FragmentHomeBinding
import com.taskforce141.comelapp.databinding.FragmentPostBinding
import com.taskforce141.comelapp.models.PostAdapter
import com.taskforce141.comelapp.models.PostData
import com.taskforce141.comelapp.models.PostInfo

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var adapter: PostAdapter
    private lateinit var database: FirebaseDatabase
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
        //Dummy Data post
        var dataPost = ArrayList<PostData>()
        dataPost.add(PostData("abcd","efg","aku syedih uhuhuuhuh"))
        dataPost.add(PostData("abcd","efg","abcdefgh"))
        dataPost.add(PostData("abcd","efg","abcdefgh"))
        //firebase data post
        sendPost()
        adapter = PostAdapter(dataPost)
        binding.rvPost.adapter = adapter
        binding.rvPost.layoutManager = LinearLayoutManager(requireContext())

        navbarBottom()
    }
    private fun sendPost(){
        database = FirebaseDatabase.getInstance(
            "https://comel-app-972f6-default-rtdb.asia-southeast1.firebasedatabase.app"
        )
        val myref = database.reference
        val uidUser = firebaseAuth.currentUser?.uid
        val textPost = binding.postTextfield.text.toString()
        binding.sendButton.setOnClickListener {
            myref.child("posts").push().setValue(
                PostInfo(uidUser,textPost)
            )
        }
    }
    private fun navbarBottom(){
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
            findNavController().navigate(
                R.id.action_homeFragment_to_loginFragment
            )
        }

    }
}
