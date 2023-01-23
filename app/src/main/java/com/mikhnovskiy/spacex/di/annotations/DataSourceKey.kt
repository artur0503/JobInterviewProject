package com.mikhnovskiy.spacex.di.annotations

import com.mikhnovskiy.spacex.data.storage.db.DataSource
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
@MustBeDocumented
@kotlin.annotation.Target(allowedTargets = [AnnotationTarget.FUNCTION])
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class DataSourceKey(val value: KClass<out DataSource>)