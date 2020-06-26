package com.kurella.urbandictionary.view

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
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
            definitionList.addAll(it as ArrayList<MeaningData>)
            dict_rv.adapter?.notifyDataSetChanged()

            Log.v("dfgsdfwe", definitionList.toString())
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
