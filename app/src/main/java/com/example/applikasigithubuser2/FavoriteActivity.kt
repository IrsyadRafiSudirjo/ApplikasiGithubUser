package com.example.applikasigithubuser2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applikasigithubuser2.adapter.FavoriteAdapter
import com.example.applikasigithubuser2.database.DatabaseContract.NoteColumns.Companion.CONTENT_URI
import com.example.applikasigithubuser2.databinding.ActivityFavoriteBinding
import com.example.applikasigithubuser2.helper.MappingHelper
import com.example.applikasigithubuser2.item.FavoriteItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteAdapter

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite"
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(true)
        adapter = FavoriteAdapter(this)
        binding.rvFavorite.adapter = adapter


        loadNotesAsync()



        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteItems) {
                finish()
                val move = Intent(this@FavoriteActivity, DetailUserFavoriteActivity::class.java)
                move.putExtra(DetailUserActivity.EXTRA_USER, data)
                startActivity(move)
            }
        })
    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredFav = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val faves = deferredFav.await()
            if (faves.size > 0) {
                adapter.listFavorite = faves
            } else {
                adapter.listFavorite = ArrayList()
            }
        }
    }
}