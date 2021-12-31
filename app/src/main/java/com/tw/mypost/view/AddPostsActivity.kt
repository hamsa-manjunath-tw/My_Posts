package com.tw.mypost.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.tw.mypost.R
import com.tw.mypost.model.MyPosts
import com.tw.mypost.viewmodel.AddEditViewModel


class AddPostsActivity : AppCompatActivity() {

    lateinit var post_id: EditText
    lateinit var post_title: EditText
    lateinit var post_description: EditText
    lateinit var add_edit_button: Button

    lateinit var addEditViewModel: AddEditViewModel
    lateinit var newPost: MyPosts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_posts)

        post_id = findViewById<EditText>(R.id.post_id)
        post_title = findViewById<EditText>(R.id.post_title)
        post_description = findViewById<EditText>(R.id.post_description)
        add_edit_button = findViewById(R.id.add_edit_button)
        addEditViewModel = ViewModelProviders.of(this).get(AddEditViewModel::class.java)

        var editPostId = intent.getStringExtra("post_id")


        if (editPostId == null) {
            add_edit_button.text = "ADD"
        } else {
            add_edit_button.text = "UPDATE"
            post_id.setText(intent.getStringExtra("post_id"))
            post_title.setText(intent.getStringExtra("description"))
            post_description.setText(intent.getStringExtra("description"))
        }

        add_edit_button.setOnClickListener {

            newPost = MyPosts(
                0, post_id.text.toString().toLong(),
                post_title.text.toString(), post_description.text.toString()
            )
            addEditViewModel.refresh(newPost, add_edit_button.text.toString())
            finish()
        }
    }

    override fun finish() {
        super.finish()
        if (add_edit_button.text.equals("ADD")) {
            Toast.makeText(
                this, "The ${newPost.title} got added successfully",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this, "The ${newPost.title} got updated successfully",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}