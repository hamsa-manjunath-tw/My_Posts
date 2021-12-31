package com.tw.mypost.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tw.mypost.R
import com.tw.mypost.model.MyPosts
import android.view.animation.Animation
import android.view.animation.AnimationUtils


class PostsListAdapter(var posts: ArrayList<MyPosts>) :
    RecyclerView.Adapter<PostsListAdapter.PostsViewHolder>() {

    fun updatePosts(newPosts: List<MyPosts>) {
        posts.clear()
        posts.addAll(newPosts)
        notifyDataSetChanged()

    }

    class PostsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val post_id = view.findViewById<TextView>(R.id.post_id)
        val title = view.findViewById<TextView>(R.id.title)
        val description = view.findViewById<TextView>(R.id.description)
        val edit_posts = view.findViewById<ImageView>(R.id.edit_posts)

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
        holder.edit_posts.setOnClickListener {

            val context = it.context
            var intent: Intent = Intent(context, AddPostsActivity::class.java)

            intent.putExtra("post_id", holder.post_id.text.toString())
            intent.putExtra("title", holder.title.text.toString())
            intent.putExtra("description", holder.description.text.toString())

            context.startActivity(intent)

            val animation: Animation =
                AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right)
            holder.itemView.startAnimation(animation)
        }


    }

    override fun getItemCount() = posts.size


}