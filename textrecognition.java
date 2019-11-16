package com.example.samuelhimself.agent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class textrecognition extends AppCompatActivity {

    Button camerabtn, detect;
    int determinant = 0;
    ImageView imageread;
    Bitmap bitmapimg;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textrecognition);

        textView=findViewById(R.id.bikenumber);
        detect=findViewById(R.id.detect);
        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectImg();
            }
        });
        imageread = (ImageView) findViewById(R.id.numberimage);
        camerabtn = findViewById(R.id.cameraconf);
        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (determinant == 0) {
                    determinant++;
                    camerabtn.setText("Give Bike");
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                } else {

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmapimg = (Bitmap) data.getExtras().get("data");
        imageread.setImageBitmap(bitmapimg);
        detectImg();
    }

    private void detectImg() {
        Log.w("txt", "detecting");
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmapimg);
        FirebaseVisionTextRecognizer textRecognizer =
                FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        textRecognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                Log.w("txt", "success");
                processTxt(firebaseVisionText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("txt", "failed");

            }
        });
    }
    private void processTxt (FirebaseVisionText text){
        Log.w("txt", text.getText());
        textView.setText(text.getText());
//        runSystems(text.getText());
    }

}