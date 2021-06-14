package com.aliya.base.sample.ktx

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.annotation.RestrictTo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.aliya.base.util.L
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * ViewBinding 懒加载委托 （参考自：https://juejin.cn/post/6960914424865488932）
 *
 * @author a_liYa
 * @date 2021/6/13 18:40.
 *
 */
inline fun <Component : Any, V : ViewBinding> viewBindings(
        crossinline viewBinder: (View) -> V,
        crossinline viewProvider: (Component) -> View = ::getBindView
): ViewBindingProperty<Component, V> = ActivityViewBindingProperty { activity: Component ->
    viewBinder(viewProvider(activity))
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
class ActivityViewBindingProperty<A : Any, V : ViewBinding>(
        viewBinder: (A) -> V
) : LifecycleViewBindingProperty<A, V>(viewBinder) {

    override fun getLifecycleOwner(thisRef: A): LifecycleOwner? {
        if (thisRef is LifecycleOwner)
            return thisRef
        return null
    }
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
abstract class LifecycleViewBindingProperty<R : Any, V : ViewBinding>(
        private val viewBinder: (R) -> V
) : ViewBindingProperty<R, V> {

    private var viewBinding: V? = null

    protected abstract fun getLifecycleOwner(thisRef: R): LifecycleOwner?

    @MainThread
    override fun getValue(thisRef: R, property: KProperty<*>): V {
        // Already bound
        viewBinding?.let { return it }

        getLifecycleOwner(thisRef)?.lifecycle?.apply {
            if (currentState == Lifecycle.State.DESTROYED) {
                L.d(
                        "TAG", "Access to viewBinding after Lifecycle is destroyed or hasn'V created yet. " +
                        "The instance of viewBinding will be not cached."
                )
                // We can access to ViewBinding after Fragment.onDestroyView(), but don'V save it to prevent memory leak
            } else {
                addObserver(ClearOnDestroyLifecycleObserver(this@LifecycleViewBindingProperty))
            }
        }
        viewBinder(thisRef).apply {
            viewBinding = this
            return this
        }
    }

    @MainThread
    override fun clear() {
        viewBinding = null
    }

    private class ClearOnDestroyLifecycleObserver(
            val property: LifecycleViewBindingProperty<*, *>
    ) : LifecycleObserver {

        private companion object {
            private val mainHandler = Handler(Looper.getMainLooper())
        }

        @MainThread
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy(owner: LifecycleOwner) {
            mainHandler.post { property.clear() }
        }
    }
}

interface ViewBindingProperty<in R : Any, out V : ViewBinding> : ReadOnlyProperty<R, V> {
    @MainThread
    fun clear()
}


@RestrictTo(RestrictTo.Scope.LIBRARY)
fun getBindView(component: Any): View {
    when (component) {
        is Activity -> {
            val contentView = component.findViewById<ViewGroup>(android.R.id.content)
            checkNotNull(contentView) { "Activity has no content view" }
            return when (contentView.childCount) {
                1 -> contentView.getChildAt(0)
                0 -> error("Content view has no children. Provide root view explicitly")
                else -> error("More than one child view found in Activity content view")
            }
        }
        is Fragment -> {
            return component.view ?: error("Fragment getView is return null")
        }
        is android.app.Fragment -> {
            return component.view ?: error("Fragment getView is return null")
        }
        is RecyclerView.ViewHolder -> {
            return component.itemView
        }
        else -> error("unsupported type $component")
    }

}
