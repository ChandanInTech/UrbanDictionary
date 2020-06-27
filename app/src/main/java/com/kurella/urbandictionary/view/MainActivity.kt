package com.kurella.urbandictionary.view

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.kurella.urbandictionary.R
import com.kurella.urbandictionary.model.MeaningData
import com.kurella.urbandictionary.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var definitionList = ArrayList<MeaningData>()
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }


    override fun onResume() {
        super.onResume()

        floatingActionButton.setOnClickListener {
            viewModel.updateIsUpVote()
        }

        viewModel.getIsUpVoteSelectedLiveData().observe(this, Observer {
            if (it) {

            } else {

            }
        })

        viewModel.getShouldShowSpinnerLiveData().observe(this, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
        })

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

        viewModel.getDefinitionLiveData().observe(this, Observer {
            definitionList.clear()
            definitionList.addAll(it)
            dict_rv.adapter?.notifyDataSetChanged()
            dict_rv.smoothScrollToPosition(0)
        })
    }

    private fun setupRecyclerView() {
        dict_rv.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = DictionaryAdapter(definitionList)
        }
    }
}
