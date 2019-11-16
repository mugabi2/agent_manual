package com.example.samuelhimself.agent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class finesOfUsers extends AppCompatActivity {
    String lsname,lfname,lpnumb,lmail,lresi,lbnumb,lextra,lfine,ltsr,ltr,ltt,ldur;
    TextView tnm,tpn,tem,trd,tbn,tex,tfin,ttsr,ttr,ttt,tdr;
    JSONObject jObjc;

    private static final String AGENT_CODE_KEY ="Agent Code";


    String serverKey="2y10pN0pj28Q9WNoLrPCI3mIwtDkHhBmfpFGWshiuHxvqmzsltGQKzS";

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
        setContentView(R.layout.activity_fines_of_users);

        Bundle extras=getIntent().getExtras();
        String meso=extras.getString("fines");

        try {
            jObjc = new JSONObject(meso);

            JSONArray userArray=jObjc.getJSONArray("user");
            JSONObject user=userArray.getJSONObject(0);
            lsname=user.getString("SN");
            lfname=user.getString("FN");
            lpnumb=user.getString("PN");
            lmail=user.getString("EM");
            lresi=user.getString("RD");
            lbnumb=user.getString("BN");
            lextra=user.getString("ET");
            lfine=user.getString("FIN");
            ltsr=user.getString("TSR");
            ldur=user.getString("DR");
            ltr=user.getString("TR");
            ltt=user.getString("TT");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        tnm=findViewById(R.id.texname55);
        tpn=findViewById(R.id.texpnumber55);
        tem=findViewById(R.id.texmail55);
        trd=findViewById(R.id.texresidence55);
//        tbn=findViewById(R.id.texbnumber55);
        tex=findViewById(R.id.texex55);
        tfin=findViewById(R.id.texfine55);
//        ttsr=findViewById(R.id.textsr55);
        ttr=findViewById(R.id.textimereturn55);
        ttt=findViewById(R.id.textimetaken55);
        tdr=findViewById(R.id.texdura55);


//        SET THE TEXT BOXES
        tnm.setText(lsname+" "+lfname);
        tpn.setText(lpnumb);
        tem.setText(lmail);
        trd.setText(lresi);
        tex.setText(lextra);
        tfin.setText(lfine);
        ttr.setText(ltr);
        ttt.setText(ltt);
        tdr.setText(ldur);
    }

    public void clearfine(View v){

        prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        String codeyo=prefs.getString(AGENT_CODE_KEY,"");

//        ProgressBar pb =findViewById(R.id.progressBar11);
//        pb.setVisibility(ProgressBar.VISIBLE);

        new finesOfUsers.backgroundclearfines(this).execute(codeyo);
    }

    class backgroundclearfines extends AsyncTask<String, Void,String> {

        AlertDialog dialog;
        Context context;
        public backgroundclearfines(Context context){
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
//            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

//            ProgressBar pb =findViewById(R.id.progressBar11);
//            pb.setVisibility(ProgressBar.INVISIBLE);
            Log.d("JSONStatus", s);

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
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    Intent int1111=new Intent(finesOfUsers.this,MainActivity.class);
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
            String connstr="http://stardigitalbikes.com/fines_confirm.php";
//            String connstr="http://192.168.43.113/pdobikephp/fines_confirm.php";


//*************time*************8
            Calendar date= Calendar.getInstance();
            SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
            day=sdf.format(date.getTime());


            try {
                URL url =new URL(connstr);
                HttpURLConnection http =(HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops =http.getOutputStream();
                BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("agent_code","UTF-8")+"="+URLEncoder.encode(acode,"UTF-8")
//                        +"&&"+ URLEncoder.encode("timenow","UTF-8")+"="+URLEncoder.encode(timez,"UTF-8")
                        +"&&"+ URLEncoder.encode("day","UTF-8")+"="+URLEncoder.encode(day,"UTF-8")
                        +"&&"+ URLEncoder.encode("phonenumber","UTF-8")+"="+URLEncoder.encode(lpnumb,"UTF-8")
                        +"&&"+ URLEncoder.encode("serverkey","UTF-8")+"="+URLEncoder.encode(serverKey,"UTF-8")
                        +"&&"+ URLEncoder.encode("bikenumber","UTF-8")+"="+URLEncoder.encode(lbnumb,"UTF-8");

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
}
