package com.yeminnaing.navigationwithcustomnavigatorwithviewmodel.presentation.navigation

import androidx.navigation.ActivityNavigator
import androidx.navigation.NavOptionsBuilder

sealed interface NavigationAction {
    data class Navigate(
        val destinations:Destination,
        val navOptions:NavOptionsBuilder.()->Unit ={}
        ):NavigationAction

    data object NavigateUp:NavigationAction
}