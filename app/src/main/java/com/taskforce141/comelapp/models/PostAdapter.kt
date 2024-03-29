package com.taskforce141.comelapp.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.taskforce141.comelapp.R

class PostAdapter(var dataSource: ArrayList<PostData>): RecyclerView.Adapter<PostAdapter.PostViewHolder>(){
    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var tvContent: TextView = itemView.findViewById(R.id.tvContent)
        var nameTv: TextView = itemView.findViewById(R.id.nameTv)
        var usernameTv: TextView = itemView.findViewById(R.id.usernameTv)
        var love: Button = itemView.findViewById(R.id.loveButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_post,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int)  {
        val currentItem = dataSource[position]
        holder.itemView.apply{
            holder.tvContent.text = currentItem.postText
//            holder.nameTv.text = currentItem.name
//            holder.usernameTv.text = currentItem.username

        }
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }
}
