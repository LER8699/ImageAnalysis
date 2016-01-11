package ler8966.imageanalysis;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class ImageAnalyst {
    private Bitmap bmp;

    public ImageAnalyst(Bitmap bmp) {
        if (bmp == null) {
            throw new NullPointerException("bitmap is null");
        } else {
            this.bmp = bmp;
        }
    }

    public Bitmap getBitmap() {
        return bmp;
    }

    public void analyzePNGBytes() {
        byte[] bytes = getBytes();

        Log.d("ImageAnalyst", "byteArray length: " + bytes.length);
        Log.d("ImageAnalyst", "bitmap width: " + bmp.getWidth());
    }

    private byte[] getBytes() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public int[] swapRedAndBlue() {
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

    public int[] showFindRed() {
        int[] colors = ColorUtil.getColorArray(bmp);
        int[] temp = new int[colors.length];
        for (int i = 0; i < colors.length; i++) {
            int b = colors[i];
            if (isRed(b)) {
                temp[i] = Color.rgb(255, 0, 0);
            } else {
                temp[i] = Color.rgb(0, 0, 0);
            }
        }
        return temp;
    }

    public int[] somewhatSimpleRedDetection() {
        int[] distribution = new int[bmp.getWidth()];
        for (int i = 0; i < bmp.getHeight(); i++) {
            for (int j = 0; j < bmp.getWidth(); j++) {
                int b = bmp.getPixel(j, i);
                if (isRed(b)) {
                    distribution[j]++;
                }
            }
        }
        Log.d("ImageAnalyst", Arrays.toString(distribution));
        return null;
    }

    public int superSimpleRedDetection() {
        int left = 0;
        int right = 0;

        for (int i = 0; i < bmp.getHeight(); i++) {
            for (int j = 0; j < bmp.getWidth() / 2; j++) {
                int b = bmp.getPixel(j, i);
                if (isRed(b)) {
                    left++;
                }
            }
        }

        for (int i = 0; i < bmp.getHeight(); i++) {
            for (int j = bmp.getWidth() / 2; j < bmp.getWidth(); j++) {
                int b = bmp.getPixel(j, i);
                if (isRed(b)) {
                    right++;
                }
            }
        }
        Log.d("ImageAnalyst", "right: " + right);
        Log.d("ImageAnalyst", "left: " + left);
        return right - left;
    }


    // todo - implement threshold?
    int threshold = 70; // lower is looser (more will show up as red)

    public boolean isRed(int b) {
        int blue = Color.blue(b);
        int red = Color.red(b);
        int green = Color.green(b);
        int blueGreenAvg = (blue + green) / 2;
        if ((red - blueGreenAvg) > threshold) {
            return true;
        } else {
            return false;
        }
    }

    public String getReportMessage() {
        if (bmp == null) {
            return "no image to report on";
        } else {
            return "image width = " + bmp.getWidth();
        }
    }

}
