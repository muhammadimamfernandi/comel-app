package com.taskforce141.comelapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
    private lateinit var dataPost: ArrayList<PostData>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataPost = ArrayList()
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance(
            "https://comel-app-972f6-default-rtdb.asia-southeast1.firebasedatabase.app"
        )
        checkUser()
//        //Dummy Data post
//        var dataPost = ArrayList<PostData>()
//        dataPost.add(PostData("abcd","efg","aku syedih uhuhuuhuh"))
//        dataPost.add(PostData("abcd","efg","abcdefgh"))
//        dataPost.add(PostData("abcd","efg","abcdefgh"))
        //firebase data post
        sendPost()
        //load post from firebase
        loadPost()
        adapter = PostAdapter(dataPost)
        binding.rvPost.adapter = adapter
        binding.rvPost.layoutManager = LinearLayoutManager(requireContext())
        //navbar bottom
        navbarBottom()
    }
    private fun loadPost(){
        val myref = database.reference
        myref.child("posts").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val value = snapshot.value as HashMap<String, Any>

                    for(key in value.keys){
                        var postKey = value[key] as HashMap<String, Any>
                        dataPost.add(
                            PostData(
                                key,
                                postKey["userId"].toString(),
                                postKey["postText"].toString()
                            )
                        )
                        Toast.makeText(requireActivity(),"Berhasil Posting! : $postKey",
                            Toast.LENGTH_SHORT).show()
                    }
                    adapter.notifyDataSetChanged()
                }catch (ex: Exception){
                    Log.w("tag", "loadPost:onCancelled", ex)
                    Toast.makeText(requireActivity(),"error Exception : ${ex}",
                        Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("tag", "error database $error")
                Toast.makeText(requireActivity(),"error Database : ${error}",
                    Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun sendPost(){
        val myref = database.reference
        val uidUser = firebaseAuth.currentUser?.uid
         binding.sendButton.setOnClickListener {
            myref.child("posts").push().setValue(
                PostInfo(uidUser,binding.postTextfield.text.toString())
            )
            binding.postTextfield.text.clear()

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
        database = FirebaseDatabase.getInstance(
            "https://comel-app-972f6-default-rtdb.asia-southeast1.firebasedatabase.app"
        )
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
