package com.example.bangkit_2024_fp_bfaa.ui.favorite

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.example.bangkit_2024_fp_bfaa.data.database.FavoriteUser
import com.example.bangkit_2024_fp_bfaa.data.response.UserResponse
import com.example.bangkit_2024_fp_bfaa.databinding.ItemListUserBinding
import com.example.bangkit_2024_fp_bfaa.ui.detailuser.DetailActivity

class FavoriteUserAdapter: RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {
    private val listUserFav = ArrayList<FavoriteUser>()

    fun setListFavUser(listUserFav: List<FavoriteUser>) {
        val diffCallback = FavoriteDiffCallBack(this.listUserFav, listUserFav)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUserFav.clear()
        this.listUserFav.addAll(listUserFav)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(val binding: ItemListUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(items: FavoriteUser){
            Glide.with(itemView.context)
                .load(items.avatarUrl)
                .circleCrop()
                .into(binding.ivAvatar)
            binding.tvNama.text = items.login

            binding.listUser.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, items.login)
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listUserFav.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUserFav[position])
    }
}