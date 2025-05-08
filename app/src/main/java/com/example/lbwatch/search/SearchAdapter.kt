package com.example.lbwatch.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lbwatch.R
import com.example.lbwatch.model.Item
import com.squareup.picasso.Picasso

class SearchAdapter(
    private var list: List<Item>,
    private val listener: SearchAdapterListener, // Используем интерфейс для делегирования
    private val context: Context
) : RecyclerView.Adapter<SearchAdapter.SearchHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie_detal, parent, false)
        val viewHolder = SearchHolder(view)

        // Делегируем событие клика на элемент в SearchActivity через интерфейс
        view.setOnClickListener { v -> listener.onItemClick(v, viewHolder.adapterPosition) }

        return viewHolder
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val item = list[position]

        holder.titleTextView.text = item.title
        holder.releaseDateTextView.text = item.releaseDate
        holder.overviewTextView.text = item.overview

        // Если у элемента есть изображение, загружаем его с помощью Picasso
        if (item.posterPath != "N/A") {
            Picasso.get().load(item.posterPath).into(holder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getItemAtPosition(pos: Int): Item {
        return list[pos]
    }

    inner class SearchHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTextView: TextView = view.findViewById(R.id.title_textview)
        var overviewTextView: TextView = view.findViewById(R.id.overview_overview)
        var releaseDateTextView: TextView = view.findViewById(R.id.release_date_textview)
        var imageView: ImageView = view.findViewById(R.id.movie_imageview)

        init {
            // Передаем клик обратно через интерфейс
            view.setOnClickListener { v: View ->
                listener.onItemClick(v, adapterPosition)
            }
        }
    }
}

