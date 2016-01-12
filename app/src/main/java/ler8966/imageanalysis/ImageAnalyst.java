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

    /**
     * takes distribution of a color and calculates location
     * For example: array [0,0,0,1,1,2,2,3,4,10,20,22,34,45] represents a high concentration of
     * color on the extreme right.
     * <p/>
     * This method takes the middle of the distribution and returns how much to the right or left
     * of the middle the blob is (negative for left, positive for right). It is not weighted as to
     * the values - maybe later.
     * <p/>
     * todo - if no red found, it returns center, which is wrong. Need a way to show no red found
     */

    public int analyzeDistribution(int[] distribution) {
      /*  int average = 0;
        for (int i = 0; i < distribution.length; i++) {
            average += distribution[i];
        }
        average = average / distribution.length;
        Log.d("ImageAnalyst", "average = " + average);
        // normalize distribution by subtracting average (but not allowing negative numbers. If
        // subtracting average leads to a negative number, then use 0.
        for (int i = 0; i < distribution.length; i++) {
            int val = distribution[i] - average;
           // Log.d("ImageAnalyst", "changing from " + distribution[i] + " to " + val);
            distribution[i] = val < 0 ? 0 : val;
        }
        Log.d("ImageAnalyst", "Normalized Distribution: ");
        Log.d("ImageAnalyst", Arrays.toString(distribution));
        */
        // find first index of red
        int startIndex = 0;
        for (int i = 0; i < distribution.length; i++) {
            if (distribution[i] > 0) {
                startIndex = i;
                break;
            }
        }
        // iterate backwards to find lastIndex of red
        int endIndex = distribution.length - 1;
        for (int i = distribution.length - 1; i >= startIndex; i--) {
            if (distribution[i] > 0) {
                endIndex = i;
                break;
            }
        }

        Log.d("ImageAnalyst", "start/end = " + startIndex + " / " + endIndex);
        // average start and end indexes
        int blobMiddle = (startIndex + endIndex) / 2;

        // get how much left or right the blob is
        // negative will be left, positive will be right
        int dirAmt = blobMiddle - bmp.getWidth() / 2;

        Log.d("ImageAnalyst", "blobMiddle = " + blobMiddle);
        Log.d("ImageAnalyst", "bmp middle =  " + bmp.getWidth() / 2);
        return dirAmt;
    }

    public int[] getVerticalDistribution(char rb) throws IllegalArgumentException {
        int[] distribution = new int[bmp.getWidth()];
        for (int i = 0; i < bmp.getHeight(); i++) {
            for (int j = 0; j < bmp.getWidth(); j++) {
                int b = bmp.getPixel(j, i);
                if (rb == 'R') {
                    if (ColorUtil.isRed(b)) {
                        distribution[j]++;
                    }
                } else if (rb == 'B') {
                    if (ColorUtil.isBlue(b)) {
                        distribution[j]++;
                    }
                } else {
                    throw new IllegalArgumentException(rb + " is not a valid argument - use R for red or B for blue.");
                }
            }
        }

        Log.d("ImageAnalyst", Arrays.toString(distribution));
        return distribution;
    }

    public int superSimpleRedDetection() {
        return superSimpleColorDetection('R');
    }


    public int superSimpleBlueDetection() {
        return superSimpleColorDetection('B');
    }

    protected int superSimpleColorDetection(char rb) throws IllegalArgumentException {
        int left = 0;
        int right = 0;

        for (int i = 0; i < bmp.getHeight(); i++) {
            for (int j = 0; j < bmp.getWidth() / 2; j++) {
                int b = bmp.getPixel(j, i);
                if (ColorUtil.isRed(b)) {
                    left++;
                }
            }
        }

        for (int i = 0; i < bmp.getHeight(); i++) {
            for (int j = bmp.getWidth() / 2; j < bmp.getWidth(); j++) {
                int b = bmp.getPixel(j, i);
                if (rb == 'R') {
                    if (ColorUtil.isRed(b)) {
                        right++;
                    }
                } else if (rb == 'B') {
                    if (ColorUtil.isBlue(b)) {
                        right++;
                    }
                } else {
                    throw new IllegalArgumentException(rb + " is not a valid argument - use R for red or B for blue.");
                }
            }
        }
        Log.d("ImageAnalyst", "right: " + right);
        Log.d("ImageAnalyst", "left: " + left);
        return right - left;
    }


    // todo - implement threshold?


    public String getReportMessage() {
        if (bmp == null) {
            return "no image to report on";
        } else {
            return "image width = " + bmp.getWidth();
        }
    }

}
