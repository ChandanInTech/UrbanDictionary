package com.kurella.urbandictionary

import androidx.recyclerview.widget.DiffUtil
import com.kurella.urbandictionary.model.json_data_classes.ListDataItem

class DefinitionDiffCallBacks(
    private val newList: List<ListDataItem>,
    private val oldList: List<ListDataItem>
) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition].defid == oldList[oldItemPosition].defid
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition] == oldList[oldItemPosition]
    }
}