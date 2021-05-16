package com.example.applikasigithubuser2.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.applikasigithubuser2.database.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.example.applikasigithubuser2.database.DatabaseContract.NoteColumns.Companion._ID
import com.example.applikasigithubuser2.database.DatabaseContract.NoteColumns.Companion.nameFav
import java.sql.SQLException

class FavoriteHelper(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: FavoriteHelper? = null

        fun getInstance(context: Context): FavoriteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(DATABASE_TABLE, null, "$_ID = ?", arrayOf(id), null, null, null, null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }

    fun checkUser(username: String): Boolean {
        val cursor = database.query(
            DATABASE_TABLE,
            arrayOf(nameFav),
            "$nameFav = ?",
            arrayOf(username),
            null,
            null,
            null
        ) //The sort order
        val cursorCount = cursor.count
        cursor.close()
        if (cursorCount > 0)
            return true
        return false
    }

    fun deleteFavorite(username: String) {
        database.delete(
            TABLE_NAME, "$nameFav = ?",
            arrayOf(username)
        )
    }
}