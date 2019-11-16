package com.example.samuelhimself.agent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class registration extends AppCompatActivity {


    String serverKey="2y10pN0pj28Q9WNoLrPCI3mIwtDkHhBmfpFGWshiuHxvqmzsltGQKzS";
    private static final String SURNAME_KEY ="Surname";
    private static final String FIRST_NAME_KEY ="First Name";
    private static final String AGENT_CODE_KEY ="Agent Code";
    private static final String PHONE_NUMBER_KEY ="Phone Number";
    private static final String EMAIL_ADDRESS_KEY ="Email";
    private static final String RESIDENCE_KEY ="Residence";
    private static final String GENDER_KEY ="Gender";
    private static final String LOGIN_STATUS_KEY ="Login Status";

    private SharedPreferences prefs,prefl;
    private String prefName ="preProfile";
    private String preflogin="preflogin";
    static JSONObject jObj = null;
    static String json = "";
    String usersurname,userfirstname,userphonenumb,useremailadd,userresidence,usergender,usercode,message;
    Boolean loginStatus;

    String sex="M";
    EditText esname,efname,ephone,eemail,epassword,eresi,ealtphone,enin;
    Button Bsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        esname=(EditText)findViewById(R.id.sname);
        efname=(EditText)findViewById(R.id.fname);
        ephone=(EditText)findViewById(R.id.phonenum);
        ealtphone=(EditText)findViewById(R.id.altphonenum);
        enin=(EditText)findViewById(R.id.nationalid);
        eemail=(EditText)findViewById(R.id.Eemail);
        epassword=(EditText)findViewById(R.id.Ppassword);
        eresi=(EditText)findViewById(R.id.resi);
        Bsignup=(Button)findViewById(R.id.signup);


    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_male:
                if (checked){
                    sex="M";
                }
                // Pirates are the best
                break;
            case R.id.radio_female:
                if (checked){
                    sex="F";
                }
                // Ninjas rule
                break;
        }

    }

    public void regMe(View view){

        String sname =esname.getText().toString();
        String fname =efname.getText().toString();
        String phone =ephone.getText().toString();
        String altpn =ealtphone.getText().toString();
        String national=enin.getText().toString();
        String email =eemail.getText().toString();
        String psword =epassword.getText().toString();
        String resid =eresi.getText().toString();


        //Checking if all fields have been filled
        if(!sname.isEmpty() && !fname.isEmpty() && !phone.isEmpty() && !altpn.isEmpty() && !national.isEmpty() && !email.isEmpty() && !psword.isEmpty() &&!resid.isEmpty()){

            new backgroundregistration(this).execute(sname,fname,phone,altpn,national,email,psword,resid,sex);
            Log.d("Request status","GOOD INPUT am gonna make the request");
        }
        else {
            Toast.makeText(getApplicationContext(),"Please fill in all fields",Toast.LENGTH_LONG).show();
        }

    }
    //##################BACK GROUND CLASSS$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$444444444
    class backgroundregistration extends AsyncTask<String, Void,String> {


        AlertDialog dialog;
        Context context;
        public backgroundregistration(Context context){
            this.context=context;

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
//                    dialog.setMessage(s);
//                    dialog.show();
//      new registration().savingToSharedPrefs(usersurname,userfirstname,userphonenumb,useremailadd,userresidence,usergender,loginStatus);
            prefl=getSharedPreferences(preflogin, MODE_PRIVATE);
            if (prefl.getBoolean(LOGIN_STATUS_KEY,true)){
                Intent intent= new Intent(registration.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"Phone number already used",Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected String doInBackground(String... voids) {
            String result="";
            String sname =voids[0];
            String  fname= voids[1];
            String phonenum=voids[2];
            String altphone=voids[3];
            String natnumber=voids[4];
            String mail=voids[5];
            String pass=voids[6];
            String residence=voids[7];
            String sex=voids[8];


            String connstr="http://stardigitalbikes.com/agent_sign_up.php";
//            String connstr="http://192.168.43.189/bikephp/agent_sign_up.php";

            try {
                URL url =new URL(connstr);
                HttpURLConnection http =(HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);


                OutputStream ops =http.getOutputStream();
                BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("surname","UTF-8")+"="+URLEncoder.encode(sname,"UTF-8")
                        +"&&"+ URLEncoder.encode("firstname","UTF-8")+"="+URLEncoder.encode(fname,"UTF-8")
                        +"&&"+ URLEncoder.encode("phonenumber","UTF-8")+"="+URLEncoder.encode(phonenum,"UTF-8")
                        +"&&"+ URLEncoder.encode("altphonenumber","UTF-8")+"="+URLEncoder.encode(altphone,"UTF-8")
                        +"&&"+ URLEncoder.encode("nationalnumber","UTF-8")+"="+URLEncoder.encode(natnumber,"UTF-8")
                        +"&&"+ URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(mail,"UTF-8")
                        +"&&"+ URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8")
                        +"&&"+ URLEncoder.encode("residence","UTF-8")+"="+URLEncoder.encode(residence,"UTF-8")
                        +"&&"+ URLEncoder.encode("gender","UTF-8")+"="+URLEncoder.encode(sex,"UTF-8")
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
                            usergender=user.getString("GD");

                            prefl=getSharedPreferences(preflogin,MODE_PRIVATE);
                            SharedPreferences.Editor editor=prefl.edit();
                            loginStatus=Boolean.TRUE;
                            editor.putBoolean(LOGIN_STATUS_KEY,loginStatus);
                            editor.commit();
                            Log.d("JSONStatus","Login success");



//make SHARED PREFS A METHOD

                            savingToSharedPrefs(usersurname,userfirstname,userphonenumb,usercode,useremailadd,userresidence,usergender);

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

    public  void savingToSharedPrefs( String Ssurname,String Sfirstname,String Sphonenumb,String Scode,String Semail,String Sresidence,String Sgender){
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
        editor.putString(GENDER_KEY, Sgender);

        //---saves the values---
        editor.commit();

        Log.d("JSONStatus","saved to prefs successfully");
    }

}
