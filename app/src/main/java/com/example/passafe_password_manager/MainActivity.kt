package com.example.passafe_password_manager

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.passafe_password_manager.data.local.passData
import com.example.passafe_password_manager.screens.home_screen
import com.example.passafe_password_manager.ui.theme.Passafe_password_managerTheme
import com.example.passafe_password_manager.viewmodel.passViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var allPass: List<passData>
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        allPass = emptyList()
        val viewModel: passViewModel by viewModels()
        setContent {
            Passafe_password_managerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   home_screen(allPass, viewModel, this)
                }
            }
        }
    }
}