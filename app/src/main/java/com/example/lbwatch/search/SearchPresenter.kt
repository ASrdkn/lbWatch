package com.example.lbwatch.search

import com.example.lbwatch.api.ClientAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SearchPresenter(private val view: SearchView) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.omdbapi.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val clientAPI: ClientAPI = retrofit.create(ClientAPI::class.java)

    fun searchMovies(query: String) {
        view.showLoading()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = clientAPI.fetchResponse("c54d9bf7", query)
                withContext(Dispatchers.Main) {
                    val items = response.items ?: emptyList()
                    if (items.isEmpty()) {
                        view.showEmptyState()
                    } else {
                        view.showSearchResults(items)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view.showError("Ошибка при поиске: ${e.message}")
                }
            }
        }
    }
}
