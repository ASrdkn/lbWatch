package com.example.lbwatch.main_activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lbwatch.R
import com.example.lbwatch.model.Movie
import com.example.lbwatch.model.MovieDB
import com.example.lbwatch.ui.AddActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), MainView, MainAdapterListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainAdapter
    private lateinit var imageEmpty: LinearLayout
    private lateinit var presenter: MainPresenter

    private val selectedMovies = mutableSetOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageEmpty = findViewById(R.id.no_movies_layout)
        val deleteBtn = findViewById<ImageView>(R.id.img_delete)

        recyclerView = findViewById(R.id.movies_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val addBtn = findViewById<FloatingActionButton>(R.id.fab)
        addBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivityForResult(intent, ADD_VIEW_ACTIVITY_REQUEST_CODE)
        }

        presenter = MainPresenter(this, MovieDB.getDb(this))

        loadMovies()

        deleteBtn.setOnClickListener {
            presenter.deleteMovies(selectedMovies.toList())
        }
    }

    private fun loadMovies() {
        presenter.loadMovies()  // Делегируем загрузку фильмов презентеру
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_VIEW_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                showToast("Фильм успешно добавлен")
                loadMovies()
            } else {
                showToast("Нет добавленных фильмов")
            }
        }
    }

    companion object {
        const val ADD_VIEW_ACTIVITY_REQUEST_CODE = 1
    }

    override fun onMovieSelected(movie: Movie, isSelected: Boolean) {
        if (isSelected) {
            selectedMovies.add(movie)
        } else {
            selectedMovies.remove(movie)
        }
    }

    // Реализация методов MainView
    override fun showMovies(movies: List<Movie>) {
        imageEmpty.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
        adapter = MainAdapter(movies, this@MainActivity, this)
        recyclerView.adapter = adapter
    }

    override fun showEmptyState() {
        recyclerView.visibility = View.INVISIBLE
        imageEmpty.visibility = View.VISIBLE
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}