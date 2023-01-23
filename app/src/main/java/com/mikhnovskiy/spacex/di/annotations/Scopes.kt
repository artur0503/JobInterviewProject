package com.mikhnovskiy.spacex.di.annotations

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AndroidLayer

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class BuisnesLogicLayer

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class DataLayer