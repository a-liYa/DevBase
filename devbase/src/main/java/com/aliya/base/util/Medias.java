package com.aliya.base.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

/**
 * media 相关处理工具类
 *
 * @author a_liYa
 * @date 2019/2/21 10:53.
 */
public final class Medias {

    /**
     * 扫描文件，加入相册预览
     *
     * @param context Context
     * @param path    文件绝对路径
     * @see android.Manifest.permission_group#STORAGE 加入对sd卡操作的权限
     */
    public static void scanFile(Context context, String path) {
        if (TextUtils.isEmpty(path)) return;

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(new File(path)));
        context.sendBroadcast(intent);

        // 以下是同步操作,但比较耗时 3M的照片耗时1233ms
//        try {
//            MediaStore.Images.Media.insertImage(sContext.getContentResolver(),
//                    path, new File(path).getName(), null);
//        } catch (FileNotFoundException e) {
//            // no-op
//        }
    }

    /**
     * 创建隐藏媒体资源的文件
     *
     * @param dir 文件夹路径
     */
    public static void createNoMedia(String dir) {
        File noMedia = new File(dir, ".nomedia");
        try {
            if (!noMedia.exists())
                noMedia.createNewFile();
        } catch (IOException e) {
            // no-op
        }
    }

}
