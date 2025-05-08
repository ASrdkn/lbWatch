package com.example.lbwatch.main_activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lbwatch.R
import com.example.lbwatch.model.Movie
import com.squareup.picasso.Picasso

class MainAdapter(
    private var list: List<Movie>,
    private val context: Context,
    private val listener: MainAdapterListener
) : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie_main, parent, false)
        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val movie = list[position]

        holder.titleTextView.text = movie.title
        holder.releaseDateTextView.text = movie.year

        if (movie.posterUrl.isNullOrEmpty()) {
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.no_image))
        } else {
            Picasso.get().load(movie.posterUrl).into(holder.imageView)
        }
    }

    override fun getItemCount(): Int = list.size

    inner class MainHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.title_textview)
        val releaseDateTextView: TextView = view.findViewById(R.id.release_date_textview)
        val imageView: ImageView = view.findViewById(R.id.movie_imageview)
        val checkBox: CheckBox = view.findViewById(R.id.checkbox)

        init {
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val movie = list[position]
                    // Передаем информацию о выбранном фильме в listener
                    listener.onMovieSelected(movie, isChecked)
                }
            }
        }
    }
}