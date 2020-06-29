package com.kurella.urbandictionary.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kurella.urbandictionary.DefinitionDiffCallBacks
import com.kurella.urbandictionary.R
import com.kurella.urbandictionary.model.json_data_classes.ListDataItem
import kotlinx.android.synthetic.main.response_item.view.*

class DictionaryAdapter :
    RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder>() {

    private val definitionList = ArrayList<ListDataItem>()

    class DictionaryViewHolder(val defView: View) : RecyclerView.ViewHolder(defView)

    fun setData(newDefinitionList: List<ListDataItem>) {
        val diffResults =
            DiffUtil.calculateDiff(DefinitionDiffCallBacks(newDefinitionList, definitionList))

        definitionList.clear()
        definitionList.addAll(newDefinitionList)

        diffResults.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        val defView = LayoutInflater.from(parent.context)
            .inflate(R.layout.response_item, parent, false) as View

        return DictionaryViewHolder(
            defView
        )
    }

    override fun getItemCount() = definitionList.size

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        val itemData = definitionList[position]
        holder.defView.definition.text = itemData.definition
        holder.defView.up_votes.text = itemData.thumbs_up.toString()
        holder.defView.down_votes.text = itemData.thumbs_down.toString()
    }
}