package com.aliya.base.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * IO流 相关处理工具类
 *
 * @author a_liYa
 * @date 2019/2/25 10:41.
 */
public final class IOs {

    public static void close(Closeable... closeables) {
        if (closeables == null) return;

        for (Closeable closeable : closeables) {
            if (closeable != null)
                try {
                    closeable.close();
                } catch (IOException e) {
                    // no-op
                }
        }
    }

}
