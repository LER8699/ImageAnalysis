package ler8966.imageanalysis;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class ImageAlterer {

    public static int[] swapRedAndBlue(Bitmap bmp) {
        int[] colors = ColorUtil.getColorArray(bmp);
        int[] temp = new int[colors.length];
        for (int i = 0; i < colors.length; i++) {
            int b = colors[i];
            int blue = Color.blue(b);
            int red = Color.red(b);
            int c = Color.rgb(blue, Color.green(b), red); // swap red & blue
            temp[i] = c;
        }
        return temp;
    }

    public static int[] showFindRed(Bitmap bmp) {
        int[] colors = ColorUtil.getColorArray(bmp);
        int[] temp = new int[colors.length];
        for (int i = 0; i < colors.length; i++) {
            int b = colors[i];
            if (ColorUtil.isRed(b)) {
                temp[i] = Color.rgb(255, 0, 0);
            } else {
                temp[i] = Color.rgb(0, 0, 0);
            }
        }
        return temp;
    }

    public static int[] showVerticalDistributionImage(char rb, Bitmap bmp) throws IllegalArgumentException {
        int[] temp = new int[bmp.getWidth() * bmp.getHeight()];
        int counter = 0;
        for (int i = 0; i < bmp.getHeight(); i++) {
            for (int j = 0; j < bmp.getWidth(); j++) {
                temp[counter] = Color.rgb(235, 235, 235);
                int b = bmp.getPixel(j, i);
                if (rb == 'R') {
                    if (ColorUtil.isRed(b)) {
                        temp[counter] = Color.rgb(255, 0, 0);
                    }
                } else if (rb == 'B') {
                    if (ColorUtil.isBlue(b)) {
                        temp[counter] = Color.rgb(0, 0, 255);
                    }
                } else {
                    throw new IllegalArgumentException(rb + " is not a valid argument - use R for red or B for blue.");
                }
                counter++;
            }
        }

        for (int i = 0; i < bmp.getHeight(); i++) {
            //temp[j] = Color.rgb()
        }
        return temp;
    }

    public static void drawVerticalLineAt(int x, Bitmap bmp) {
        for (int i = 0; i < bmp.getHeight(); i++) {
            bmp.setPixel(x, i, Color.rgb(0, 0, 0));
        }
    }

    public static byte[] getBytes(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
