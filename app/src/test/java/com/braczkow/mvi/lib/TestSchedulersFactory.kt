package com.braczkow.mvi.lib

import io.reactivex.rxjava3.schedulers.Schedulers

class TestSchedulersFactory : SchedulersFactory {
    override val io = Schedulers.trampoline()
    override val main = Schedulers.trampoline()
}
