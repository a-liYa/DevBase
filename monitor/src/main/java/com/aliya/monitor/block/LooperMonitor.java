package com.aliya.monitor.block;

import android.os.Debug;
import android.os.SystemClock;
import android.util.Printer;


/**
 * LooperMonitor
 *
 * @author a_liYa
 * @date 2020/12/15 18:41.
 */
public class LooperMonitor implements Printer {


    private static final int DEFAULT_BLOCK_THRESHOLD_MILLIS = 3000;

    private long mBlockThresholdMillis = DEFAULT_BLOCK_THRESHOLD_MILLIS;
    private long mStartTimestamp = 0;
    private long mStartThreadTimestamp = 0;
    private BlockListener mBlockListener = null;
    private boolean mPrintingStarted = false;
    private final boolean mStopWhenDebugging;

    public static long getSampleDelay() {
        return (long) (BlockCanary.sContext.provideBlockThreshold() * 0.8f);
    }

    public interface BlockListener {
        void onBlockEvent(long realStartTime,
                          long realTimeEnd,
                          long threadTimeStart,
                          long threadTimeEnd);
    }

    public LooperMonitor(BlockListener blockListener, long blockThresholdMillis, boolean stopWhenDebugging) {
        if (blockListener == null) {
            throw new IllegalArgumentException("blockListener should not be null.");
        }
        mBlockListener = blockListener;
        mBlockThresholdMillis = blockThresholdMillis;
        mStopWhenDebugging = stopWhenDebugging;
    }

    @Override
    public void println(String x) {
        if (mStopWhenDebugging && Debug.isDebuggerConnected()) {
            return;
        }
        if (!mPrintingStarted) {
            mStartTimestamp = System.currentTimeMillis();
            mStartThreadTimestamp = SystemClock.currentThreadTimeMillis();
            mPrintingStarted = true;
            startDump();
        } else {
            final long endTime = System.currentTimeMillis();
            mPrintingStarted = false;
            if (isBlock(endTime)) {
                notifyBlockEvent(endTime);
            }
            stopDump();
        }
    }

    private boolean isBlock(long endTime) {
        return endTime - mStartTimestamp > mBlockThresholdMillis;
    }

    private void notifyBlockEvent(final long endTime) {
        final long startTime = mStartTimestamp;
        final long startThreadTime = mStartThreadTimestamp;
        final long endThreadTime = SystemClock.currentThreadTimeMillis();
        HandlerThreadFactory.getWriteLogThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                mBlockListener.onBlockEvent(startTime, endTime, startThreadTime, endThreadTime);
            }
        });
    }

    private void startDump() {
        if (null != BlockCanary.get().stackSampler) {
            BlockCanary.get().stackSampler.start();
        }

        if (null != BlockCanary.get().cpuSampler) {
            BlockCanary.get().cpuSampler.start();
        }
    }

    private void stopDump() {
        if (null != BlockCanary.get().stackSampler) {
            BlockCanary.get().stackSampler.stop();
        }

        if (null != BlockCanary.get().cpuSampler) {
            BlockCanary.get().cpuSampler.stop();
        }
    }
}
