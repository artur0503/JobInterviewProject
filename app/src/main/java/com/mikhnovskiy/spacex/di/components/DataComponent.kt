package com.mikhnovskiy.spacex.di.components

import com.mikhnovskiy.spacex.data.network.Api
import com.mikhnovskiy.spacex.data.repository.NetworkRepository
import com.mikhnovskiy.spacex.data.repository.StorageRepository
import com.mikhnovskiy.spacex.data.storage.db.DataSource
import com.mikhnovskiy.spacex.di.annotations.DataLayer
import com.mikhnovskiy.spacex.di.modules.NetworkModule
import com.mikhnovskiy.spacex.di.modules.StorageModule
import dagger.Component
import javax.inject.Singleton

@DataLayer
@Singleton
@Component(modules = [StorageModule::class, NetworkModule::class])
interface DataComponent {
    fun getApi(): Api
    fun getNetworkRepository() : NetworkRepository
    fun getPersistenceCache() : DataSource
    fun getStorageRepository() : StorageRepository
}