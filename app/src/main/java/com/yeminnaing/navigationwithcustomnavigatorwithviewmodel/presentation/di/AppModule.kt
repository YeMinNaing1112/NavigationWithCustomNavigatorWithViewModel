package com.yeminnaing.navigationwithcustomnavigatorwithviewmodel.presentation.di

import com.yeminnaing.navigationwithcustomnavigatorwithviewmodel.presentation.navigation.DefaultNavigator
import com.yeminnaing.navigationwithcustomnavigatorwithviewmodel.presentation.navigation.Destination
import com.yeminnaing.navigationwithcustomnavigatorwithviewmodel.presentation.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNavigator():Navigator{
        return DefaultNavigator(startDestination = Destination.AuthGraph)
    }
}