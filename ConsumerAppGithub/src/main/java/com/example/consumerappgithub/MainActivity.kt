package com.example.consumerappgithub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applikasigithubuser2.helper.MappingHelper
import com.example.consumerappgithub.adapter.FavoriteAdapter
import com.example.consumerappgithub.database.DatabaseContract.NoteColumns.Companion.CONTENT_URI
import com.example.consumerappgithub.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FavoriteAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite"
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(true)
        adapter = FavoriteAdapter(this)
        binding.rvFavorite.adapter = adapter

        supportActionBar?.title = "Consumer Favorites"

        loadNotesAsync()

    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredFaves = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val faves = deferredFaves.await()
            if (faves.size > 0) {
                adapter.listFavorite = faves
            } else {
                adapter.listFavorite = ArrayList()
            }
        }
    }
}