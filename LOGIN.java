package com.example.samuelhimself.agent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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

public class LOGIN extends AppCompatActivity {

    private ProgressBar progBar;
    private TextView text;
    private Handler mHandler = new Handler();
    private int mProgressStatus=0;


    String serverKey="2y10pN0pj28Q9WNoLrPCI3mIwtDkHhBmfpFGWshiuHxvqmzsltGQKzS";
    Button Bhom,Bmore,Blogin;
    EditText ecode,epassword;

    private static final String SURNAME_KEY ="Surname";
    private static final String FIRST_NAME_KEY ="First Name";
    private static final String AGENT_CODE_KEY ="Agent Code";
    private static final String PHONE_NUMBER_KEY ="Phone Number";
    private static final String EMAIL_ADDRESS_KEY ="Email";
    private static final String RESIDENCE_KEY ="Residence";
    private static final String LOGIN_STATUS_KEY ="Login Status";

    private SharedPreferences prefs,prefl;
    private String prefName ="preProfile";
    private String preflogin="preflogin";
    static JSONObject jObj = null;
    static String json = "";
    String usersurname,userfirstname,userphonenumb,useremailadd,userresidence,usercode,message;
    Boolean loginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progBar= (ProgressBar)findViewById(R.id.progressBar66);
        pogless();


        ecode=(EditText)findViewById(R.id.input_code);
        epassword=(EditText)findViewById(R.id.input_password);


//        prefs=getSharedPreferences(prefName, MODE_PRIVATE);
//        SharedPreferences.Editor editor=prefs.edit();
//        editor.putBoolean(LOGIN_STATUS_KEY,false);

//        Intent inte=new Intent(Intent.ACTION_MAIN);
//        inte.addCategory(Intent.CATEGORY_HOME);
//        startActivity(inte);
//        finish();
////        System.exit(5);
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

    public void onBackPressed(){
        moveTaskToBack(true);
    }

    public void logMeIn(View v){

        ProgressBar pb =findViewById(R.id.progressBar66);
        pb.setVisibility(ProgressBar.VISIBLE);

        String code =ecode.getText().toString();
        String psword =epassword.getText().toString();

        new LOGIN.backgroundlogin(this).execute(code,psword);

    }
    //##################BACK GROUND CLASSS$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$444444444
    class backgroundlogin extends AsyncTask<String, Void,String> {

        AlertDialog dialog;
        Context context;
        public backgroundlogin(Context context){
            this.context=context;
        }

        @Override
        protected void onPreExecute() {
            dialog= new AlertDialog.Builder(context).create();
            dialog.setTitle("login status");
        }

        @Override
        protected void onPostExecute(String s) {
//            dialog.setMessage(s);
//                    dialog.show();
//      new registration().savingToSharedPrefs(usersurname,userfirstname,userphonenumb,useremailadd,userresidence,usergender,loginStatus);

            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

            ProgressBar pb =findViewById(R.id.progressBar66);
            pb.setVisibility(ProgressBar.INVISIBLE);


            prefl=getSharedPreferences(preflogin, MODE_PRIVATE);

            if(prefl.getBoolean(LOGIN_STATUS_KEY,true)){
                Intent int1 =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(int1);
                finish();
            }
            else {

//                Toast.makeText(getApplicationContext(),"Please input correct credentials",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... voids) {
            String result="";

            String acode=voids[0];
            String pass=voids[1];

            String connstr="http://stardigitalbikes.com/agent_login_pdo.php";
//            String connstr="http://192.168.43.113/pdobikephp/agent_login_pdo.php";

            try {
                URL url =new URL(connstr);
                HttpURLConnection http =(HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);


                OutputStream ops =http.getOutputStream();
                BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("agent_code","UTF-8")+"="+URLEncoder.encode(acode,"UTF-8")
                        +"&&"+ URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8")
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
                            usercode=user.getString("AC");
                            userphonenumb=user.getString("PN");
                            useremailadd=user.getString("EM");
                            userresidence=user.getString("RD");

                            prefl=getSharedPreferences(preflogin,MODE_PRIVATE);
                            SharedPreferences.Editor editor=prefl.edit();
                            loginStatus=Boolean.TRUE;
                            editor.putBoolean(LOGIN_STATUS_KEY,loginStatus);
                            editor.commit();
                            Log.d("JSONStatus","Login success");

                            savingToSharedPrefs(usersurname,userfirstname,usercode,userphonenumb,useremailadd,userresidence);

                        }else{
                            prefl=getSharedPreferences(preflogin,MODE_PRIVATE);
                            SharedPreferences.Editor editor=prefl.edit();
                            loginStatus=Boolean.FALSE;
                            editor.putBoolean(LOGIN_STATUS_KEY,loginStatus);
                            editor.commit();
                            Log.d("JSONStatus","Login failure");
                            message=jObj.getString("message");
                            Log.d("JSONStatus",message);
                        }
                    }else{
                        prefl=getSharedPreferences(preflogin,MODE_PRIVATE);
                        SharedPreferences.Editor editor=prefl.edit();
                        loginStatus=Boolean.FALSE;
                        editor.putBoolean(LOGIN_STATUS_KEY,loginStatus);
                        editor.commit();
                        Log.e("JSON Parser", "RETURNED JSON IS NULL ");
                    }
                } catch (JSONException e) {
                    prefl=getSharedPreferences(preflogin,MODE_PRIVATE);
                    SharedPreferences.Editor editor=prefl.edit();
                    loginStatus=Boolean.FALSE;
                    editor.putBoolean(LOGIN_STATUS_KEY,loginStatus);
                    editor.commit();
                    Log.e("JSON Parser", "Error creating the json object " + e.toString());
                }
//##################################################################33
                http.disconnect();
                return result;

            } catch (MalformedURLException e) {
                prefl=getSharedPreferences(preflogin,MODE_PRIVATE);
                SharedPreferences.Editor editor=prefl.edit();
                loginStatus=Boolean.FALSE;
                editor.putBoolean(LOGIN_STATUS_KEY,loginStatus);
                editor.commit();
                Log.d("JSON Exception",e.toString());
                result =e.getMessage();
            } catch (ProtocolException e) {
                prefl=getSharedPreferences(preflogin,MODE_PRIVATE);
                SharedPreferences.Editor editor=prefl.edit();
                loginStatus=Boolean.FALSE;
                editor.putBoolean(LOGIN_STATUS_KEY,loginStatus);
                editor.commit();
                Log.d("JSON Exception",e.toString());
                result =e.getMessage();
            } catch (IOException e) {
                prefl=getSharedPreferences(preflogin,MODE_PRIVATE);
                SharedPreferences.Editor editor=prefl.edit();
                loginStatus=Boolean.FALSE;
                editor.putBoolean(LOGIN_STATUS_KEY,loginStatus);
                editor.commit();
                Log.d("JSON Exception",e.toString());
                result =e.getMessage();
            }

            return result;
        }
    }

    public  void savingToSharedPrefs( String Ssurname,String Sfirstname,String Scode,String Sphonenumb,String Semail,String Sresidence){
        //shared prefs#########################################
        prefs=getSharedPreferences(prefName, MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();

        //---save the values in the EditText view to preferences---
        editor.putString(SURNAME_KEY, Ssurname);
        editor.putString(FIRST_NAME_KEY, Sfirstname);
        editor.putString(PHONE_NUMBER_KEY, Sphonenumb);
        editor.putString(AGENT_CODE_KEY, Scode);
        editor.putString(EMAIL_ADDRESS_KEY, Semail);
        editor.putString(RESIDENCE_KEY, Sresidence);
        //---saves the values---
        editor.commit();
        Log.d("JSONStatus","saved to prefs successfully");
    }

    public void createAccount(View view){
        Intent intent= new Intent(this,registration.class);
        startActivity(intent);
    }
}
