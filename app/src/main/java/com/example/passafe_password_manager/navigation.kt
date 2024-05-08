//package com.example.passafe_password_manager
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.example.passafe_password_manager.data.local.passData
//import com.example.passafe_password_manager.screens.add_New
//import com.example.passafe_password_manager.screens.home_screen
//import com.example.passafe_password_manager.screens.pass_show
//import com.example.passafe_password_manager.viewmodel.passViewModel
//
//@Composable
//fun nav(allPass: List<passData>, viewModel: passViewModel, mainActivity: MainActivity)
//{
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = "home"){
//        composable("home"){
//            home_screen(allPass = allPass, viewModel = viewModel, mainActivity = mainActivity, navController)
//        }
//        composable("add/update"){
//            add_New(viewModel = viewModel)
//        }
//        composable("show"){
//            pass_show(allPass = allPass, itemcount = allPass.size, viewModel = viewModel, navController)
//        }
//    }
//}