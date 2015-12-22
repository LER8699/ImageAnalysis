package ler8966.imageanalysis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.ByteArrayOutputStream;
/* from Orient OpMode */


public class CameraIntentTestApp extends AppCompatActivity {

    private Uri fileUri;

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

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 114;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("test", "onActivityResult");
        Bundle extras = data.getExtras();
        Bitmap bmp = (Bitmap) extras.get("data");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        Log.d("test", "byteArray length: " + byteArray.length);
        Log.d("test", "bitmap width: " + bmp.getWidth());

        //ColorUtil.printByteArray(byteArray);
        ColorUtil.printPixelSaturationHSV(bmp);
    }


}



