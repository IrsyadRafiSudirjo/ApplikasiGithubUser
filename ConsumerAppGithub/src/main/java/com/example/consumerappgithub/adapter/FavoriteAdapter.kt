package com.example.consumerappgithub.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.consumerappgithub.R
import com.example.consumerappgithub.databinding.ItemRowUserBinding
import com.example.consumerappgithub.item.FavoriteItems

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


}