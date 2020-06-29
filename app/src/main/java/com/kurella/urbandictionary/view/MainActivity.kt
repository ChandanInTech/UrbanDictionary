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
import com.kurella.urbandictionary.model.json_data_classes.ListDataItem
import com.kurella.urbandictionary.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var definitionList = ArrayList<ListDataItem>()
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }


    override fun onResume() {
        super.onResume()

        viewModel.getToastLiveData().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        floatingActionButton.setOnClickListener {
            viewModel.updateIsUpVote()
        }

        viewModel.getIsUpVoteSelectedLiveData().observe(this, Observer {
            if (it) {
                floatingActionButton.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorGreen))
            } else {
                floatingActionButton.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorRed))
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
