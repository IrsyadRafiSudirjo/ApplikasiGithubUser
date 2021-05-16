package com.example.applikasigithubuser2.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {


    const val AUTHORITY = "com.example.applikasigithubuser2"
    const val SCHEME = "content"

    class NoteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val _ID = "_id"
            const val avatarFav = "avatar"
            const val nameFav = "username"
            const val realNameFav = "realname"
            const val followTextFav = "following"
            const val followrTextFav = "follower"
            const val repoTextFav = "repository"
            const val companyTextFav = "company"
            const val locaTextFav = "location"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}