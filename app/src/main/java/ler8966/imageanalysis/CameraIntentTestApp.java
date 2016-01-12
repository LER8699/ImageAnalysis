package ler8966.imageanalysis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
/* from Orient OpMode */


public class CameraIntentTestApp extends AppCompatActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 114;
    protected ImageAnalyst analyst;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);

        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        Log.d("test", "Activity Started");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("test", "onActivityResult");
        Bundle extras = data.getExtras();
        Bitmap bmp = (Bitmap) extras.get("data");

        analyst = new ImageAnalyst(bmp);
        //analyst.analyzePNGBytes();

        //ColorUtil.printByteArray(byteArray);
        //ColorUtil.printPixelSaturationHSV(bmp);


        showResponse();
    }

    protected void showResponse() {
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.my_message);
        tv.setText(analyst.getReportMessage());

        // show the original image
        ImageView image = (ImageView) findViewById(R.id.imageView1);
        image.setImageBitmap(analyst.getBitmap());

        char color = 'R';

        int i = analyst.analyzeDistribution(analyst.getVerticalDistribution(color));
        if (i < 0) {
            tv.setText("left by " + i);
        } else if (i > 0) {
            tv.setText("right by " + i);
        } else {
            tv.setText("center");
        }

        int[] img = ImageAlterer.showVerticalDistributionImage(color, analyst.getBitmap());
        Bitmap bmp = createBitmap(img);
        ImageAlterer.drawVerticalLineAt(bmp.getWidth()/2 + i, bmp);
        fillImageView2(bmp);

    }

    protected Bitmap createBitmap(int[] newImage) {
        // create bitmap with old bitmap height & width
        int width = analyst.getBitmap().getWidth();
        int height = analyst.getBitmap().getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        newBitmap.setPixels(newImage, 0, width, 0, 0, width, height);
        newBitmap.setHasAlpha(true);
        return newBitmap;
    }

    protected void fillImageView2(int[] newImage) {
        Bitmap newBitmap = createBitmap(newImage);
        fillImageView2(newBitmap);
    }

    protected void fillImageView2(Bitmap newBitmap) {
        ImageView image2 = (ImageView) findViewById(R.id.imageView2);
        image2.setImageBitmap(newBitmap);
    }
}



