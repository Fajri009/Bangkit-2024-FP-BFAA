package com.example.bangkit_2024_fp_bfaa.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangkit_2024_fp_bfaa.data.response.UserResponse
import com.example.bangkit_2024_fp_bfaa.databinding.ActivityHomeBinding
import com.example.bangkit_2024_fp_bfaa.ui.detailuser.DetailActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showRecyclerList()

        viewModel.listUser.observe(this) {
            setUserData(it)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        search(binding)
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
        moveWithParcelableIntent.putExtra(DetailActivity.EXTRA_USER, data)
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