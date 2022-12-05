package com.vovan.pokeapp.utils

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


fun Completable.rxSubscribe(
    compositeDisposable: CompositeDisposable? = null
): Disposable = this.subscribeOn(Schedulers.io())
    .observeOn(Schedulers.computation())
    .subscribe().also { compositeDisposable?.add(it) }

fun <T> Observable<T>.rxSubscribe(
    compositeDisposable: CompositeDisposable? = null,
    subscribe: (T) -> Unit
): Disposable = this.subscribeOn(Schedulers.io())
    .observeOn(Schedulers.computation())
    .subscribe(subscribe).also { compositeDisposable?.add(it) }

fun <T, O> Observable<T>.rxSubscribe(
    compositeDisposable: CompositeDisposable? = null,
    map: (T) -> O,
    subscribe: (O) -> Unit
): Disposable = this.subscribeOn(Schedulers.io())
    .observeOn(Schedulers.computation())
    .map(map)
    .subscribe(subscribe).also { compositeDisposable?.add(it) }