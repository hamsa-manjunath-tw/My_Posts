package com.tw.mypost.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tw.mypost.R
import com.tw.mypost.viewmodel.ListViewModel
import com.google.android.material.snackbar.Snackbar
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

                val deletedData: MyPosts =
                    postsAdapter.posts.get(viewHolder.adapterPosition)

                val position = viewHolder.adapterPosition

                postsAdapter.posts.removeAt(viewHolder.adapterPosition)

                postsAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                Snackbar.make(postsList, deletedData.title, Snackbar.LENGTH_LONG)
                    .setAction("Undo",
                        View.OnClickListener { // adding on click listener to our action of snack bar.
                            // below line is to add our item to array list with a position.
                            postsAdapter.posts.add(position, deletedData)
                            postsAdapter.notifyItemInserted(position)
                        }).show()
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
                //overridePendingTransition(R.anim.slide_down, R.anim.slide_up)
               overridePendingTransition(R.anim.slide_down, R.anim.slide_up)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}