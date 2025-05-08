package com.example.lbwatch.main_activity

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import com.example.lbwatch.model.Movie
import com.example.lbwatch.model.MovieDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainPresenter(private val view: MainView, private val movieDb: MovieDB) {

    fun loadMovies() {
        movieDb.getDao().getAll().asLiveData().observe(view as LifecycleOwner) { movies: List<Movie> -> // Указываем тип List<Movie>
            if (movies.isNotEmpty()) {
                view.showMovies(movies)
            } else {
                view.showEmptyState()
            }
        }
    }

    fun deleteMovies(movies: List<Movie>) {
        CoroutineScope(Dispatchers.IO).launch {
            for (movie in movies) {
                movieDb.getDao().delete(movie) // Удаление фильма через DAO
            }
            // После удаления фильмов, возвращаем соответствующее сообщение в Activity
            launch(Dispatchers.Main) {
                val message = if (movies.size == 1) {
                    "Фильм успешно удален"
                } else {
                    "Фильмы успешно удалены"
                }
                view.showToast(message)
                loadMovies()
            }
        }
    }
}
