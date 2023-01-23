package com.mikhnovskiy.spacex.di.modules

import com.mikhnovskiy.spacex.data.repository.StorageRepository
import com.mikhnovskiy.spacex.data.storage.db.DataSource
import com.mikhnovskiy.spacex.data.storage.db.PersistenceCache
import com.mikhnovskiy.spacex.di.annotations.DataSourceKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [StorageModule.DataSourceBinder::class])
class StorageModule {

    @Provides
    @Singleton
    fun provideStorageRepository(dataSource: DataSource): StorageRepository {
        return StorageRepository(dataSource)
    }

    @Module
    abstract class DataSourceBinder {
        @Binds
        @DataSourceKey(PersistenceCache::class)
        abstract fun bindDataSource(outDataSource : PersistenceCache) : DataSource
    }

}