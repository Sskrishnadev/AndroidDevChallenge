package com.demo.composemvvm.navigation

import FlickrScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.demo.composemvvm.model.FlickrItem
import com.demo.composemvvm.view.DetailScreen
import com.demo.composemvvm.viewmodel.FlickrViewModel
import com.google.gson.Gson

@Composable
fun MyNavGraph(navController: NavHostController, viewModel: FlickrViewModel) {
    NavHost(
        navController = navController, startDestination = Screens.Home.route
    ) {
        composable(route = Screens.Home.route) {
            FlickrScreen(navController, viewModel)
        }

        composable(
            route = Screens.Detail.route.plus("?flickerSelectedItem={flickerSelectedItem}"),
            arguments = listOf(
                navArgument(
                    name = "flickerSelectedItem"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                },
            )
        ) {
            val flickerSelectedItemJsonString = it.arguments?.getString("flickerSelectedItem")
            val flickerSelectedItem =
                Gson().fromJson(flickerSelectedItemJsonString, FlickrItem::class.java)
            DetailScreen(navController = navController, flickerSelectedItem)
        }
    }
}