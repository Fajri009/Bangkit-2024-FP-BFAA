package com.example.bangkit_2024_fp_bfaa.ui.setting

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.*
import androidx.lifecycle.ViewModelProvider
import com.example.bangkit_2024_fp_bfaa.databinding.ActivitySettingBinding
import com.example.bangkit_2024_fp_bfaa.ui.ViewModelFactory

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var viewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.datastore)
        viewModel = ViewModelProvider(this@SettingActivity, ViewModelFactory(pref)).get(SettingViewModel::class.java)

        switchTheme(viewModel)
        backButton()
    }

    private fun backButton() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun switchTheme(viewModel: SettingViewModel) {
        val switchTheme = binding.switchTheme

        this.viewModel.getThemeSettings().observe(this@SettingActivity) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            this.viewModel.saveThemeSetting(isChecked)
        }
    }
}