package ler8966.imageanalysis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.ByteArrayOutputStream;
/* from Orient OpMode */


public class CameraIntentTestApp extends AppCompatActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 114;
    protected ImageAnalyst analyst;
    private char color = 'R';
    private int tolerance1 = 80;
    private int tolerance2 = 40;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //setContentView(R.layout.main);

        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        Log.d("test", "Activity Started");

        ToggleButton toggle = (ToggleButton) findViewById(R.id.tb1);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    color = 'R';
                    showResponse();
                } else {
                    color = 'B';
                    showResponse();
                }
            }
        });
        EditText editText = (EditText) findViewById(R.id.tolerance1);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                tolerance1 = Integer.parseInt(String.valueOf(v.getText()));
                showResponse();
                return false;
            }
        });

        editText = (EditText) findViewById(R.id.tolerance2);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                tolerance2 = Integer.parseInt(String.valueOf(v.getText()));
                showResponse();
                return false;
            }
        });

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

        // show the original image
        ImageView image = (ImageView) findViewById(R.id.imageView1);
        image.setImageBitmap(analyst.getBitmap());

        // show altered images at different tolerances
        createImageView(R.id.tv1, color, tolerance1, R.id.imageView2);
        createImageView(R.id.tv2, color, tolerance2, R.id.imageView3);

    }

    private void createImageView(int textViewId, char color, int tolerance, int viewId) {
        int i = analyst.analyzeDistribution(analyst.getVerticalDistribution(color, tolerance));
        TextView tv = (TextView) findViewById(textViewId);
        tv.setText(getLocationText(i, tolerance));

        int[] img = ImageAlterer.showVerticalDistributionImage(color, analyst.getBitmap(), tolerance);
        Bitmap bmp = createBitmap(img);
        ImageAlterer.drawVerticalLineAt(bmp.getWidth() / 2 + i, bmp);
        fillImageView(bmp, viewId);
    }

    public String getLocationText(int i, int tolerance) {
        if (i < 0) {
            return "t = " + tolerance + "; left by " + i;
        } else if (i > 0) {
            return "t = " + tolerance + "; right by " + i;
        } else {
            return "t = " + tolerance + "; center";
        }
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

    protected void fillImageView(int[] newImage, int viewId) {
        Bitmap newBitmap = createBitmap(newImage);
        fillImageView(newBitmap, viewId);
    }

    protected void fillImageView(Bitmap newBitmap, int viewId) {
        ImageView image2 = (ImageView) findViewById(viewId);
        image2.setImageBitmap(newBitmap);
    }
}



