package com.braczkow.mvi.lib

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

interface SchedulersFactory {

    val io: Scheduler
    val main: Scheduler

    companion object : SchedulersFactory {
        private var instance: SchedulersFactory = Default()

        class Default : SchedulersFactory {
            override val io
                get() = Schedulers.io()
            override val main
                get() = AndroidSchedulers.mainThread()
        }


        //for test purposes
        fun set(factory: SchedulersFactory) {
            instance = factory
        }

        override val io
            get() = instance.io
        override val main
            get() = instance.main
    }
}
