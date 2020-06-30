package io.alcatraz.ccd

interface AsyncInterface<T> {
    fun onDone(result: T)
}
