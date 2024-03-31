package com.example.bangkit_2024_fp_bfaa.ui.detailuser

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.bangkit_2024_fp_bfaa.R
import com.example.bangkit_2024_fp_bfaa.data.database.FavoriteUser
import com.example.bangkit_2024_fp_bfaa.data.response.DetailUserResponse
import com.example.bangkit_2024_fp_bfaa.data.response.UserResponse
import com.example.bangkit_2024_fp_bfaa.databinding.ActivityDetailBinding
import com.example.bangkit_2024_fp_bfaa.ui.favorite.FavoriteUserViewModel
import com.example.bangkit_2024_fp_bfaa.ui.viewmodelfactory.FavoriteViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    private var buttonState: Boolean = false
    private lateinit var favoriteUser: FavoriteUser
    private var detailUser = DetailUserResponse()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val application = FavoriteViewModelFactory.getInstance(this@DetailActivity.application)
        viewModel = ViewModelProvider(this@DetailActivity, application).get(DetailViewModel::class.java)

        val user = intent.extras!!.getString(EXTRA_USER)

        back()

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.getUser(user!!)

        viewModel.listDetail.observe(this) {
            setShowUser(it)
            setFavUser(it)
        }

        setViewPager(user)
    }

    private fun back() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setShowUser(listUser: DetailUserResponse) {
        Glide.with(this@DetailActivity)
            .load(listUser.avatarUrl)
            .circleCrop()
            .into(binding.ivAvatar)
        binding.tvNama.text = listUser.name
        binding.tvUsername.text = listUser.login
        binding.tvTwitter.text =
            if (listUser.twitterUsername.toString() == "null") {
                "-"
            } else {
                listUser.twitterUsername.toString()
            }
        binding.tvLocation.text = listUser.location
        binding.tvFollower.text = "${listUser.followers} followers"
        binding.tvFollowing.text = "${listUser.following} following"
    }

    private fun setViewPager(curr_user: String) {
        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = curr_user
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, positon ->
            tab.text = resources.getString(TAB_TITLES[positon])
        }.attach()
    }

    private fun insertToDatabase(detailUserList: DetailUserResponse) {
        favoriteUser.let { favUser ->
            favUser.id = detailUserList.id!!
            favUser.avatarUrl = detailUserList.avatarUrl
            favUser.login = detailUserList.login
            favUser.htmlUrl = detailUserList.htmlUrl
            viewModel.insertFavUser(favUser)
            Toast.makeText(this@DetailActivity, "User ini telah dimasukkan ke dalam favorite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFavUser(detailUserResponse: DetailUserResponse) {
        detailUser = detailUserResponse
        setShowUser(detailUserResponse)

        favoriteUser= FavoriteUser(detailUser.id!!, detailUser.avatarUrl, detailUser.login, detailUser.htmlUrl)

        viewModel.getAllFavorites().observe(this) { favoriteUser ->
            if (favoriteUser  != null) {
                for (data in favoriteUser) {
                    if (detailUserResponse.id!! == data.id)  {
                        buttonState = true
                        binding.fabFav.setImageResource(R.drawable.ic_favorite)
                    }
                }
            }
        }

        binding.fabFav.setOnClickListener {
            if (!buttonState) {
                buttonState = true
                binding.fabFav.setImageResource(R.drawable.ic_favorite)
                insertToDatabase(detailUserResponse)

            } else {
                buttonState = false
                binding.fabFav.setImageResource(R.drawable.ic_unfavorite)
                viewModel.deleteFavUser(detailUserResponse.id!!)
                Toast.makeText(this@DetailActivity, "User ini telah dihapus dari favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_title_1,
            R.string.tab_title_2
        )
    }
}