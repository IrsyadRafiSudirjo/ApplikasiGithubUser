package com.example.consumerappgithub.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class FavoriteItems(
    var id: Int = 0,
    var nameFav: String? = null,
    var avatarFav: String? = null,
    var realNameFav: String? = null,
    var followTextFav: String? = null,
    var followrTextFav: String? = null,
    var repoTextFav: String? = null,
    var companyTextFav: String? = null,
    var locaTextFav: String? = null,
) : Parcelable