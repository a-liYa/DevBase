package com.aliya.base.compat;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * DensityDpi相关处理 - 兼容类
 *
 * @author a_liYa
 * @date 2017/3/31 16:30.
 */
public class DensityCompat {

    public static Resources forceDensityDpiByResources(Resources resources) {
        Configuration config = resources.getConfiguration();
        /**
         *  0.8   0.9  1.0  1.1  1.2  1.35  1.5
         *   |    |     |    |    |    ｜　　｜
         * 极小   特小   小　  中   大   特大  超大
         */
        // 在这里设置字号 config.fontScale =

        final int dpi = matchFitDensityDpi();
        if (config.densityDpi != dpi) {
            config.densityDpi = dpi;
            // updateConfiguration方法已过时，暂时未找到替代方案
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        }
        return resources;
    }

    public static int sFitDensityDpi;

    /**
     * 匹配合适的 density dpi
     *
     * @return 返回匹配的 density dpi 理论值
     */
    public static int matchFitDensityDpi() {
        if (sFitDensityDpi == 0) {
            DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
            int width = Math.min(metrics.widthPixels, metrics.heightPixels);
            int height = Math.max(metrics.widthPixels, metrics.heightPixels);
            DpiEntity[] values = DpiEntity.values();

            double min = -1;
            int index = 0;

            for (int i = 0; i < values.length; i++) {
                DpiEntity value = values[i];
                double distance = getDistance(value.getWidth(), value.getHeight(), width, height);
                if (min < 0) {
                    min = distance;
                    index = i;
                } else if (distance < min) {
                    min = distance;
                    index = i;
                }
            }
            sFitDensityDpi = values[index].getDpi();
        }
        return sFitDensityDpi;
    }

    private static double getDistance(int x1, int y1, int x2, int y2) {
        double _x = Math.abs(x1 - x2);
        double _y = Math.abs(y1 - y2);
        return Math.sqrt(_x * _x + _y * _y);
    }

    /**
     * @see Configuration
     * @see DisplayMetrics
     */
    private enum DpiEntity {
        L(240, 320, DisplayMetrics.DENSITY_LOW, 0.75f),
        M(320, 480, DisplayMetrics.DENSITY_MEDIUM, 1f),
        H(480, 800, DisplayMetrics.DENSITY_HIGH, 1.5f),
        XH(720, 1280, DisplayMetrics.DENSITY_XHIGH, 2f),
        XXH(1080, 1920, DisplayMetrics.DENSITY_XXHIGH, 3f),
        XXXH(1440, 2560, DisplayMetrics.DENSITY_XXXHIGH, 4f);

        private int width;
        private int height;
        private int dpi;
        private float density;

        DpiEntity(int width, int height, int dpi, float density) {
            this.width = width;
            this.height = height;
            this.dpi = dpi;
            this.density = density;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getDpi() {
            return dpi;
        }

        public float getDensity() {
            return density;
        }
    }

}
