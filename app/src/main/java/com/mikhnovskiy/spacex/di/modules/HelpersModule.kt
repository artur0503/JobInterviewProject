package com.mikhnovskiy.spacex.di.modules

import com.mikhnovskiy.spacex.tools.DateHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HelpersModule {

    @Singleton
    @Provides
    fun provideDateHelper() : DateHelper {
        return DateHelper()
    }

}