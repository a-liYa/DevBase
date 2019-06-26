package com.aliya.base.sample.common;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 计算可靠的设备唯一ID (伪ID)
 *
 * @author a_liYa
 * @date 2019-06-14 09:13.
 */
public class UniqueID {

    private static final List<String> mInvalidAndroidId = new ArrayList<String>() {
        {
            add("9774d56d682e549c");
            add("0123456789abcdef");
        }
    };

    public static String getPseudoID(Context context, String prefix) {
        String mostSig = getMostSig(prefix);
        String leastSig;
        String serial = getSerial();

        if (!TextUtils.isEmpty(serial) && !"unknown".equals(serial)) {
            leastSig = serial;
        } else {
            String androidID = getAndroidID(context);
            if (isValidAndroidID(androidID)) {
                leastSig = androidID;
            } else {
                leastSig = Installation.id(context);
            }
        }
        return new UUID(mostSig.hashCode(), leastSig.hashCode()).toString();
    }

    private static String getMostSig(String prefix) {
        String shortID;
        if (Build.VERSION.SDK_INT < 28 /* Android P */) {
            shortID = prefix
                    + (Build.BOARD.length() % 10)
                    + (Build.BRAND.length() % 10)
                    + (Build.CPU_ABI.length() % 10) // 配置ndk {abiFilters } 不同导致结果不同
                    + (Build.DEVICE.length() % 10)
                    + (Build.MANUFACTURER.length() % 10)
                    + (Build.MODEL.length() % 10)
                    + (Build.PRODUCT.length() % 10);
        } else {
            shortID = prefix
                    + (Build.BOARD.length() % 10)
                    + (Build.BRAND.length() % 10)
                    + (Build.DEVICE.length() % 10)
                    + (Build.MANUFACTURER.length() % 10)
                    + (Build.MODEL.length() % 10)
                    + (Build.PRODUCT.length() % 10);
        }
        return shortID;
    }

    private static String getSerial() {
        if (Build.VERSION.SDK_INT < 28 /* Android P */) {
            try {
                return android.os.Build.class.getField("SERIAL").get(null).toString();
            } catch (Exception e) {
                // no-op
            }
        }
        return null;
    }

    private static String getAndroidID(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            // no-op
        }
        return null;
    }

    private static boolean isValidAndroidID(String androidId) {
        if (TextUtils.isEmpty(androidId)) return false;
        if (mInvalidAndroidId.contains(androidId.toLowerCase())) return false;
        return true;
    }

    /**
     * Installation ID - Google Developer Blog 提供的方案
     *
     * @author a_liYa
     * @date 2019-06-13 17:22.
     */
    private static class Installation {

        private static String sID = null;
        private static final String INSTALLATION = "INSTALLATION";

        public synchronized static String id(Context context) {
            if (sID == null) {
                File installation = new File(context.getFilesDir(), INSTALLATION);
                try {
                    if (!installation.exists())
                        writeInstallationFile(installation);
                    sID = readInstallationFile(installation);
                } catch (Exception e) {
                    sID = UUID.randomUUID().toString();
                }
            }
            return sID;
        }

        private static String readInstallationFile(File installation) throws IOException {
            RandomAccessFile f = new RandomAccessFile(installation, "r");
            byte[] bytes = new byte[(int) f.length()];
            f.readFully(bytes);
            f.close();
            return new String(bytes);
        }

        private static void writeInstallationFile(File installation) throws IOException {
            FileOutputStream out = new FileOutputStream(installation);
            String id = UUID.randomUUID().toString();
            out.write(id.getBytes());
            out.close();
        }
    }
}
