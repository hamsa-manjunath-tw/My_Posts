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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_posts)

        add_edit_button = findViewById(R.id.add_edit_button)

        addEditViewModel = ViewModelProviders.of(this).get(AddEditViewModel::class.java)


        add_edit_button.setOnClickListener {
            post_id = findViewById(R.id.post_id)
            post_title = findViewById(R.id.post_title)
            post_description = findViewById(R.id.post_description)


            var newPost: MyPosts = MyPosts(
                0, post_id.text.toString().toLong(),
                post_title.text.toString(), post_description.text.toString()
            )

            addEditViewModel.refresh(newPost)

            finish()

            Toast.makeText(
                this, "The ${newPost.title} got added successfully",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    override fun finish() {
        super.finish()
    }



}