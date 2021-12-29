package com.tw.mypost.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tw.mypost.R
import com.tw.mypost.model.MyPosts

class PostsListAdapter(var posts: ArrayList<MyPosts>) :
    RecyclerView.Adapter<PostsListAdapter.PostsViewHolder>() {

    fun updatePosts(newPosts: List<MyPosts>) {
        posts.clear()
        posts.addAll(newPosts)
        notifyDataSetChanged()

    }

    class PostsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val post_id = view.findViewById<TextView>(R.id.post_id)
        private val title = view.findViewById<TextView>(R.id.title)
        private val description = view.findViewById<TextView>(R.id.description)

        fun bind(post: MyPosts) {
            post_id.text = post.id.toString()
            title.text = post.title
            description.text = post.body

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
    )

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size


}