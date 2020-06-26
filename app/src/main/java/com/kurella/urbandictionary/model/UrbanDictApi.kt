package com.kurella.urbandictionary.model

import com.kurella.urbandictionary.model.json_data_classes.UrbanDictionaryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

const val BASE_URL = "https://mashape-community-urban-dictionary.p.rapidapi.com/"
const val PATH = "define"
const val HOST_HEADER = "x-rapidapi-host"
const val HOST_HEADER_VAL = "mashape-community-urban-dictionary.p.rapidapi.com"
const val HEADER_KEY = "x-rapidapi-key"
const val HOST_KEY_VAL = "e802946cb9mshe0f66a129d568bep1831a3jsn7f3cec490c67"

interface UrbanDictApi {

    @GET(PATH)
    fun userEntry(@Query("term") input: String,
                  @Header(HOST_HEADER) host: String = HOST_HEADER_VAL,
                  @Header(HEADER_KEY) key: String = HOST_KEY_VAL ) : Call<UrbanDictionaryResponse>
}