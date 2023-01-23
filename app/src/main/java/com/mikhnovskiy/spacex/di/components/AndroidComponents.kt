package com.mikhnovskiy.spacex.di.components

import com.mikhnovskiy.spacex.di.annotations.AndroidLayer
import com.mikhnovskiy.spacex.di.viewmodel.ViewModelModule
import com.mikhnovskiy.spacex.presentation.screens.MainActivity
import com.mikhnovskiy.spacex.presentation.screens.listing.ListingFragment
import dagger.Component
import dagger.Subcomponent

@AndroidLayer
@Component(dependencies = [UseCaseComponent::class, UtilComponent::class])
interface AndroidComponents {
    val activityComponent: ActivityComponent
    val fragmentComponent: FragmentComponent
}

@Subcomponent(modules = [ViewModelModule::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}

@Subcomponent(modules = [ViewModelModule::class])
interface FragmentComponent {
    fun inject(fragment: ListingFragment)
}