package com.example.samuelhimself.agent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class finalReg extends AppCompatActivity implements View.OnClickListener{

    private ProgressBar progBar;
    private TextView text;
    private Handler mHandler = new Handler();
    private int mProgressStatus=0;

    String serverKey="2y10pN0pj28Q9WNoLrPCI3mIwtDkHhBmfpFGWshiuHxvqmzsltGQKzS";
    private static final String AGENT_CODE_KEY ="Agent Code";
    static String json = "";
    static JSONObject jObj = null;
    boolean confirm=false;

    private SharedPreferences prefs;
    private String prefName ="preProfile";
    String mesos,respo;
    int succelence;
    int regsuccess;

    String msg,namey,filename;
    String timez;


    String usersurname,userfirstname,userphonenumb,useremailadd,userresidence,usercash,userduration,userpaymeth,message,userreg;
    Boolean loginStatus;

    TextView tname,tphone,tmail,tresi,tdur,tpaym,tgear,tcash,treg;

    ImageView imageToUpload;
//    Bitmap image;

    private static final String SURNAME_KEY ="Surname";
    private static final String FIRST_NAME_KEY ="First Name";
    private static final String PHONE_NUMBER_KEY ="Phone Number";
    private static final String EMAIL_ADDRESS_KEY ="Email";
    private static final String RESIDENCE_KEY ="Residence";
    private static final String LOGIN_STATUS_KEY ="Login Status";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_reg);

        Bundle extras=getIntent().getExtras();
        mesos=extras.getString("gettingInfo");

//        Toast.makeText(getApplicationContext(), meso, Toast.LENGTH_LONG).show();


        //       TAKING PHOTO %%%%%%%%%%%%%%
        Button cameroo=(Button)findViewById(R.id.camera1);
        imageToUpload=(ImageView)findViewById(R.id.imageToUpload);


        cameroo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
        Button upload=(Button)findViewById(R.id.upload1);

        imageToUpload.setOnClickListener(this);
        upload.setOnClickListener(this);


        try {
            jObj = new JSONObject(mesos);
            JSONArray userArray=jObj.getJSONArray("user");
            JSONObject user=userArray.getJSONObject(0);
            usersurname=user.getString("SN");
            userfirstname=user.getString("FN");
            userphonenumb=user.getString("PN");
            useremailadd=user.getString("EM");
            userresidence=user.getString("RD");
            userduration=user.getString("DR");
            usercash=user.getString("CS");
//            usergear=user.getString("GR");
            userpaymeth=user.getString("PM");
            userreg=user.getString("RG");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        filename=usersurname+"-"+userfirstname+"-"+userphonenumb;

        TextView finalreguser=findViewById(R.id.userfinalreg);
        finalreguser.setText(usersurname+" "+userfirstname+" "+userphonenumb+" "+userresidence);
    }

    @Override
    public void onClick(View v) {
        String totname="7777namuwayajovia";
        switch (v.getId()){
            case R.id.upload1:
                        Bitmap image=((BitmapDrawable)imageToUpload.getDrawable()).getBitmap();
//                        new uploadimage(image,namey);
                new finalReg.uploadimage(image,namey).execute();
                Toast.makeText(getApplicationContext(),"in the onclick methid",Toast.LENGTH_SHORT).show();
                break;
            case R.id.download1:
                new finalReg.downloadimage(totname).execute();
                Toast.makeText(getApplicationContext(),"in the onclick methid",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap=(Bitmap)data.getExtras().get("data");
        imageToUpload.setImageBitmap(bitmap);
    }

    public void confirmFinalReq(View view) {
//        new finalReg.backgroundfinalreg(this).execute();
        String totname="7777namuwayajovia";

        new finalReg.downloadimage(totname).execute();
        Toast.makeText(getApplicationContext(),"in the onclick methid",Toast.LENGTH_SHORT).show();

    }

    class backgroundfinalreg extends AsyncTask<String, Void,String> {

        AlertDialog dialog;
        Context context;
        public backgroundfinalreg(Context context){
            this.context=context;
        }

        @Override
        protected void onPreExecute() {
//            dialog= new AlertDialog.Builder(context).create();
//            dialog.setTitle("login status");
        }

        @Override
        protected void onPostExecute(String s) {
//            dialog.setMessage(s);
//            dialog.show();
            json = s.toString();

            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

            try {
                jObj = new JSONObject(json);
                int  success = jObj.getInt("success");

                switch (success){
                    case 0:
                        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Intent int1 =new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(int1);
                        break;
                    case 2:
                        Intent int2 =new Intent(getApplicationContext(),finesOfUsers.class);
                        startActivity(int2);
                        break;
                    case 3:
                        Intent int3 =new Intent(getApplicationContext(),finalReg.class);
                        int3.putExtra("gettingInfo",s);
                        startActivity(int3);
                        break;

                }
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error creating the json object " + e.toString());
            }

//            try {
//                jObj = new JSONObject(s);
//                msg=jObj.getString("message");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            if(regsuccess==1) {
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
//                Intent int2 = new Intent(finalReg.this, MainActivity.class);
//                startActivity(int2);
//            }else {
//                Toast.makeText(getApplicationContext(), "Did no fully register user, please try again", Toast.LENGTH_LONG).show();
//            }
        }

        @Override
        protected String doInBackground(String... voids) {
            String result="";

            String connstr="http://stardigitalbikes.com/final_registration.php";
//            String connstr="http://192.168.43.189/bikephp/final_registration.php";


            prefs = getSharedPreferences(prefName, MODE_PRIVATE);
            String siname=prefs.getString(SURNAME_KEY,"");
            String finame=prefs.getString(FIRST_NAME_KEY,"");
            String aco=prefs.getString(AGENT_CODE_KEY,"");
            String phone=prefs.getString(PHONE_NUMBER_KEY,"");

            try {
                URL url =new URL(connstr);
                HttpURLConnection http =(HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);


                OutputStream ops =http.getOutputStream();
                BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("serverKey","UTF-8")+"="+URLEncoder.encode(serverKey,"UTF-8")
                        +"&&"+ URLEncoder.encode("phonenumber","UTF-8")+"="+URLEncoder.encode(userphonenumb,"UTF-8")
                        +"&&"+ URLEncoder.encode("agentsurname","UTF-8")+"="+URLEncoder.encode(userphonenumb,"UTF-8")
                        +"&&"+ URLEncoder.encode("agentfirstname","UTF-8")+"="+URLEncoder.encode(userphonenumb,"UTF-8")
                        +"&&"+ URLEncoder.encode("agentcode","UTF-8")+"="+URLEncoder.encode(userphonenumb,"UTF-8")
                        +"&&"+ URLEncoder.encode("agentphonenumber","UTF-8")+"="+URLEncoder.encode(userphonenumb,"UTF-8");

                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();
                Log.d("JSON Exception","DONE SENDING");

                InputStream ips =http.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
                String line ="";
                while ((line=reader.readLine()) !=null){
                    result +=line;

                }
//#######INTRODICING THE READING OOF THE RETURNED JSON
                ips.close();
                reader.close();
//##################################################################33
                http.disconnect();
                return result;

            } catch (MalformedURLException e) {
                Log.d("JSON Exception",e.toString());
                result =e.getMessage();
            } catch (ProtocolException e) {
                Log.d("JSON Exception",e.toString());
                result =e.getMessage();
            } catch (IOException e) {
                Log.d("JSON Exception",e.toString());
                result =e.getMessage();
            }
            return result;
        }
    }

    class uploadimage extends AsyncTask<String, Void,String> {
        AlertDialog dialog;
        Context context;
        Bitmap image;
        String name;
        public uploadimage(Bitmap image,String name){
            this.image=image;
            this.name=name;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
            json = s.toString();
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

            try {
                jObj = new JSONObject(json);
                int  successs = jObj.getInt("success");

                switch (successs){
                    case 1:
                        Intent int2 =new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(int2);
                        break;
                }
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error " + e.toString()+"post execute");
            }

        }

        @Override
        protected String doInBackground(String... voids) {
            String result="";
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);
            String encodedImage=Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

            String connstr="http://stardigitalbikes.com/final_registration_pdo.php";
//            String connstr="http://192.168.43.113/pdobikephp/final_registration_pdo.php";
            Log.e("JSON Parser", encodedImage);
//            Toast.makeText(getApplicationContext(),encodedImage,Toast.LENGTH_LONG).show();


            prefs = getSharedPreferences(prefName, MODE_PRIVATE);
            String siname=prefs.getString(SURNAME_KEY,"");
            String finame=prefs.getString(FIRST_NAME_KEY,"");
            String aco=prefs.getString(AGENT_CODE_KEY,"");

            try {
                URL url =new URL(connstr);
                HttpURLConnection http =(HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                Log.e("JSON Parser", "sending staff");

                OutputStream ops =http.getOutputStream();
                BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(filename,"UTF-8")
                        +"&&"+ URLEncoder.encode("image","UTF-8")+"="+URLEncoder.encode(encodedImage,"UTF-8")
                        +"&&"+ URLEncoder.encode("phonenumber","UTF-8")+"="+URLEncoder.encode(userphonenumb,"UTF-8")
                        +"&&"+ URLEncoder.encode("agentcode","UTF-8")+"="+URLEncoder.encode(aco,"UTF-8");

                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();
                Log.d("JSON Exception","DONE SENDING");

                InputStream ips =http.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
                String line ="";
                while ((line=reader.readLine()) !=null){
                    result +=line;

                }
//#######INTRODICING THE READING OOF THE RETURNED JSON
                ips.close();
                reader.close();
                json = result.toString();
                Log.d("JSON ",json+"JSON RETURNED");
//                try {
//                    jObj = new JSONObject(json);
//                    if(json!=null){
//                        Log.d("JSONStatus", "JSON RETURNED");
//                    }else{
//                        Log.e("JSON Parser", "RETURNED JSON IS NULL ");
//                        message=jObj.getString("message");
//                    }
//                } catch (JSONException e) {
//                    Log.e("JSON Parser", "Error " + e.toString()+"after return");
//                }
//##################################################################33
                http.disconnect();
                return result;

            } catch (MalformedURLException e) {
                Log.d("JSON Exception",e.toString());
                result =e.getMessage();
            } catch (ProtocolException e) {
                Log.d("JSON Exception",e.toString());
                result =e.getMessage();
            } catch (IOException e) {
                Log.d("JSON Exception",e.toString());
                result =e.getMessage();
            }

            return result;
        }


    }

    class downloadimage extends AsyncTask<Void, Void,Bitmap> {
        Bitmap image;
        String name;
        public downloadimage(String name){
            this.name=name;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
          super.onPostExecute(bitmap);

          if(bitmap!=null){
              imageToUpload.setImageBitmap(bitmap);
          }
        }

        @Override
        protected  Bitmap doInBackground(Void... params) {

            String result="",SERVER_ADDRESS="http://stardigitalbikes.com/";
//            String result="",SERVER_ADDRESS="http://192.168.43.113/pdobikephp/";
            String url = SERVER_ADDRESS+"pictures/"+name+".JPG";

            try {
                URLConnection connection= new URL(url).openConnection();
                connection.setConnectTimeout(1000*30);
                connection.setReadTimeout(1000*30);

                return BitmapFactory.decodeStream((InputStream) connection.getContent(),null,null);
            } catch (Exception e) {
                Log.d("JSON Exception",e.toString());
                result =e.getMessage();
                return null;
            }

        }


    }

}
