package com.tw.mypost.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import com.tw.mypost.R
import com.tw.mypost.model.MyPosts
import com.tw.mypost.viewmodel.AddEditViewModel


class AddPostsActivity : AppCompatActivity() {

    lateinit var post_id: TextInputEditText
    lateinit var post_title: TextInputEditText
    lateinit var post_description: TextInputEditText
    lateinit var add_edit_button: Button

    lateinit var addEditViewModel: AddEditViewModel
    lateinit var newPost: MyPosts

    lateinit var actionTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_posts)

        post_id = findViewById<TextInputEditText>(R.id.post_id)
        post_title = findViewById<TextInputEditText>(R.id.post_title)
        post_description = findViewById<TextInputEditText>(R.id.post_description)
        add_edit_button = findViewById(R.id.add_edit_button)
        actionTitle = findViewById(R.id.actionTitle)
        addEditViewModel = ViewModelProviders.of(this).get(AddEditViewModel::class.java)

        var editPostId = intent.getStringExtra("post_id")


        if (editPostId == null) {
            add_edit_button.text = "ADD"
            actionTitle.text = "ADD POST"
        } else {
            add_edit_button.text = "UPDATE"
            actionTitle.text = "EDIT POST"
            post_id.setText(intent.getStringExtra("post_id"))
            post_title.setText(intent.getStringExtra("description"))
            post_description.setText(intent.getStringExtra("description"))
        }

        add_edit_button.setOnClickListener {
            if (post_id.text.toString() == null || post_id.text.toString() == "") {
                post_id.setError("Id is mandatory !")
            } else {
                newPost = MyPosts(
                    0, post_id.text.toString().toLong(),
                    post_title.text.toString(), post_description.text.toString()
                )
                addEditViewModel.refresh(newPost, add_edit_button.text.toString())
                finish()
            }

        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down)
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