package com.example.applikasigithubuser2.helper

import android.database.Cursor
import com.example.consumerappgithub.database.DatabaseContract
import com.example.consumerappgithub.item.FavoriteItems

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<FavoriteItems> {
        val favList = ArrayList<FavoriteItems>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
                val nameFav = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.nameFav))
                val avatarFav =
                    getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.avatarFav))
                favList.add(FavoriteItems(id, nameFav, avatarFav))
            }
        }
        return favList
    }

}