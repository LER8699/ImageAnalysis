package ler8966.imageanalysis;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class ColorUtil {

    // not sure if these should be final
    public static final int BLUE_THRESHOLD = 40; // lower is looser (more will show up as blue)
    public static final int RED_THRESHOLD = 70; // lower is looser (more will show up as red)

    public static void printPixelColorsHSV(Bitmap bmp) {
        for (int i = 0; i < bmp.getHeight(); i++) {
            StringBuffer buff = new StringBuffer();
            for (int j = 0; j < bmp.getWidth(); j++) {
                float[] f = new float[3];
                Color.colorToHSV(bmp.getPixel(j, i), f);
                buff.append(getHSVColor(f[0]) + " ");
                //buff.append(f[0] + ", ");
            }
            Log.d("ColorUtil", buff.toString());
            buff.setLength(0);
        }
    }

    public static void printPixelSaturationHSV(Bitmap bmp) {
        for (int i = 0; i < bmp.getHeight(); i++) {
            StringBuffer buff = new StringBuffer();
            for (int j = 0; j < bmp.getWidth(); j++) {
                float[] f = new float[3];
                Color.colorToHSV(bmp.getPixel(j, i), f);

                // multiplying saturation value by 100 for simplicity
                int sat = (int) (f[1] * 100);
                int black = (int) (f[2] * 100);
                if (black < 10) {
                    buff.append("| ");
                } else if (sat < 15) {
                    buff.append("X ");
                } else {
                    buff.append(". ");
                }
                //buff.append((f[1]) + " ");

            }
            Log.d("test", buff.toString());
            buff.setLength(0);
        }
    }

    public static int[] getColorArray(Bitmap bmp) {
        int width = bmp.getWidth();
        int[] pixels = new int[bmp.getHeight() * width];
        bmp.getPixels(pixels, 0, width, 1, 1, width - 1, bmp.getHeight() - 1);
        return pixels;
    }

    public static String getHSVColor(float f) {
        if (f < 32) {
            return "RED";
        } else if (f < 64) {
            return "ORANGE";
        } else if (f < 96) {
            return "YELLOW";
        } else if (f < 128) {
            return "GREEN";
        } else if (f < 160) {
            return "AQUA";
        } else if (f < 192) {
            return "BLUE";
        } else if (f < 224) {
            return "PURPLE";
        } else {
            return "PINK";
        }
    }

    public static boolean isRed(int b) {
        int blue = Color.blue(b);
        int red = Color.red(b);
        int green = Color.green(b);
        int blueGreenAvg = (blue + green) / 2;
        if ((red - blueGreenAvg) > RED_THRESHOLD) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isBlue(int b) {
        int blue = Color.blue(b);
        int red = Color.red(b);
        int green = Color.green(b);
        int redGreenAvg = (red + green) / 2;
        if ((blue - redGreenAvg) > BLUE_THRESHOLD) {
            return true;
        } else {
            return false;
        }
    }
}
