package com.example.bangkit_2024_fp_bfaa.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangkit_2024_fp_bfaa.databinding.ActivityFavoriteUserBinding
import com.example.bangkit_2024_fp_bfaa.ui.viewmodelfactory.FavoriteViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var viewModel: FavoriteUserViewModel
    private lateinit var adapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = FavoriteViewModelFactory.getInstance(this@FavoriteUserActivity.application)
        viewModel = ViewModelProvider(this@FavoriteUserActivity, factory).get(FavoriteUserViewModel::class.java)

        back()

        adapter = FavoriteUserAdapter()

        binding.rvFavUser.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
        binding.rvFavUser.setHasFixedSize(false)
        binding.rvFavUser.adapter = this.adapter

        viewModel.getAllFavUser().observe(this) { favUser ->
            if (favUser != null) {
                adapter.setListFavUser(favUser)
            }
        }
    }

    private fun back() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}