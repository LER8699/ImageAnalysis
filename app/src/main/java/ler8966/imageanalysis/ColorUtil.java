package ler8966.imageanalysis;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class ColorUtil {

    public static void printPixelColorsHSV(Bitmap bmp) {
        for (int i = 0; i < bmp.getHeight(); i++) {
            StringBuffer buff = new StringBuffer();
            for (int j = 0; j < bmp.getWidth(); j++) {
                float[] f = new float[3];
                Color.colorToHSV(bmp.getPixel(j, i), f);
                buff.append(getHSVColor(f[0]) + " ");
                //buff.append(f[0] + ", ");
            }
            Log.d("test", buff.toString());
            buff.setLength(0);
        }
    }

    public static void printPixelSaturationHSV(Bitmap bmp) {
        for (int i = 0; i < bmp.getHeight(); i++) {
            StringBuffer buff = new StringBuffer();
            for (int j = 0; j < bmp.getWidth(); j++) {
                float[] f = new float[3];
                Color.colorToHSV(bmp.getPixel(j, i), f);

                // multiplying saturationv value by 100 for simplicity
                int sat = (int) (f[1] * 100);
                if (sat < 15) {
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

/* Logging cuts off after 4131 characters, clips array

    public static void printByteArray(byte[] byteArray) {
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            buff.append(byteArray[i] + ", ");
        }
        buff.setLength(buff.length()-1);
        Log.d("byteArray", "[" + buff.toString() + "]");
    }
*/

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
}
