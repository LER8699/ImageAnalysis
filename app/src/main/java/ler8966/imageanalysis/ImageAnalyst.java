package ler8966.imageanalysis;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.io.ByteArrayOutputStream;

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

    public String getReportMessage() {
        if (bmp == null) {
            return "no image to report on";
        } else {
            return "image width = " + bmp.getWidth();
        }
    }

}
