package com.example.lbwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lbwatch.model.MovieDB

class AddActivity : AppCompatActivity() {
    lateinit var titleEditText: EditText
    lateinit var releaseDateEditText: EditText
    lateinit var movieImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        // Включаем режим отображения контента от края до края
        enableEdgeToEdge()

        // Устанавливаем слушатель для встраивания системы в интерфейс
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Получаем доступ к базе данных
        val dataBase = MovieDB.getDb(this)

        // Инициализация элементов UI
        titleEditText = findViewById(R.id.movie_title)
        val searchBtn = findViewById<ImageButton>(R.id.search_btn)
        val addBtn = findViewById<Button>(R.id.add_movie)
        releaseDateEditText = findViewById(R.id.movie_release_date)
        movieImageView = findViewById(R.id.movie_imageview)

    }
}
