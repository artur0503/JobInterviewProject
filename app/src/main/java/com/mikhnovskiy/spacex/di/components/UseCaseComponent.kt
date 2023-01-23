package com.mikhnovskiy.spacex.di.components

import com.mikhnovskiy.spacex.di.annotations.BuisnesLogicLayer
import com.mikhnovskiy.spacex.di.modules.UseCaseModule
import com.mikhnovskiy.spacex.domain.ListingLoaderUseCase
import dagger.Component

@BuisnesLogicLayer
@Component(dependencies = [DataComponent::class, UtilComponent::class], modules = [UseCaseModule::class])
interface UseCaseComponent {
    fun getListingLoaderUseCase() : ListingLoaderUseCase
}