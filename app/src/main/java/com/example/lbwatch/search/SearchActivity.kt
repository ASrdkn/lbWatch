package com.example.lbwatch.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lbwatch.R
import com.example.lbwatch.model.Item

class SearchActivity : AppCompatActivity(), SearchView, SearchAdapterListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private lateinit var noMoviesTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: SearchPresenter
    private var query = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recyclerView = findViewById(R.id.search_results_recyclerview)
        progressBar = findViewById(R.id.progress_bar)
        noMoviesTextView = findViewById(R.id.no_movies_textview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val intent = intent
        query = intent.getStringExtra(SEARCH_QUERY) ?: ""

        // Инициализация презентера
        presenter = SearchPresenter(this)

        presenter.searchMovies(query)
    }

    // Реализуем методы из SearchView
    override fun showSearchResults(items: List<Item>) {
        noMoviesTextView.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
        adapter = SearchAdapter(items, this@SearchActivity, this@SearchActivity)
        recyclerView.adapter = adapter
        progressBar.visibility = View.INVISIBLE
    }

    override fun showError(message: String) {
        progressBar.visibility = View.INVISIBLE
        noMoviesTextView.visibility = View.VISIBLE
        noMoviesTextView.text = message
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
        noMoviesTextView.visibility = View.INVISIBLE
    }

    override fun showEmptyState() {
        progressBar.visibility = View.INVISIBLE
        recyclerView.visibility = View.INVISIBLE
        noMoviesTextView.visibility = View.VISIBLE
        noMoviesTextView.text = "Нет фильмов для данного поиска"
    }

    // Реализуем метод из SearchAdapterListener
    override fun onItemClick(view: View, position: Int) {
        val movie = adapter.getItemAtPosition(position)
        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_TITLE, movie.title)
        replyIntent.putExtra(EXTRA_RELEASE_DATE, movie.getReleaseYearFromDate().toString())
        replyIntent.putExtra(EXTRA_POSTER_PATH, movie.posterPath)
        setResult(RESULT_OK, replyIntent)
        finish()
    }

    companion object {
        const val SEARCH_QUERY = "searchQuery"
        const val EXTRA_TITLE = "SearchActivity.TITLE_REPLY"
        const val EXTRA_RELEASE_DATE = "SearchActivity.RELEASE_DATE_REPLY"
        const val EXTRA_POSTER_PATH = "SearchActivity.POSTER_PATH_REPLY"
    }
}
