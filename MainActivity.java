package com.example.samuelhimself.agent;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {

    private ProgressBar progBar;
    private TextView text;
    private Handler mHandler = new Handler();
    private int mProgressStatus=0;

    Button Bconfirm,Bbalancing,Brevenue,Bmap,Bprofile,Bparking;

    String serverKey="2y10pN0pj28Q9WNoLrPCI3mIwtDkHhBmfpFGWshiuHxvqmzsltGQKzS";
    Button Bhom,Bmore,Blogin, textrec;
    EditText ecode,epassword;

    private static final String SURNAME_KEY ="Surname";
    private static final String FIRST_NAME_KEY ="First Name";
    private static final String AGENT_CODE_KEY ="Agent Code";
    private static final String PHONE_NUMBER_KEY ="Phone Number";
    private static final String EMAIL_ADDRESS_KEY ="Email";
    private static final String RESIDENCE_KEY ="Residence";
    private static final String LOGIN_STATUS_KEY ="Login Status";

    private SharedPreferences prefs;
    private String prefName ="preProfile";
    static JSONObject jObj = null;
    static String json = "";
    String usersurname,userfirstname,userphonenumb,useremailadd,userresidence,userduration,userpaymeth,userbikeno,usercash,message;

    Boolean loginStatus;

    String pkafrica,pkcedat,pkcomplex,pkfema,pklibrary,pklivingstone,pklumumba,pkmaingate,pkmarystuart,pkmitchell,pknkrumah,pkuh;

    String timez,day,msg;
    int suckie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//*************time*************8
        Calendar date= Calendar.getInstance();
        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
        day=sdf.format(date.getTime());


        progBar= (ProgressBar)findViewById(R.id.progressBar11);
        pogless();

//        Bconfirm= findViewById(R.id.confirm1);
//        Bconfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent int1 =new Intent(getApplicationContext(),confirmRequests.class);
//                startActivity(int1);
//            }
//        });

        textrec= findViewById(R.id.textrec);
        textrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int1 =new Intent(getApplicationContext(),textrecognition.class);
                startActivity(int1);
            }
        });

        Bbalancing= findViewById(R.id.bikebalancing1);
        Bbalancing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int1 =new Intent(getApplicationContext(),bikeBalancing.class);
                startActivity(int1);
            }
        });

//        Button bsave= findViewById(R.id.save);
//        bsave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent int1 =new Intent(getApplicationContext(),saving.class);
//                startActivity(int1);
//            }
//        });

//---------------toolbar--------------------
        Toolbar toolbar =findViewById(R.id.maintool);
        setSupportActionBar(toolbar);
    }

    public void pogless() {

        new Thread(new Runnable() {
            public void run() {
                final int presentage=0;
                while (mProgressStatus < 100) {
                    mProgressStatus += 10;
                    if(mProgressStatus==100){
                        mProgressStatus=0;
                    }
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            progBar.setProgress(mProgressStatus);
//                            text.setText(""+mProgressStatus+"%");
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.fresh_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mainmenu:
                Intent int1 =new Intent(getApplicationContext(),profile.class);
                startActivity(int1);
        }
        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed(){
        moveTaskToBack(true);
    }

    public void getRequests(View v){
        prefs=getSharedPreferences(prefName,MODE_PRIVATE);
        String Acode =prefs.getString(AGENT_CODE_KEY,"");

        ProgressBar pb =findViewById(R.id.progressBar11);
        pb.setVisibility(ProgressBar.VISIBLE);

        new MainActivity.backgroundGetreq(this).execute(Acode);
    }

    class backgroundGetreq extends AsyncTask<String, Void,String> {

        AlertDialog dialog;
        Context context;
        public backgroundGetreq(Context context){
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
                        Intent int1 =new Intent(getApplicationContext(),confirmRequests.class);
                        int1.putExtra("getInfo",s);
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

            ProgressBar pb =findViewById(R.id.progressBar11);
            pb.setVisibility(ProgressBar.INVISIBLE);
//
//            Intent int2=new Intent(MainActivity.this,confirmRequests.class);
//            int2.putExtra("getInfo",s);
//            startActivity(int2);

        }

        @Override
        protected String doInBackground(String... voids) {
            String result="";

            String acode=voids[0];
            String connstr="http://stardigitalbikes.com/get_requests_pdo.php";
//            String connstr="http://192.168.43.113/pdobikephp/get_requests_pdo.php";

            try {
                URL url =new URL(connstr);
                HttpURLConnection http =(HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);


                OutputStream ops =http.getOutputStream();
                BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("agent_code","UTF-8")+"="+URLEncoder.encode(acode,"UTF-8")
                        +"&&"+ URLEncoder.encode("serverKey","UTF-8")+"="+URLEncoder.encode(serverKey,"UTF-8")
                        +"&&"+ URLEncoder.encode("day","UTF-8")+"="+URLEncoder.encode(day,"UTF-8");

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

//                try {
//                    jObj = new JSONObject(json);
//                    if(json!=null){
//                        int success=jObj.getInt("success");
//
//                        Log.d("JSONStatus", "JSON RETURNED");
//
//                        if(success==1){
//                            JSONArray userArray=jObj.getJSONArray("user");
//                            JSONObject user=userArray.getJSONObject(0);
//                            usersurname=user.getString("SN");
//                            userfirstname=user.getString("FN");
//                            userphonenumb=user.getString("PN");
//                            useremailadd=user.getString("EM");
//                            userresidence=user.getString("RD");
//                            userduration=user.getString("DR");
//                            userpaymeth=user.getString("PM");
//                            usercash=user.getString("CS");
//                            Log.d("JSONStatus","Login success");
//
//                        }else{
//                            Log.d("JSONStatus","Login failure");
//                            message=jObj.getString("message");
//                            Log.d("JSONStatus",message);
//                        }
//                    }else{
//                        Log.e("JSON Parser", "RETURNED JSON IS NULL ");
//                    }
//                } catch (JSONException e) {
//                    Log.e("JSON Parser", "Error creating the json object " + e.toString());
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


    //RETURNRETURNRETURNRETURNRETURNRETURNRETURNRETURNRETURNRETURNRETURNRETURNRETURN
    public void getReturnRequests(View v){


        prefs=getSharedPreferences(prefName,MODE_PRIVATE);
        String Agcode =prefs.getString(AGENT_CODE_KEY,"");

        ProgressBar pb =findViewById(R.id.progressBar11);
        pb.setVisibility(ProgressBar.VISIBLE);

        new MainActivity.backgroundreturnReq(this).execute(Agcode);
    }
    class backgroundreturnReq extends AsyncTask<String, Void,String> {
        AlertDialog dialog;
        Context context;
        public backgroundreturnReq(Context context){
            this.context=context;
        }

        @Override
        protected void onPreExecute() {

//*************time*************8
            Calendar calendar= Calendar.getInstance();
            SimpleDateFormat format= new SimpleDateFormat("HH:mm");
            timez=format.format(calendar.getTime());
        }

        @Override
        protected void onPostExecute(String s) {
//            dialog.setMessage(s);
//            dialog.show();
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

            ProgressBar pb =findViewById(R.id.progressBar11);
            pb.setVisibility(ProgressBar.INVISIBLE);

            Log.d("JSON Exception","IN MAIN ACTIVITY POST EXEC METHOD");


            try {
                jObj = new JSONObject(json);
                if(json!=null){
                    suckie=jObj.getInt("success");
                    msg=jObj.getString("message");
                }else{
                    Log.e("JSON Parser", "RETURNED JSON IS NULL ");
                }
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error creating the json object " + e.toString());
//                dialog.setMessage("Please connect to the internet and then try again");
//                dialog.show();
            }

            switch (suckie) {
                case 0:
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    Intent int1111=new Intent(MainActivity.this,returnBike.class);
                    int1111.putExtra("getInform",s);
                    startActivity(int1111);

                    break;
                case 2:
                    Intent int22 =new Intent(getApplicationContext(),finesOfUsers.class);
                    int22.putExtra("fines",s);
                    startActivity(int22);
                    break;
                case 3:
                    Intent int33 = new Intent(MainActivity.this, finalReg.class);
                    int33.putExtra("gettingInfo",s);
                    startActivity(int33);
                    break;
//                case 4:
//                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
//                    Intent int444=new Intent(SPLASH1.this,Mapsimport1.class);
//                    int444.putExtra("bikesin",s);
//                    startActivity(int444);
//                    break;

            }

        }

        @Override
        protected String doInBackground(String... voids) {
            String result="";

            String acode=voids[0];
            String connstr="http://stardigitalbikes.com/get_return_requests_pdo.php";
//            String connstr="http://192.168.43.113/pdobikephp/get_return_requests_pdo.php";

            try {
                URL url =new URL(connstr);
                HttpURLConnection http =(HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);


                OutputStream ops =http.getOutputStream();
                BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("agent_code","UTF-8")+"="+URLEncoder.encode(acode,"UTF-8")
                        +"&&"+ URLEncoder.encode("timenow","UTF-8")+"="+URLEncoder.encode(timez,"UTF-8")
                        +"&&"+ URLEncoder.encode("day","UTF-8")+"="+URLEncoder.encode(day,"UTF-8")
                        +"&&"+ URLEncoder.encode("serverKey","UTF-8")+"="+URLEncoder.encode(serverKey,"UTF-8");

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

                try {
                    jObj = new JSONObject(json);
                    if(json!=null){
                        int success=jObj.getInt("success");

                        Log.d("JSONStatus", "JSON RETURNED");

                        if(success==1){
                            JSONArray userArray=jObj.getJSONArray("user");
                            JSONObject user=userArray.getJSONObject(0);
                            usersurname=user.getString("SN");
                            userfirstname=user.getString("FN");
                            userphonenumb=user.getString("PN");
                            useremailadd=user.getString("EM");
                            userresidence=user.getString("RD");
                            userbikeno=user.getString("BN");
                            Log.d("JSONStatus","got requests");

                        }else{
                            Log.d("JSONStatus","get requests failure");
                            message=jObj.getString("message");
                            Log.d("JSONStatus",message);
                        }
                    }else{
                        Log.e("JSON Parser", "RETURNED JSON IS NULL ");
                    }
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error creating the json object " + e.toString());
                }
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

    public void getBikesIn(View v){

        ProgressBar pb =findViewById(R.id.progressBar11);
        pb.setVisibility(ProgressBar.VISIBLE);

        new MainActivity.backgroundBikesIn(this).execute();
    }

    class backgroundBikesIn extends AsyncTask<String, Void,String> {

        AlertDialog dialog;
        Context context;
        public backgroundBikesIn(Context context){
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
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

            ProgressBar pb =findViewById(R.id.progressBar11);
            pb.setVisibility(ProgressBar.INVISIBLE);
            Intent int2=new Intent(MainActivity.this,bicyclesIn.class);
            int2.putExtra("bikesin",s);
            startActivity(int2);

        }

        @Override
        protected String doInBackground(String... voids) {
            String result="";

//            String acode=voids[0];
            String connstr="http://stardigitalbikes.com/bicycles_in.php";
//            String connstr="http://192.168.43.113/pdobikephp/bicycles_in.php";


            try {
                URL url =new URL(connstr);
                HttpURLConnection http =(HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops =http.getOutputStream();
                BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("serverKey","UTF-8")+"="+URLEncoder.encode(serverKey,"UTF-8");

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

                try {
                    jObj = new JSONObject(json);
                    if(json!=null){
                        int success=jObj.getInt("success");

                        Log.d("JSONStatus", "JSON RETURNED");

                        if(success==1){
                            JSONArray userArray=jObj.getJSONArray("user");
                            JSONObject user=userArray.getJSONObject(0);
                            pkafrica=user.getString("AF");
                            pkcedat=user.getString("CD");
                            pkcomplex=user.getString("IT");
                            pkfema=user.getString("FM");
                            pklibrary=user.getString("LB");
                            pklivingstone=user.getString("LV");
                            pklumumba=user.getString("LM");
                            pkmaingate=user.getString("MG");
                            pkmarystuart=user.getString("MS");
                            pkmitchell=user.getString("MT");
                            pknkrumah=user.getString("NK");
                            pkuh=user.getString("UH");
                            Log.d("JSONStatus","Login success");

                        }else{
                            Log.d("JSONStatus","Login failure");
                            message=jObj.getString("message");
                            Log.d("JSONStatus",message);
                        }
                    }else{
                        Log.e("JSON Parser", "RETURNED JSON IS NULL ");
                    }
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error creating the json object " + e.toString());
                }
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


    public void myRevenue(View v){

        prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        String codeyo=prefs.getString(AGENT_CODE_KEY,"");

        ProgressBar pb =findViewById(R.id.progressBar11);
        pb.setVisibility(ProgressBar.VISIBLE);

        new MainActivity.backgroundRevenue(this).execute(codeyo);
    }

    class backgroundRevenue extends AsyncTask<String, Void,String> {

        AlertDialog dialog;
        Context context;
        public backgroundRevenue(Context context){
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
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

            ProgressBar pb =findViewById(R.id.progressBar11);
            pb.setVisibility(ProgressBar.INVISIBLE);

            Intent int2=new Intent(MainActivity.this,revenue.class);
            int2.putExtra("rev",s);
            startActivity(int2);

        }

        @Override
        protected String doInBackground(String... voids) {
            String result="";

            String acode=voids[0];
            String connstr="http://stardigitalbikes.com/revenue.php";
//            String connstr="http://192.168.43.113/pdobikephp/revenue.php";

            try {
                URL url =new URL(connstr);
                HttpURLConnection http =(HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops =http.getOutputStream();
                BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("agent_code","UTF-8")+"="+URLEncoder.encode(acode,"UTF-8")
                        +"&&"+ URLEncoder.encode("serverKey","UTF-8")+"="+URLEncoder.encode(serverKey,"UTF-8");

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

                try {
                    jObj = new JSONObject(json);
                    if(json!=null){
                        int success=jObj.getInt("success");

                        Log.d("JSONStatus", "JSON RETURNED");

                        if(success==1){
                            Log.d("JSONStatus","Login success");

                        }else{
                            Log.d("JSONStatus","Login failure");
                            message=jObj.getString("message");
                            Log.d("JSONStatus",message);
                        }
                    }else{
                        Log.e("JSON Parser", "RETURNED JSON IS NULL ");
                    }
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error creating the json object " + e.toString());
                }
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

    public void myBikesout(View v){

        prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        String codeyo=prefs.getString(AGENT_CODE_KEY,"");

        ProgressBar pb =findViewById(R.id.progressBar11);
        pb.setVisibility(ProgressBar.VISIBLE);

        new MainActivity.backgroundbikesOut(this).execute(codeyo);
    }

    class backgroundbikesOut extends AsyncTask<String, Void,String> {

        AlertDialog dialog;
        Context context;
        public backgroundbikesOut(Context context){
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
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

            ProgressBar pb =findViewById(R.id.progressBar11);
            pb.setVisibility(ProgressBar.INVISIBLE);
            Log.d("JSONStatus", s);
            Intent int3=new Intent(MainActivity.this,bicyclesOut.class);
            int3.putExtra("bikesout",s);
            startActivity(int3);

        }

        @Override
        protected String doInBackground(String... voids) {
            String result="";

            String acode=voids[0];
            String connstr="http://stardigitalbikes.com/bicycles_out.php";
//            String connstr="http://192.168.43.113/pdobikephp/bicycles_out.php";

            try {
                URL url =new URL(connstr);
                HttpURLConnection http =(HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops =http.getOutputStream();
                BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("agent_code","UTF-8")+"="+URLEncoder.encode(acode,"UTF-8")
                        +"&&"+ URLEncoder.encode("serverKey","UTF-8")+"="+URLEncoder.encode(serverKey,"UTF-8");

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

                try {
                    jObj = new JSONObject(json);
                    if(json!=null){
                        int success=jObj.getInt("success");

                        Log.d("JSONStatus", "JSON RETURNED");

                        if(success==1){
                            Log.d("JSONStatus","Login success");
                        }else{
                            Log.d("JSONStatus","Login failure");
                            message=jObj.getString("message");
                            Log.d("JSONStatus",message);
                        }
                    }else{
                        Log.e("JSON Parser", "RETURNED JSON IS NULL ");
                    }
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error creating the json object " + e.toString());
                }
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

    public void getfineRequests(View v){


        prefs=getSharedPreferences(prefName,MODE_PRIVATE);
        String Agcode =prefs.getString(AGENT_CODE_KEY,"");

        ProgressBar pb =findViewById(R.id.progressBar11);
        pb.setVisibility(ProgressBar.VISIBLE);

        new MainActivity.backgroundfineReq(this).execute(Agcode);
    }
    class backgroundfineReq extends AsyncTask<String, Void,String> {
        AlertDialog dialog;
        Context context;
        public backgroundfineReq(Context context){
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
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

            ProgressBar pb =findViewById(R.id.progressBar11);
            pb.setVisibility(ProgressBar.INVISIBLE);

            Log.d("JSON Exception","IN MAIN ACTIVITY POST EXEC METHOD");


            try {
                jObj = new JSONObject(json);
                if(json!=null){
                    suckie=jObj.getInt("success");
                    msg=jObj.getString("message");
                }else{
                    Log.e("JSON Parser", "RETURNED JSON IS NULL ");
                }
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error creating the json object " + e.toString());
//                dialog.setMessage("Please connect to the internet and then try again");
//                dialog.show();
            }

            switch (suckie) {
                case 0:
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    Intent int1111=new Intent(MainActivity.this,finesOfUsers.class);
                    int1111.putExtra("fines",s);
                    startActivity(int1111);

                    break;
//                case 2:
//                    Intent int22 =new Intent(getApplicationContext(),finesOfUsers.class);
//                    int22.putExtra("fines",s);
//                    startActivity(int22);
//                    break;
//                case 3:
//                    Intent int33 = new Intent(MainActivity.this, finalReg.class);
//                    int33.putExtra("gettingInfo",s);
//                    startActivity(int33);
//                    break;
//                case 4:
//                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
//                    Intent int444=new Intent(SPLASH1.this,Mapsimport1.class);
//                    int444.putExtra("bikesin",s);
//                    startActivity(int444);
//                    break;

            }

        }

        @Override
        protected String doInBackground(String... voids) {
            String result="";

            String acode=voids[0];
            String connstr="http://stardigitalbikes.com/fines_get.php";
//            String connstr="http://192.168.43.113/pdobikephp/fines_get.php";

            try {
                URL url =new URL(connstr);
                HttpURLConnection http =(HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);


                OutputStream ops =http.getOutputStream();
                BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("agent_code","UTF-8")+"="+URLEncoder.encode(acode,"UTF-8");

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

    public void mymessages(View v){
                Intent int1 =new Intent(getApplicationContext(),messages.class);
                startActivity(int1);
    }



}
