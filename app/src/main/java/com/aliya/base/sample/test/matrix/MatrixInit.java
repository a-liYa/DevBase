package com.aliya.base.sample.test.matrix;

import android.app.Application;
import android.os.SystemClock;
import android.util.Log;

import com.aliya.base.sample.SplashActivity;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.trace.TracePlugin;
import com.tencent.matrix.trace.config.TraceConfig;

/**
 * MatrixInit
 *
 * @author a_liYa
 * @date 2020/12/15 10:22.
 */
public final class MatrixInit {

    public static void init(Application application) {
        long uptimeMillis = SystemClock.uptimeMillis();
        Matrix.Builder builder = new Matrix.Builder(application); // build matrix
        builder.patchListener(new MatrixPluginListener(application)); // add general pluginListener

        //trace
        TraceConfig traceConfig = new TraceConfig.Builder()
                .enableFPS(true)
                .enableEvilMethodTrace(true)
                .enableAnrTrace(true)
                .enableStartup(true)
                .splashActivities(SplashActivity.class.getName() + ";")
                .isDebug(true)
                .isDevEnv(false)
                .build();

        TracePlugin tracePlugin = (new TracePlugin(traceConfig));
        builder.plugin(tracePlugin);

        // init plugin
//        IOCanaryPlugin ioCanaryPlugin = new IOCanaryPlugin(new IOConfig.Builder()
//                .dynamicConfig(new MatrixDynamicConfigImpl()) // dynamic config
//                .build());
        // add to matrix
//        builder.plugin(ioCanaryPlugin);

        // init matrix
        Matrix.init(builder.build());

        // start plugin
//        ioCanaryPlugin.start();

        Log.e("TAG", "腾讯 Matrix init: " + (SystemClock.uptimeMillis() - uptimeMillis) + " ms");
    }
}
