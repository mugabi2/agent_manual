package com.example.samuelhimself.agent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
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

public class confirmRequests extends AppCompatActivity {

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
    String meso,respo;
    int succelence;
    int regsuccess;
    int determinant=0;

    String msg;
    String timez;


    String usersurname,userfirstname,userphonenumb,useremailadd,userresidence,usercash,userduration,userpaymeth,message,userreg,userduraInt;
    Boolean loginStatus;

    TextView tname,tphone,tmail,tresi,tdur,tpaym,tgear,tcash,treg,bikenumbtxt;
    Button camerabtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_requests);


//        Toast.makeText(getApplicationContext(), timez, Toast.LENGTH_LONG).show();

        progBar= (ProgressBar)findViewById(R.id.progressBar22);
        pogless();

//        camerabtn=findViewById(R.id.cameraconf);
//        camerabtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(determinant==0){
//                    determinant++;
//                    camerabtn.setText("Give Bike");
//                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent,0);
//                }else{
//
//                }
//            }
//        });
        //---------------toolbar--------------------
        Toolbar toolbar =findViewById(R.id.confirmtool);
        setSupportActionBar(toolbar);

        Bundle extras=getIntent().getExtras();
        meso=extras.getString("getInfo");

        Toast.makeText(getApplicationContext(), meso, Toast.LENGTH_LONG).show();

        try {
            jObj = new JSONObject(meso);
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
            userduraInt=user.getString("DRI");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        tname=findViewById(R.id.texname1);
        tphone=findViewById(R.id.texpnumber1);
        tmail=findViewById(R.id.texmail1);
        tresi=findViewById(R.id.texresidence1);
        tdur=findViewById(R.id.texdura1);
        tpaym=findViewById(R.id.texpaymeth1);
//        tgear=findViewById(R.id.texgear1);
        tcash=findViewById(R.id.texcash1);
        treg=findViewById(R.id.texreg1);

        tname.append(usersurname+" "+userfirstname);
        tphone.setText(userphonenumb);
        tmail.setText(useremailadd);
        tresi.setText(userresidence);
        tdur.setText(userduration);
        tpaym.setText(userpaymeth);
//        tgear.setText(usergear);
        tcash.setText(usercash);
        treg.setText(userreg);

        try {
            succelence=jObj.getInt("success");

            respo=jObj.getString("message");

            if(succelence==0) {
//                Toast.makeText(getApplicationContext(), respo, Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mainmenu:
                Intent int1 =new Intent(getApplicationContext(),profile.class);
                startActivity(int1);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void confirmReq(View view) {
        prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        String Acode = prefs.getString(AGENT_CODE_KEY, "");

        EditText Ebikeno = (EditText) findViewById(R.id.TextBikeNo);
        String GivenBikeNo = Ebikeno.getText().toString();

        //Checking if all fields have been filled
// check if fully registred and do need full

//        String regtrue = "true";
//        if (regtrue.equals(userreg)){

            if (!GivenBikeNo.isEmpty()) {

                ProgressBar pb =findViewById(R.id.progressBar22);
                pb.setVisibility(ProgressBar.VISIBLE);

                new confirmRequests.backgroundConfirm(this).execute(Acode, GivenBikeNo);
                Log.d("Request status", "GOOD INPUT am gonna make the request");
                Toast.makeText(getApplicationContext(), "confirming..................", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Please fill in the bike number", Toast.LENGTH_LONG).show();
            }
//    }else {
//            new confirmRequests.backgroundfinalreg(this).execute();
//        }
    }
    //##################BACK GROUND CLASSS$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$444444444
    class backgroundConfirm extends AsyncTask<String, Void,String> {
        AlertDialog dialog;
        Context context;
        public backgroundConfirm(Context context){
            this.context=context;
        }

        @Override
        protected void onPreExecute() {
                dialog= new AlertDialog.Builder(context).create();
                   dialog.setTitle("Bike request status");
        }

        @Override
        protected void onPostExecute(String s) {

            ProgressBar pb =findViewById(R.id.progressBar22);
            pb.setVisibility(ProgressBar.INVISIBLE);

            json = s.toString();

//            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

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

                }
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error creating the json object " + e.toString());
            }

//      new registration().savingToSharedPrefs(usersurname,userfirstname,userphonenumb,useremailadd,userresidence,usergender,loginStatus);
//            if (confirm){
////                dialog.setMessage("CONFIRMED");
////                dialog.show();
//                Intent intent= new Intent(confirmRequests.this,MainActivity.class);
//                startActivity(intent);
//            }
//            else {
//                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
//            }
        }

        @Override
        protected String doInBackground(String... voids) {
            String result="";
            String  AgCode=voids[0];
            String  Bike= voids[1];

            String connstr="http://stardigitalbikes.com/confirm_request_pdo.php";
//            String connstr="http://192.168.43.113/pdobikephp/confirm_request_pdo.php";

            //*************time*************8
            Calendar calendar= Calendar.getInstance();
            SimpleDateFormat format= new SimpleDateFormat("HH:mm");
            timez=format.format(calendar.getTime());


            try {
                URL url =new URL(connstr);
                HttpURLConnection http =(HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);


                OutputStream ops =http.getOutputStream();
                BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("agent_code","UTF-8")+"="+URLEncoder.encode(AgCode,"UTF-8")
                        +"&&"+ URLEncoder.encode("bikenumber","UTF-8")+"="+URLEncoder.encode(Bike,"UTF-8")
                        +"&&"+ URLEncoder.encode("phonenumber","UTF-8")+"="+URLEncoder.encode(userphonenumb,"UTF-8")
                        +"&&"+ URLEncoder.encode("timenow","UTF-8")+"="+URLEncoder.encode(timez,"UTF-8")
                        +"&&"+ URLEncoder.encode("duration","UTF-8")+"="+URLEncoder.encode(userduration,"UTF-8")
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

//                try {
//                    jObj = new JSONObject(json);
//                    if(json!=null){
//                        int success=jObj.getInt("success");
//
//                        Log.d("JSONStatus", "JSON RETURNED");
//
//                        if(success==1){
//                            confirm=true;
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

            try {
                jObj = new JSONObject(s);
                 msg=jObj.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(regsuccess==1) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                Intent int2 = new Intent(confirmRequests.this, MainActivity.class);
                startActivity(int2);
            }else {
                Toast.makeText(getApplicationContext(), "Did no fully register user, please try again", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... voids) {
            String result="";

            String connstr="http://stardigitalbikes.com/final_registration.php";
//            String connstr="http://192.168.43.189/bikephp/final_registration.php";

            try {
                URL url =new URL(connstr);
                HttpURLConnection http =(HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);


                OutputStream ops =http.getOutputStream();
                BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("serverKey","UTF-8")+"="+URLEncoder.encode(serverKey,"UTF-8")
                        +"&&"+ URLEncoder.encode("phonenumber","UTF-8")+"="+URLEncoder.encode(userphonenumb,"UTF-8");

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
                            regsuccess=1;
                            Log.d("JSONStatus"," final registration success");
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
}