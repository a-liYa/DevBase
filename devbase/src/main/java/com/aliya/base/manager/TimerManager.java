package com.aliya.base.manager;

import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;

/**
 * 定时任务的封装
 *
 * @author a_liYa
 * @date 2018/11/27 上午9:26.
 */
public class TimerManager {

    private static SparseArray<TimerTask> tasks = new SparseArray<>();
    /**
     * what 生成器
     */
    private static int what = 0;

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TimerTask timerTask = tasks.get(msg.what);
            if (timerTask != null) {

                timerTask.run(++timerTask.count);
                sendEmptyMessageDelayed(timerTask.what, timerTask.period);

            }
        }
    };

    /**
     * Message.what 生成器
     *
     * @return Integer
     */
    private static int generateWhat() {
        if (what == Integer.MAX_VALUE) {
            what = 0;
            return Integer.MAX_VALUE;
        } else {
            return what++;
        }
    }

    /**
     * 开启定时任务
     *
     * @param task
     */
    public static TimerTask schedule(TimerTask task) {

        if (task == null) {
            return null;
        }
        tasks.append(task.what, task);

        if (task.delay <= 0) {
            mHandler.sendEmptyMessage(task.what);
        } else {
            mHandler.sendEmptyMessageDelayed(task.what, task.delay);
        }

        return task;
    }

    /**
     * 取消定时任务
     *
     * @param task 被取消任务
     */
    public static void cancel(TimerTask task) {
        if (task == null)
            return;

        mHandler.removeMessages(task.what);
        tasks.delete(task.what);

    }

    /**
     * 取消所有定时任务
     */
    public static void cancelAll() {

        mHandler.removeCallbacksAndMessages(null);
        tasks.clear();

    }

    /**
     * 定时任务自定义 - 封装
     *
     * @author a_liYa
     * @date 16/7/26 下午4:48.
     */
    static public abstract class TimerTask {

        private int what;
        /**
         * 时间间隔
         */
        private long period;
        /**
         * 首次执行的延迟时间
         */
        private long delay;

        /**
         * 执行次数
         */
        private long count = 0;

        public TimerTask(long period) {
            this.what = generateWhat();
            this.period = period;
        }

        public TimerTask(long period, long delay) {
            this.what = generateWhat();
            this.period = period;
            this.delay = delay;
        }

        /**
         * UI线程中执行
         *
         * @param count 当前执行的次数
         */
        public abstract void run(long count);

    }

}
