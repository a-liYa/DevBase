package com.aliya.base.sample.test.util;

import android.content.Intent;

/**
 * Activity启动模式检测工具
 *
 * @author a_liYa
 * @date 2019-12-12 21:54.
 */
public final class ActivityFlags {

    public static String flagsToString(int flags) {
        StringBuffer sb = new StringBuffer();
        if (containsFlag(flags, Intent.FLAG_ACTIVITY_NEW_TASK)) {
            if (sb.length() != 0) sb.append('|');
            sb.append("Intent.FLAG_ACTIVITY_NEW_TASK");
        }
        if (containsFlag(flags, Intent.FLAG_ACTIVITY_SINGLE_TOP)) {
            if (sb.length() != 0) sb.append('|');
            sb.append("Intent.FLAG_ACTIVITY_SINGLE_TOP");
        }
        if (containsFlag(flags, Intent.FLAG_ACTIVITY_CLEAR_TOP)) {
            if (sb.length() != 0) sb.append('|');
            sb.append("Intent.FLAG_ACTIVITY_CLEAR_TOP");
        }
        if (containsFlag(flags, Intent.FLAG_ACTIVITY_CLEAR_TASK)) {
            if (sb.length() != 0) sb.append('|');
            sb.append("Intent.FLAG_ACTIVITY_SINGLE_TOP");
        }
        if (containsFlag(flags, Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)) {
            if (sb.length() != 0) sb.append('|');
            sb.append("Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS");
        }
        return "Flags:" + sb.toString();
    }

    public static boolean containsFlag(int flags, int flag) {
        return (flags & flag) == flag;
    }

}
