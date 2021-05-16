package com.example.applikasigithubuser2.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.applikasigithubuser2.R
import com.example.applikasigithubuser2.databinding.ItemRowUserBinding
import com.example.applikasigithubuser2.item.FavoriteItems

class FavoriteAdapter(private val activity: Activity) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    var listFavorite = ArrayList<FavoriteItems>()
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listNotes)

            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listFavorite[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = this.listFavorite.size

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowUserBinding.bind(itemView)
        fun bind(note: FavoriteItems) {
            binding.tvUsername.text = note.nameFav
            Glide.with(itemView.context)
                .load(note.avatarFav)
                .into(binding.avatarImage)
        }
    }

    private lateinit var onItemClickCallback: FavoriteAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: FavoriteAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteItems)
    }
}