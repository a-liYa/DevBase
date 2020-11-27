package com.aliya.base.sample.ui.activity.photo;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 存储卷 相关处理 工具类
 *
 * @author a_liYa
 * @date 2020/11/27 13:52.
 */
public class Storages {

    /**
     * 获取可用的存储卷地址
     *
     * @param context   .
     * @param removable 可移动设备，including SD cards and USB drives。
     * @return null 表示没有满足条件的存储地址
     */
    public static String getStoragePath(Context context, boolean removable) {
        StorageManager storageManager =
                (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        StorageVolume[] volumes = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 对象可用的共享/外部存储卷的列表
            List<StorageVolume> volumeList = storageManager.getStorageVolumes();
            volumes = volumeList.toArray(new StorageVolume[volumeList.size()]);
        } else {
            try {
                Method getVolumeListMethod = StorageManager.class.getMethod("getVolumeList");
                volumes = (StorageVolume[]) getVolumeListMethod.invoke(storageManager);
            } catch (Exception e) {
            }
        }
        if (volumes != null) {
            for (StorageVolume volume : volumes) {
                if (removable == isRemovableStorageVolume(volume) &&
                        Environment.MEDIA_MOUNTED.equals(getStorageVolumeState(volume))) {
                    return getStorageVolumePath(volume);
                }
            }
        }
        return null;
    }

    private static String getStorageVolumeState(StorageVolume volume) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return volume.getState();
        } else {
            try {
                Method getStateMethod = volume.getClass().getMethod("getState");
                return (String) getStateMethod.invoke(volume);
            } catch (Exception e) {
                return Environment.MEDIA_UNKNOWN;
            }
        }
    }

    private static boolean isRemovableStorageVolume(StorageVolume volume) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return volume.isRemovable();
        } else {
            try {
                Method isRemovable = volume.getClass().getMethod("isRemovable");
                return (boolean) isRemovable.invoke(volume);
            } catch (Exception e) {
                return false;
            }
        }
    }

    private static String getStorageVolumePath(StorageVolume volume) {
        try {
            Class storageVolumeClass;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                storageVolumeClass = StorageVolume.class;
            } else {
                storageVolumeClass = Class.forName("android.os.storage.StorageVolume");
            }
            Method getPathMethod = storageVolumeClass.getMethod("getPath");
            return (String) getPathMethod.invoke(volume);
        } catch (Exception e) {
            return null;
        }
    }
}
