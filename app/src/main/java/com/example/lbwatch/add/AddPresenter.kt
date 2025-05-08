package com.example.lbwatch.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.lbwatch.model.Movie
import com.example.lbwatch.model.MovieDB
import com.example.lbwatch.search.SearchActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddPresenter(private val view: AddView, private val movieDb: MovieDB) {

    // Метод для поиска фильма
    fun searchMovie(query: String) {
        val intent = Intent(view as Context, SearchActivity::class.java)
        intent.putExtra(SearchActivity.SEARCH_QUERY, query)
        (view as Activity).startActivityForResult(intent, AddActivity.SEARCH_ACTIVITY_REQUEST_CODE)
    }

    // Метод для добавления фильма
    fun addMovie(title: String, releaseDate: String, posterPath: String) {
        if (title.isEmpty() || releaseDate.isEmpty()) {
            view.showError("Заполните все поля!")
            return
        }

        val validPosterPath = if (posterPath.isNotEmpty()) posterPath else null // Если пусто, передаем null

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val movie = Movie(
                    null,
                    title,
                    releaseDate,
                    validPosterPath
                )
                movieDb.getDao().insert(movie)  // Вставка фильма в базу данных
                withContext(Dispatchers.Main) {
                    view.showMovieAdded()  // Уведомление о том, что фильм добавлен
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view.showError("Ошибка при добавлении фильма: ${e.message}")
                }
            }
        }
    }

    // Обработка результатов поиска
    fun handleSearchResult(data: Intent?) {
        val title = data?.getStringExtra(SearchActivity.EXTRA_TITLE) ?: ""
        val releaseDate = data?.getStringExtra(SearchActivity.EXTRA_RELEASE_DATE) ?: ""
        val posterPath = data?.getStringExtra(SearchActivity.EXTRA_POSTER_PATH) ?: ""
        view.setMovieDetails(title, releaseDate, posterPath)
    }
}
