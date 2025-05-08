package com.example.lbwatch.search

import com.example.lbwatch.model.Item

interface SearchView {
    fun showSearchResults(items: List<Item>)
    fun showError(message: String)
    fun showLoading()
    fun showEmptyState()
}