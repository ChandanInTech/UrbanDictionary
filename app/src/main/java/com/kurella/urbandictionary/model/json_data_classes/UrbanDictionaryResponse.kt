package com.kurella.urbandictionary.model.json_data_classes

import com.google.gson.annotations.SerializedName

data class UrbanDictionaryResponse(
    @SerializedName("list")
    val listDataItem: List<ListDataItem>
)