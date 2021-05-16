package com.example.applikasigithubuser2.helper

import android.database.Cursor
import com.example.applikasigithubuser2.database.DatabaseContract.NoteColumns
import com.example.applikasigithubuser2.item.FavoriteItems

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<FavoriteItems> {
        val favList = ArrayList<FavoriteItems>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(NoteColumns._ID))
                val nameFav = getString(getColumnIndexOrThrow(NoteColumns.nameFav))
                val avatarFav = getString(getColumnIndexOrThrow(NoteColumns.avatarFav))
                favList.add(FavoriteItems(id, nameFav, avatarFav))
            }
        }
        return favList
    }

}