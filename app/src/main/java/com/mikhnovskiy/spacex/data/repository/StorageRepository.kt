package com.mikhnovskiy.spacex.data.repository

import com.mikhnovskiy.spacex.data.storage.db.DataSource
import javax.inject.Inject

class StorageRepository @Inject constructor(private val dataSource: DataSource) {}