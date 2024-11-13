package com.yeminnaing.navigationwithcustomnavigatorwithviewmodel.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeminnaing.navigationwithcustomnavigatorwithviewmodel.presentation.navigation.DefaultNavigator
import com.yeminnaing.navigationwithcustomnavigatorwithviewmodel.presentation.navigation.Destination
import com.yeminnaing.navigationwithcustomnavigatorwithviewmodel.presentation.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val navigator: Navigator
):ViewModel() {
    fun login() {
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.HomeGraph,
                navOptions = {
                    popUpTo(Destination.AuthGraph) {
                        inclusive = true
                    }
                }
            )
        }
    }
}