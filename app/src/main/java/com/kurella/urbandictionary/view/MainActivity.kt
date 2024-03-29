package com.kurella.urbandictionary.view

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kurella.urbandictionary.R
import com.kurella.urbandictionary.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val dictionaryAdapter = DictionaryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        // Toast text observer
        viewModel.getToastLiveData().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        // Updating ViewModel on FAB click
        floatingActionButton.setOnClickListener {
            viewModel.updateIsUpVote()
        }

        // Getting FAB color from ViewModel
        viewModel.getFabColorLiveData().observe(this, Observer {
            floatingActionButton.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, it))
        })

        // Setting progressbar visibility
        viewModel.getShouldShowSpinnerLiveData().observe(this, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
        })

        // Updating recyclerview list
        viewModel.getDefinitionLiveData().observe(this, Observer {
            dictionaryAdapter.setData(it)
        })

        // Sending search query to ViewModel
        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.search(search_bar.query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        setupRecyclerView()
    }

    // Recyclerview Setup
    private fun setupRecyclerView() {
        dict_rv.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = dictionaryAdapter
        }
    }
}
