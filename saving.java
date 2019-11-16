package com.example.samuelhimself.agent;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import static java.lang.System.out;

public class saving extends AppCompatActivity {
    ImageView image;
    String picname="first",picext="JPG",pathreal;
    Bitmap bit;
    OutputStream output;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving);

        //       TAKING PHOTO %%%%%%%%%%%%%%
        Button cameroo=(Button)findViewById(R.id.cams);
        image=(ImageView)findViewById(R.id.imageTosave);


        cameroo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });

//        SAVING
//        Button sav=findViewById(R.id.save);
//        sav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                image.buildDrawingCache();
//                Bitmap bmap=image.getDrawingCache();
//                saveImage(getApplicationContext(),bmap,picname,picext);
//            }
//        });

    }

    public void savethis(View view){
        image.buildDrawingCache();
        Bitmap bmap=image.getDrawingCache();
//        saveImage(getApplicationContext(),bmap,picname,picext);
//        createDirectoryAndSaveFile(bmap,picname);
//        saveImg2();
        saveToInternalStorage(bmap);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap=(Bitmap)data.getExtras().get("data");
        image.setImageBitmap(bitmap);
    }

    public void saveImage(Context context, Bitmap bitmap, String name, String extension){
        name = name + "." ;//+ extension;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),"Done saving!!! "+ name+extension,Toast.LENGTH_SHORT).show();
        Log.e("JSON Parser", "name+extension " + name+extension);


    }

    public void loadthis(View view){
//        loadImageBitmap(this,picname,picext);
        String pathis="/data/data/DB Agent/app_data/imageDir";
//        loadImageFromStorage(pathreal);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        pathreal=directory.getAbsolutePath();
        Toast.makeText(getApplicationContext(),pathreal,Toast.LENGTH_SHORT).show();

        directory.mkdirs();

    }

    public Bitmap loadImageBitmap(Context context,String name,String extension){
        name = name + ".";// + extension;
        FileInputStream fileInputStream;
        Bitmap bitmap = null;
        try{
            fileInputStream = context.openFileInput(name);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),"Done loading!!! "+ name+extension,Toast.LENGTH_SHORT).show();
        Log.e("JSON Parser", "name+extension " + name+extension);


        image.setImageBitmap(bitmap);

        return bitmap;
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

//        File direct = new File(Environment.getExternalStorageDirectory() + "/DirName");

//        if (!direct.exists()) {
            File wallpaperDirectory = new File("/storage/emulated/0/apks/");
            wallpaperDirectory.mkdirs();
//        }

        File file = new File(new File("/storage/emulated/0/apks/"), fileName);
//        if (file.exists()) {
//            file.delete();
//        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveImg(){
//        Bitmap bmap=image.getDrawingCache();
//        bit=BitmapFactory.decodeResource(getResources(),R.drawable.ic_profile);
//        File filepath=Environment.getExternalStorageDirectory();
//
//        File dir=new File(filepath.getAbsolutePath()+"/saveExample");
//        dir.mkdirs();
//
//        File file=new File(dir,"myimage.png");
//        Toast.makeText(getApplicationContext(),"image saved",Toast.LENGTH_SHORT).show();
//
//        try {
//            output=new FileOutputStream(file);
//            bit.compress(Bitmap.CompressFormat.PNG,100,output);
//            output.flush();
//            output.close();
//        }catch (Exception e){
//
//        }
//
        Bitmap bmap=image.getDrawingCache();
        bit=BitmapFactory.decodeResource(getResources(),R.drawable.ic_profile);
        File filepath=Environment.getExternalStorageDirectory();

        File dir=new File(filepath.getAbsolutePath()+"/saveExample");
        dir.mkdirs();

        File file=new File(dir,"myimage.png");
        Toast.makeText(getApplicationContext(),"image saved",Toast.LENGTH_SHORT).show();

        try {
            output=new FileOutputStream(file);
            bmap.compress(Bitmap.CompressFormat.PNG,100,output);
            output.flush();
            output.close();
        }catch (Exception e){

        }


    }

//    public void saveImg2() {
//        FileOutputStream fOut =
//                openFileOutput(“textfile.txt”,
//                        MODE_WORLD_READABLE);
//        OutputStreamWriter osw = new
//                OutputStreamWriter(fOut);
////---write the string to the file---
//        osw.write(str);
//        osw.flush();
//        osw.close();
////---display file saved message---
//        Toast.makeText(getBaseContext(),
//                “File saved successfully!”,
//        Toast.LENGTH_SHORT).show();
////---clears the EditText---
//        textBox.setText(“”);
//    }


    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        directory.mkdirs();
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Log.e("SAVE IMAGE",bitmapImage.toString());
                    Toast.makeText(getApplicationContext(),bitmapImage.toString(),Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SAVE IMAGE",e.toString());
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("SAVE IMAGE",e.toString());
            }
        }
        pathreal=getApplicationInfo().dataDir;

        return directory.getAbsolutePath();
    }


    private void loadImageFromStorage(String path)
    {

        try {
            Toast.makeText(getApplicationContext(),pathreal,Toast.LENGTH_SHORT).show();
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            image.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

}
