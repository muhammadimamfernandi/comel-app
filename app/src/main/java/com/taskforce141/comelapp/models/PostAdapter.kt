package com.taskforce141.comelapp.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.taskforce141.comelapp.R
import com.taskforce141.comelapp.databinding.FragmentPostBinding

class PostAdapter(var dataSource: List<PostData>): RecyclerView.Adapter<PostAdapter.PostViewHolder>(){
    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var tvContent: TextView = itemView.findViewById(R.id.tvContent);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_post,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int)  {
        holder.itemView.apply{
            holder.tvContent.text = dataSource[position].postText
        }
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }
}