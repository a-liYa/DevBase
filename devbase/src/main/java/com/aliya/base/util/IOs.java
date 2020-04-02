package com.aliya.base.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * IO流 相关处理工具类
 *
 * @author a_liYa
 * @date 2019/2/25 10:41.
 */
public final class IOs {

    /**
     * 获取 Assets  资源文件内的字符串
     *
     * @param path path eg: xxx.html
     * @return 文件内部的字符
     */
    public static String getAssetsText(Context context, String path) {
        AssetManager assets = context.getAssets();
        try {
            StringBuffer buffer = new StringBuffer();
            InputStream is = assets.open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String lineStr;

            while ((lineStr = reader.readLine()) != null) {
                buffer.append(lineStr);
            }

            reader.close();
            is.close();

            return buffer.toString();
        } catch (IOException e) {
            // Ignore it.
        }

        return null;
    }

    /**
     * 批量关闭数据源
     * <p>
     * JDK 1.7 (Android 5.0) 引入 AutoCloseable
     *
     * @param closeables Closeable JDK 1.5 引入
     */
    public static void close(Closeable... closeables) {
        if (closeables == null) return;

        for (Closeable closeable : closeables) {
            if (closeable != null)
                try {
                    closeable.close();
                } catch (IOException e) {
                    // Ignore it.
                }
        }
    }

}
