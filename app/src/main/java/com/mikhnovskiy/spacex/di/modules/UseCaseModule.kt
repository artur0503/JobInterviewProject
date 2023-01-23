package com.mikhnovskiy.spacex.di.modules

import com.mikhnovskiy.spacex.data.repository.NetworkRepository
import com.mikhnovskiy.spacex.domain.ListingLoaderUseCase
import com.mikhnovskiy.spacex.tools.DateHelper
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideListingLoaderUseCase(networkRepository: NetworkRepository, dateHelper: DateHelper) : ListingLoaderUseCase {
            return ListingLoaderUseCase(networkRepository, dateHelper)
    }

}