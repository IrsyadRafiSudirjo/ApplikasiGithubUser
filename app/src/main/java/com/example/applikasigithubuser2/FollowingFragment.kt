package com.example.applikasigithubuser2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applikasigithubuser2.adapter.FollowingAdapter
import com.example.applikasigithubuser2.databinding.FragmentFollowingBinding


class FollowingFragment : Fragment() {
    private lateinit var fbinding: FragmentFollowingBinding
    private lateinit var adapter: FollowingAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFollowingBinding.bind(view)
        fbinding = binding

        adapter = FollowingAdapter()
        adapter.notifyDataSetChanged()

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        fbinding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        fbinding.rvFollowing.adapter = adapter

        val query = arguments?.getString(FollowingFragment.USER_NAME)
        if (query != null) {
            showLoading(true)
            mainViewModel.setFollowing(query)
        }

        mainViewModel.getFollowing().observe(viewLifecycleOwner, { user ->
            if (user != null) {
                showLoading(false)
                adapter.setData(user)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            fbinding.progressBar.visibility = View.VISIBLE
        } else {
            fbinding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val USER_NAME = "user_name"
        fun newInstance(username: String): FollowingFragment {
            val fragment = FollowingFragment()
            val mBundle = Bundle()
            mBundle.putString(USER_NAME, username)
            fragment.arguments = mBundle
            return fragment
        }
    }
}