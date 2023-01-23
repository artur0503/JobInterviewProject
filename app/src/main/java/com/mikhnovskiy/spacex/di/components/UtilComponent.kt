package com.mikhnovskiy.spacex.di.components

import com.mikhnovskiy.spacex.di.modules.HelpersModule
import com.mikhnovskiy.spacex.tools.DateHelper
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [HelpersModule::class])
interface UtilComponent {
    fun getDateHelper() : DateHelper
}