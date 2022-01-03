package com.tw.mypost.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tw.mypost.R
import com.tw.mypost.viewmodel.ListViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import com.tw.mypost.model.MyPosts


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel
    private val postsAdapter = PostsListAdapter(arrayListOf())

    lateinit var postsList: RecyclerView
    lateinit var list_error: TextView
    lateinit var loading_view: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        list_error = findViewById(R.id.list_error)
        loading_view = findViewById(R.id.loading_view)
        postsList = findViewById(R.id.postsList)

        postsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = postsAdapter
        }

        observeViewModel()

        // Need to check direction for swipe here direction of swipe
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                AlertDialog.Builder(viewHolder.itemView.context)
                    .setMessage(" Are you sure ?")
                    .setPositiveButton("Yes") { dialogInterface: DialogInterface, position: Int ->
                        val position = viewHolder.adapterPosition

                        postsAdapter.posts.removeAt(position)
                        postsAdapter.notifyItemRemoved(position)
                        Toast.makeText(
                            viewHolder.itemView.context, "The post got deleted successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .setNegativeButton("No") { dialogInterface: DialogInterface, position: Int ->
                        postsAdapter.notifyItemChanged(viewHolder.adapterPosition)
                    }.show()
            }
        }).attachToRecyclerView(postsList)
    }

    fun observeViewModel() {
        viewModel.posts.observe(this, Observer { posts ->
            posts?.let {
                postsList.visibility = View.VISIBLE
                postsAdapter.updatePosts(it)
            }
        })

        viewModel.postsLoadError.observe(this, Observer { isError ->
            isError?.let {
                list_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loadingError.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE

                if (it) {
                    list_error.visibility = View.GONE
                    postsList.visibility = View.GONE
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        var menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.menu_items, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addPosts -> {
                var addIntent: Intent = Intent(this, AddPostsActivity::class.java)

                startActivity(addIntent)
                overridePendingTransition(R.anim.slide_down, R.anim.slide_up)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}