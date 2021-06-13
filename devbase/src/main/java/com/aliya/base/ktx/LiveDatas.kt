package com.aliya.base.ktx

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * LiveDatas
 *
 * @author a_liYa
 * @date 2021/6/12 18:35.
 *
 */

/**
 * LiveData map 转换
 */
fun <X, Y> LiveData<X>.map(function: Function<X, Y>): LiveData<Y> {
    val result = MediatorLiveData<Y>()
    result.addSource(this) {
        result.value = function.apply(it)
    }
    return result;
}

/**
 * LiveData switch map 转换
 */
fun <X, Y> LiveData<X>.switchMap(function: Function<X, LiveData<Y>>): LiveData<Y> {
    val result = MediatorLiveData<Y>()

    var source: LiveData<Y>? = null
    result.addSource(this) {
        val apply = function.apply(it)
        if (source == apply) return@addSource

        if (source != null)
            result.removeSource(source!!)

        source = apply
        if (source != null) {
            result.addSource(source!!) {
                result.value = it
            }
        }
    }
    return result;
}
