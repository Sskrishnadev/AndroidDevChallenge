package com.demo.composemvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.demo.composemvvm.navigation.MyNavGraph
import com.demo.composemvvm.ui.theme.ComposeMVVMTheme
import com.demo.composemvvm.viewmodel.FlickrViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: FlickrViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMVVMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    MyNavGraph(navController,viewModel)
                }
            }
        }
    }
}