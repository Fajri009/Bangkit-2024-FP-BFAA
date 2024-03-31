package com.example.bangkit_2024_fp_bfaa.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.bangkit_2024_fp_bfaa.ui.home.HomeViewModel
import com.example.bangkit_2024_fp_bfaa.ui.setting.SettingPreferences
import com.example.bangkit_2024_fp_bfaa.ui.setting.SettingViewModel

// Dikarenakan ViewModel terdapat constructor, maka dibuat kelas ini
class ThemeViewModelFactory(private val pref: SettingPreferences): NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown viewModel class: " + modelClass.name)
    }
}