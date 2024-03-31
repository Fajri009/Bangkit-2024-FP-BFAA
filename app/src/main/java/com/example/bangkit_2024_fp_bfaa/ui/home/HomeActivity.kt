package com.example.bangkit_2024_fp_bfaa.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangkit_2024_fp_bfaa.R
import com.example.bangkit_2024_fp_bfaa.data.response.UserResponse
import com.example.bangkit_2024_fp_bfaa.databinding.ActivityHomeBinding
import com.example.bangkit_2024_fp_bfaa.ui.viewmodelfactory.ThemeViewModelFactory
import com.example.bangkit_2024_fp_bfaa.ui.detailuser.DetailActivity
import com.example.bangkit_2024_fp_bfaa.ui.favorite.FavoriteUserActivity
import com.example.bangkit_2024_fp_bfaa.ui.setting.*

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.datastore)
        viewModel = ViewModelProvider(this@HomeActivity, ThemeViewModelFactory(pref)).get(HomeViewModel::class.java)

        switchTheme(viewModel)

        topBar()

        search(binding)
        showRecyclerList()

        viewModel.listUser.observe(this) {
            setUserData(it)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun switchTheme(viewModel: HomeViewModel) {
        viewModel.getThemeSetting().observe(this@HomeActivity) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun topBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    val settingIntent = Intent(this@HomeActivity, SettingActivity::class.java)
                    startActivity(settingIntent)
                    true
                }
                R.id.menu2 -> {
                    val favIntent = Intent(this@HomeActivity, FavoriteUserActivity::class.java)
                    startActivity(favIntent)
                    true
                }
                else -> false
            }
        }
    }

    private fun showRecyclerList() {
        val layoutInflater = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutInflater
    }

    private fun search(binding: ActivityHomeBinding) {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    viewModel.findUsers(searchView.text.toString())
                    false
                }
        }
    }

    private fun setUserData(user: List<UserResponse>) {
        val adapter = UserAdapter(user)
        adapter.submitList(user)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: UserResponse) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(data: UserResponse) {
        val moveWithParcelableIntent = Intent(this@HomeActivity, DetailActivity::class.java)
        moveWithParcelableIntent.putExtra(DetailActivity.EXTRA_USER, data.login)
        startActivity(moveWithParcelableIntent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }
}