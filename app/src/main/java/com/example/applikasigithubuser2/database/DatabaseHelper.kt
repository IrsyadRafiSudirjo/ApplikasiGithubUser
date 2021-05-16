package com.example.applikasigithubuser2.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.applikasigithubuser2.database.DatabaseContract.NoteColumns
import com.example.applikasigithubuser2.database.DatabaseContract.NoteColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "dbFavorite"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " (${NoteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${NoteColumns.avatarFav} TEXT NOT NULL," +
                " ${NoteColumns.nameFav} TEXT NOT NULL," +
                " ${NoteColumns.followTextFav} TEXT NOT NULL," +
                " ${NoteColumns.followrTextFav} TEXT NOT NULL," +
                " ${NoteColumns.repoTextFav} TEXT NOT NULL," +
                " ${NoteColumns.locaTextFav} TEXT NOT NULL," +
                " ${NoteColumns.companyTextFav} TEXT NOT NULL," +
                " ${NoteColumns.realNameFav} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}