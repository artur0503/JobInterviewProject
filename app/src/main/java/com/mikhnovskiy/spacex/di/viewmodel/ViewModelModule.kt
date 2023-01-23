package com.mikhnovskiy.spacex.di.viewmodel

import androidx.lifecycle.ViewModel
import com.mikhnovskiy.spacex.di.annotations.ViewModelKey
import com.mikhnovskiy.spacex.presentation.screens.listing.ListingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ListingViewModel::class)
    abstract fun bindsListingViewModel(viewModel: ListingViewModel): ViewModel
}