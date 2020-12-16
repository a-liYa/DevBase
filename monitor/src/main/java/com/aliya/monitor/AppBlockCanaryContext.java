//package com.aliya.monitor;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.github.moduth.blockcanary.BlockCanaryContext;
//import com.github.moduth.blockcanary.internal.BlockInfo;
//import com.github.moduth.blockcanary.BlockCanary;
//
//import java.io.File;
//
///**
// * AppBlockCanaryContext
// *
// * @author a_liYa
// * @date 2020/12/15 14:18.
// */
//public class AppBlockCanaryContext extends BlockCanaryContext {
//
//    public static void init(Context context) {
//        BlockCanary.install(context.getApplicationContext(), new AppBlockCanaryContext()).start();
//    }
//
//    /**
//     * Config block threshold (in millis), dispatch over this duration is regarded as a BLOCK. You may set it
//     * from performance of device.
//     *
//     * @return threshold in mills
//     */
//    public int provideBlockThreshold() {
//        return 100;
//    }
//
//    /**
//     * Thread stack dump interval, use when block happens, BlockCanary will dump on main thread
//     * stack according to current sample cycle.
//     * <p>
//     * Because the implementation mechanism of Looper, real dump interval would be longer than
//     * the period specified here (especially when cpu is busier).
//     * </p>
//     *
//     * @return dump interval (in millis)
//     */
//    public int provideDumpInterval() {
//        return provideBlockThreshold();
//    }
//
//    /**
//     * Implement in your project, bundled log files.
//     *
//     * @param zippedFile zipped file
//     */
//    public void upload(File zippedFile) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void onBlock(Context context, BlockInfo blockInfo) {
//        Log.e("TAG", "onBlock: " + blockInfo);
//    }
//
//    @Override
//    public boolean stopWhenDebugging() {
//        return false;
//    }
//}
