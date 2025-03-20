package ink.labrador.mmsmanager.util;

import java.awt.Color;
import java.security.SecureRandom;

public class ColorUtil {
    /**
     * 生成随机的颜色值,与指定的颜色相近,但不会相同,可以辨认
     * @param color 指定颜色值
     * @return 与标定颜色
     */
    public static Color randomColorNearly(Color color) {
        SecureRandom random = new SecureRandom();
        double hue = (double) random.nextInt(24) / 24;
        double saturation = (double) (random.nextInt(20) + 60) / 100;
        double bgLightness = getLightness(color);
        int minLightness;
        int maxLightness;
        if (bgLightness >= 0.5) {
            minLightness = (int) (Math.round(bgLightness * 100) - 45);
            maxLightness = (int) (Math.round(bgLightness * 100) - 25);
        } else {
            minLightness = (int) (Math.round(bgLightness * 100) + 25);
            maxLightness = (int) (Math.round(bgLightness * 100) + 45);
        }
        double lightness = (double) (random.nextInt(maxLightness - minLightness) + minLightness) / 100;
        double q = lightness < 0.5 ?
                lightness * (lightness + saturation) : lightness + saturation - (lightness * saturation);
        double p = (2 * lightness) - q;
        int r = (int) Math.floor(hueValue2RgbValue(p, q, hue + ((double) 1 / 3)) * 255);
        int g = (int) Math.floor(hueValue2RgbValue(p, q, hue) * 255);
        int b = (int) Math.floor(hueValue2RgbValue(p, q, hue - ((double) 1 / 3)) * 255);
        return new Color(r, g, b);
    }

    public static Color getRandomColor() {
        SecureRandom random = new SecureRandom();
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public static double getLightness(Color color) {
        int max = Math.max(color.getRed(), Math.max(color.getGreen(), color.getBlue()));
        int min = Math.min(color.getRed(), Math.min(color.getGreen(), color.getBlue()));
        return (double) (max + min) / (2 * 255);
    }

    public static double hueValue2RgbValue(double p, double q, double h) {
        h = (h + 1) % 1;
        if (h * 6 < 1) {
            return p + (q - p) * h * 6;
        }
        if (h * 2 < 1) {
            return q;
        }
        if (h * 3 < 2) {
            return p + (q - p) * (((double) 2 / 3) - h) * 6;
        }
        return p;
    }
}
