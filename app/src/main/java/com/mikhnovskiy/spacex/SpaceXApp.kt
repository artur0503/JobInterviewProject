package com.mikhnovskiy.spacex

import android.app.Application
import com.mikhnovskiy.spacex.di.Injector

class SpaceXApp : Application() {

    val injector: Injector = Injector()

}