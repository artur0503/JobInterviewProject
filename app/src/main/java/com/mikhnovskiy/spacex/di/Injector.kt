package com.mikhnovskiy.spacex.di

import com.mikhnovskiy.spacex.di.components.*
import com.mikhnovskiy.spacex.di.modules.HelpersModule
import com.mikhnovskiy.spacex.di.modules.NetworkModule
import com.mikhnovskiy.spacex.di.modules.StorageModule
import com.mikhnovskiy.spacex.di.modules.UseCaseModule

class Injector {

    private val utilComponent: UtilComponent =
        DaggerUtilComponent
            .builder()
            .helpersModule(HelpersModule())
            .build()

    private val dataComponent: DataComponent =
        DaggerDataComponent
            .builder()
            .storageModule(StorageModule())
            .networkModule(NetworkModule())
            .build()

    private val useCaseComponent: UseCaseComponent =
        DaggerUseCaseComponent
            .builder()
            .dataComponent(dataComponent)
            .utilComponent(utilComponent)
            .useCaseModule(UseCaseModule())
            .build()

    private val androidComponents: AndroidComponents =
        DaggerAndroidComponents
            .builder()
            .utilComponent(utilComponent)
            .useCaseComponent(useCaseComponent)
            .build()

    val activityComponent: ActivityComponent by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { androidComponents.activityComponent }

    val fragmentComponent: FragmentComponent by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { androidComponents.fragmentComponent }
}