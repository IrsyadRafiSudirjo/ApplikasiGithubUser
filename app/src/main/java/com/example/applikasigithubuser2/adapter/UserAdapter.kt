package com.example.applikasigithubuser2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.applikasigithubuser2.R
import com.example.applikasigithubuser2.databinding.ItemRowUserBinding
import com.example.applikasigithubuser2.item.UserItems


class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val mData = ArrayList<UserItems>()
    fun setData(items: ArrayList<UserItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowUserBinding.bind(itemView)
        fun bind(userItems: UserItems) {
            Glide.with(itemView.context)
                .load(userItems.avatar)
                .into(binding.avatarImage)
            binding.tvUsername.text = userItems.name
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserViewHolder {
        val mView =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        return UserViewHolder(mView)
    }

    override fun onBindViewHolder(userViewHolder: UserViewHolder, position: Int) {
        userViewHolder.bind(mData[position])
        userViewHolder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(mData[userViewHolder.adapterPosition]) }
    }

    override fun getItemCount(): Int = mData.size

    interface OnItemClickCallback {
        fun onItemClicked(data: UserItems)
    }
}