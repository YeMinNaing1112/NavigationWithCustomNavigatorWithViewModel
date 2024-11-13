package com.yeminnaing.navigationwithcustomnavigatorwithviewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.yeminnaing.navigationwithcustomnavigatorwithviewmodel.presentation.DetailViewModel
import com.yeminnaing.navigationwithcustomnavigatorwithviewmodel.presentation.HomeViewModel
import com.yeminnaing.navigationwithcustomnavigatorwithviewmodel.presentation.LoginViewModel
import com.yeminnaing.navigationwithcustomnavigatorwithviewmodel.presentation.navigation.Destination
import com.yeminnaing.navigationwithcustomnavigatorwithviewmodel.presentation.navigation.NavigationAction
import com.yeminnaing.navigationwithcustomnavigatorwithviewmodel.presentation.navigation.Navigator
import com.yeminnaing.navigationwithcustomnavigatorwithviewmodel.presentation.navigation.ObserveAsEvents
import com.yeminnaing.navigationwithcustomnavigatorwithviewmodel.ui.theme.NavigationWithCustomNavigatorWithViewModelTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Navigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationWithCustomNavigatorWithViewModelTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()


                    ObserveAsEvents(flow = navigator.navigationActions) { action ->
                        when (action) {
                            is NavigationAction.Navigate -> navController.navigate(
                                action.destinations
                            ) {
                                action.navOptions(this)
                            }

                            NavigationAction.NavigateUp -> navController.navigateUp()
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = navigator.startDestination,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        navigation<Destination.AuthGraph>(
                            startDestination = Destination.LoginScreen
                        ) {
                            composable<Destination.LoginScreen> {
                                val viewModel:LoginViewModel = hiltViewModel()
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Button(onClick = { viewModel.login() }) {
                                        Text(text = "Login")
                                    }
                                }
                            }
                        }
                        navigation<Destination.HomeGraph>(
                            startDestination = Destination.HomeScreen
                        ) {
                            composable<Destination.HomeScreen> {
                                val viewModel:HomeViewModel =  hiltViewModel()
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Button(onClick = {
                                        viewModel.navigateToDetail(UUID.randomUUID().toString())
                                    }) {
                                        Text(text = "Go to detail")
                                    }
                                }
                            }
                            composable<Destination.DetailScreen> {
                                val viewModel:DetailViewModel = hiltViewModel()
                                val args = it.toRoute<Destination.DetailScreen>()
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "ID: ${args.id}")
                                    Button(onClick = {viewModel.goBack()}) {
                                        Text(text = "Go back")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

