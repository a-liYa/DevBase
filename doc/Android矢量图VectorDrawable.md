## Android 矢量图 VectorDrawable

Android API 21（5.0）引入**VectorDrawable**、**AnimatedVectorDrawable**

小于 21 使用兼容包 **VectorDrawableCompat**、**AnimatedVectorDrawableCompat**

但从 ResourceManagerInternal 源码看到，Android API 24（7.0）以下均使用兼容包

```
ResourceManagerInternal {
    private static void installDefaultInflateDelegates(@NonNull ResourceManagerInternal manager) {
        // This sdk version check will affect src:appCompat code path.
        // Although VectorDrawable exists in Android framework from Lollipop, AppCompat will use
        // (Animated)VectorDrawableCompat before Nougat to utilize bug fixes & feature backports.
        if (Build.VERSION.SDK_INT < 24) {
            manager.addDelegate("vector", new VdcInflateDelegate());
            manager.addDelegate("animated-vector", new AvdcInflateDelegate());
            manager.addDelegate("animated-selector", new AsldcInflateDelegate());
            manager.addDelegate("drawable", new DrawableDelegate());
        }
    }
}
```

#### build.gradle中vectorDrawables.useSupportLibrary属性

```
defaultConfig {
	// 控制打包编译是否不生成对应的 png 资源,true 表示不转换对应的 png
	// 默认值与 minSdkVersion 有关， minSdkVersion >= 21 时，默认为 true
	vectorDrawables.useSupportLibrary = true
}
```