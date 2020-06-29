package com.kurella.urbandictionary.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kurella.urbandictionary.R
import com.kurella.urbandictionary.model.BASE_URL
import com.kurella.urbandictionary.model.UrbanDictApi
import com.kurella.urbandictionary.model.json_data_classes.ListDataItem
import com.kurella.urbandictionary.model.json_data_classes.UrbanDictionaryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    private val isUpVoteSelected = MutableLiveData(true)
    private val definitionListLiveData = MutableLiveData<List<ListDataItem>>()
    private val shouldShowSpinnerLiveData = MutableLiveData(false)
    private val toastStringLiveData = MutableLiveData<String>()
    private val fabColorLiveData = MutableLiveData<Int>(R.color.colorGreen)

    fun getShouldShowSpinnerLiveData() = shouldShowSpinnerLiveData as LiveData<Boolean>

    fun getDefinitionLiveData() = definitionListLiveData as LiveData<List<ListDataItem>>

    // getting definitions making a retrofit call
    fun search(query: String) {
        Log.v("Search query", query)

        shouldShowSpinnerLiveData.value = true

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val callUrbanDictionary = retrofit.create(UrbanDictApi::class.java).userEntry(query)

        callUrbanDictionary.enqueue(object : Callback<UrbanDictionaryResponse> {
            override fun onFailure(call: Call<UrbanDictionaryResponse>, t: Throwable) {
                Log.e("Retrofit Call", t.message)
                toastStringLiveData.value = "Something went wrong, please try again"
                shouldShowSpinnerLiveData.value = false
            }

            override fun onResponse(
                call: Call<UrbanDictionaryResponse>,
                response: Response<UrbanDictionaryResponse>
            ) {
                val unSortedList = response.body()?.listDataItem

                if (unSortedList.isNullOrEmpty()) {
                    toastStringLiveData.value = "No definitions found, try using a different word"
                    shouldShowSpinnerLiveData.value = false
                    return
                }

                sortList(unSortedList)
            }
        })
    }

    // Sorting list based on user choice
    fun sortList(unSortedList: List<ListDataItem>) {
        isUpVoteSelected.value?.also { isUpVote ->
            if (isUpVote) {
                definitionListLiveData.value = unSortedList.sortedByDescending { it.thumbs_up }
            } else {
                definitionListLiveData.value = unSortedList.sortedByDescending { it.thumbs_down }
            }
        }

        shouldShowSpinnerLiveData.value = false
        toastStringLiveData.value = "List updated"
    }

    // Updating isUpVote and changing FAB color
    fun updateIsUpVote() {
        isUpVoteSelected.value = isUpVoteSelected.value?.not()
        definitionListLiveData.value?.also { sortList(it) }

        isUpVoteSelected.value?.also {
            fabColorLiveData.value = if (it) R.color.colorGreen else R.color.colorRed
        }

    }

    fun getFabColorLiveData() = fabColorLiveData as LiveData<Int>

    fun getToastLiveData() = toastStringLiveData as LiveData<String>
}