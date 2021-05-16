package com.example.applikasigithubuser2

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.applikasigithubuser2.item.DetailUserItems
import com.example.applikasigithubuser2.item.FollowerItems
import com.example.applikasigithubuser2.item.FollowingItems
import com.example.applikasigithubuser2.item.UserItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel : ViewModel() {
    private val listUser = MutableLiveData<ArrayList<UserItems>>()
    private val config = BuildConfig.GITHUB_TOKEN


    fun setUsers(query: String) {
        val listUserItems = ArrayList<UserItems>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$query"
        client.addHeader("Authorization", "token $config")
        client.addHeader("User-Agent", " Request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    Log.d(TAG, result)
                    val responseObjects = JSONObject(result)
                    val items = responseObjects.getJSONArray("items")

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val user = UserItems()
                        user.avatar = avatar
                        user.name = username
                        listUserItems.add(user)
                    }
                    listUser.postValue(listUserItems)

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getUsers(): LiveData<ArrayList<UserItems>> {
        return listUser
    }

    private val listFollower = MutableLiveData<ArrayList<FollowerItems>>()


    fun setFollowers(query: String) {
        val listFollowerItems = ArrayList<FollowerItems>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$query/followers"
        client.addHeader("Authorization", "token $config")
        client.addHeader("User-Agent", " Request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    Log.d(TAG, result)
                    val items = JSONArray(result)
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val user = FollowerItems()
                        user.avatarfwr = avatar
                        user.namefwr = username
                        listFollowerItems.add(user)
                    }
                    listFollower.postValue(listFollowerItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }


    fun getFollowers(): LiveData<ArrayList<FollowerItems>> {
        return listFollower
    }


    private val listFollowing = MutableLiveData<ArrayList<FollowingItems>>()

    fun setFollowing(query: String) {
        val listFollowingItems = ArrayList<FollowingItems>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$query/following"
        client.addHeader("Authorization", "token $config")
        client.addHeader("User-Agent", " Request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    Log.d(TAG, result)
                    val items = JSONArray(result)
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val user = FollowingItems()
                        user.avatarfwl = avatar
                        user.namefwl = username
                        listFollowingItems.add(user)
                    }
                    listFollowing.postValue(listFollowingItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getFollowing(): LiveData<ArrayList<FollowingItems>> {
        return listFollowing
    }


    private val listUserDetail = MutableLiveData<ArrayList<DetailUserItems>>()

    fun setDetailUser(query: String) {
        val listUserDetailItems = ArrayList<DetailUserItems>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$query"
        client.addHeader("Authorization", "token $config")
        client.addHeader("User-Agent", " Request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(ContentValues.TAG, result)
                try {
                    val responseObject = JSONObject(result)
                    val username = responseObject.getString("login")
                    val avatar = responseObject.getString("avatar_url")
                    val realname = responseObject.getString("name")
                    val location = responseObject.getString("location")
                    val company = responseObject.getString("company")
                    val repository = responseObject.getString("public_repos")
                    val following = responseObject.getString("following")
                    val followers = responseObject.getString("followers")
                    val user = DetailUserItems()
                    user.avatarD = avatar
                    user.nameD = username
                    user.realname = realname
                    user.locaText = location
                    user.companyText = company
                    user.repoText = repository
                    user.followText = following
                    user.followrText = followers
                    listUserDetailItems.add(user)
                    listUserDetail.postValue(listUserDetailItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }


    fun getDetailUser(): LiveData<ArrayList<DetailUserItems>> {
        return listUserDetail
    }


}