package com.example.applikasigithubuser2

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.applikasigithubuser2.adapter.SectionsPagerAdapter
import com.example.applikasigithubuser2.database.DatabaseContract
import com.example.applikasigithubuser2.database.FavoriteHelper
import com.example.applikasigithubuser2.databinding.ActivityDetailUserFavoriteBinding
import com.example.applikasigithubuser2.item.FavoriteItems
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserFavoriteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityDetailUserFavoriteBinding
    private lateinit var favoriteHelper: FavoriteHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        val user = intent.getParcelableExtra<FavoriteItems>(EXTRA_USER) as FavoriteItems

        showLoading(true)
        mainViewModel.setDetailUser(user.nameFav.toString())
        mainViewModel.getDetailUser().observe(this, { user ->
            if (user != null) {
                showLoading(false)
                user.groupBy {
                    val username = it.nameD
                    val avatar = it.avatarD
                    val realname = it.realname
                    val location = it.locaText
                    val company = it.companyText
                    val repository = it.repoText
                    val following = it.followText
                    val followers = it.followrText
                    bindingView(
                        username,
                        avatar,
                        realname,
                        location,
                        company,
                        repository,
                        following,
                        followers
                    )
                }
            }
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = user.nameFav.toString()
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f


        var statusFavorite = favoriteHelper.checkUser(user.nameFav.toString())

        if (statusFavorite) (setStatusFavorite(true))
        else {
            setStatusFavorite(false)
        }

        binding.favoritebutton.setOnClickListener {

            if (!statusFavorite) {
                val username = binding.tvUsernameDetails.text.toString()
                val realname = binding.tvRealnameDetails.text.toString()
                val following = binding.tvFollowing.text.toString()
                val follower = binding.tvFollowers.text.toString()
                val location = binding.tvLocation.text.toString()
                val repo = binding.tvRepository.text.toString()
                val company = binding.tvCompany.text.toString()
                val avatarImg = user.avatarFav

                val values = ContentValues()
                values.put(DatabaseContract.NoteColumns.nameFav, username)
                values.put(DatabaseContract.NoteColumns.realNameFav, realname)
                values.put(DatabaseContract.NoteColumns.avatarFav, avatarImg)
                values.put(DatabaseContract.NoteColumns.followTextFav, following)
                values.put(DatabaseContract.NoteColumns.followrTextFav, follower)
                values.put(DatabaseContract.NoteColumns.locaTextFav, location)
                values.put(DatabaseContract.NoteColumns.repoTextFav, repo)
                values.put(DatabaseContract.NoteColumns.companyTextFav, company)
                val result = favoriteHelper.insert(values)

                statusFavorite = true
                setStatusFavorite(statusFavorite)
                if (result > 0) {
                    setResult(101, intent)
                }
                Toast.makeText(this, R.string.favorite_user, Toast.LENGTH_SHORT).show()
            } else {
                val result = favoriteHelper.deleteFavorite(user.nameFav.toString())
                setResult(301, intent)
                statusFavorite = false
                setStatusFavorite(statusFavorite)
                Toast.makeText(this, R.string.unfavorite_user, Toast.LENGTH_SHORT).show()
            }
        }

        onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
                val move = Intent(this@DetailUserFavoriteActivity, FavoriteActivity::class.java)
                startActivity(move)
            }
        })
    }


    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) binding.favoritebutton.setBackgroundResource(R.drawable.ic_baseline_star_filled)
        else binding.favoritebutton.setBackgroundResource(R.drawable.ic_baseline_star_notfilled)
    }


    fun bindingView(
        bindusername: String?,
        bindavatar: String?,
        bindrealname: String?,
        bindLocation: String?,
        bindCompany: String?,
        bindRepository: String?,
        bindFollowing: String?,
        bindFollower: String?
    ) {
        val itemView = binding.avatarImageDetails

        val followText = bindFollowing
        val followrText = bindFollower
        val repoText = bindRepository
        val companyText = bindCompany
        val locaText = bindLocation
        showLoading(false)

        binding.tvUsernameDetails.text = bindusername
        Glide.with(itemView.context)
            .load(bindavatar)
            .into(binding.avatarImageDetails)
        binding.tvRealnameDetails.text = bindrealname
        binding.tvFollowers.text = followrText
        binding.tvFollowing.text = followText
        binding.tvRepository.text = repoText
        binding.tvLocation.text = locaText
        binding.tvCompany.text = companyText

        binding.tvCompanyText.text = resources.getString(R.string.company_text)
        binding.tvFollowingText.text = resources.getString(R.string.following_text)
        binding.tvFollowersText.text = resources.getString(R.string.followers_text)
        binding.tvRepositoryText.text = resources.getString(R.string.repository_text)
        binding.tvLocationText.text = resources.getString(R.string.location_text)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.progressBarDetails.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.progressBarDetails.visibility = View.GONE
        }
    }
}