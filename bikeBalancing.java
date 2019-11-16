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

public class bikeBalancing extends AppCompatActivity {

    private ProgressBar progBar;
    private TextView text;
    private Handler mHandler = new Handler();
    private int mProgressStatus=0;

    EditText efrom,eto,eby,ebikeno;

    String serverKey="2y10pN0pj28Q9WNoLrPCI3mIwtDkHhBmfpFGWshiuHxvqmzsltGQKzS";
    static JSONObject jObj = null;
    static String json = "";

    String message;
    int success=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_balancing);

        progBar= (ProgressBar)findViewById(R.id.progressBar44);
        pogless();

        //---------------toolbar--------------------
        Toolbar toolbar =findViewById(R.id.balancingtool);
        setSupportActionBar(toolbar);

        efrom=(EditText)findViewById(R.id.editTextfrom);
        eto=(EditText)findViewById(R.id.editTextto);
        eby=(EditText)findViewById(R.id.editTextby);
        ebikeno=(EditText)findViewById(R.id.editTextbikebal);

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

    public void balance(View view){

        String bfrom =efrom.getText().toString();
        String bto =eto.getText().toString();
        String bby =eby.getText().toString();
        String bbikeno =ebikeno.getText().toString();


        //Checking if all fields have been filled
        if(!bfrom.isEmpty() && !bto.isEmpty() && !bby.isEmpty() && !bbikeno.isEmpty()){

            ProgressBar pb =findViewById(R.id.progressBar44);
            pb.setVisibility(ProgressBar.VISIBLE);

            Toast.makeText(getApplicationContext(),"Balancing...",Toast.LENGTH_LONG).show();
            new bikeBalancing.backgroundbalance(this).execute(bfrom,bto,bby,bbikeno);
            Log.d("Request status","GOOD INPUT am gonna make the request");

        }
        else {
            Toast.makeText(getApplicationContext(),"Please fill in all fields",Toast.LENGTH_LONG).show();
        }

    }
    //##################BACK GROUND CLASSS$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$444444444
    class backgroundbalance extends AsyncTask<String, Void,String> {

        AlertDialog dialog;
        Context context;
        public backgroundbalance(Context context){
            this.context=context;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
//                    dialog.setMessage(s);
//                    dialog.show();

            ProgressBar pb =findViewById(R.id.progressBar44);
            pb.setVisibility(ProgressBar.INVISIBLE);

            if (success==1){
                try {
                    jObj = new JSONObject(s);
                    String myrespo=jObj.getString("message");
                    Toast.makeText(getApplicationContext(),myrespo,Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent= new Intent(bikeBalancing.this,MainActivity.class);
                startActivity(intent);

            }
            else {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected String doInBackground(String... voids) {
            String result="";
            String sfrom =voids[0];
            String sto= voids[1];
            String sby=voids[2];
            String sbikeno=voids[3];



            String connstr="http://stardigitalbikes.com/bike_balancing.php";
//            String connstr="http://192.168.43.113/pdobikephp/bike_balancing.php";

            try {
                URL url =new URL(connstr);
                HttpURLConnection http =(HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);


                OutputStream ops =http.getOutputStream();
                BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("agentto","UTF-8")+"="+URLEncoder.encode(sto,"UTF-8")
                        +"&&"+ URLEncoder.encode("agentfrom","UTF-8")+"="+URLEncoder.encode(sfrom,"UTF-8")
                        +"&&"+ URLEncoder.encode("agentby","UTF-8")+"="+URLEncoder.encode(sby,"UTF-8")
                        +"&&"+ URLEncoder.encode("bikenumber","UTF-8")+"="+URLEncoder.encode(sbikeno,"UTF-8")
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
                        success=jObj.getInt("success");

                        Log.d("JSONStatus", "JSON RETURNED");

                        if(success==1){
                            message=jObj.getString("message");
                            Log.d("JSONStatus",message);


                        }else{
                            Log.d("JSONStatus","Login failure");
                            message=jObj.getString("message");
                            Log.d("JSONStatus",message);
                        }
                    }else{
                        message="balancing unsuccessful";
                        Log.e("JSON Parser", "RETURNED JSON IS NULL ");
                    }
                } catch (JSONException e) {
                    message="balancing unsuccessful";
                    Log.e("JSON Parser", "Error creating the json object " + e.toString());
                }
//##################################################################33
                http.disconnect();
                return result;

            } catch (MalformedURLException e) {
                message="balancing unsuccessful 1";
                Log.d("JSON Exception",e.toString());
                result =e.getMessage();
            } catch (ProtocolException e) {
                message="balancing unsuccessful 2";
                Log.d("JSON Exception",e.toString());
                result =e.getMessage();
            } catch (IOException e) {
                message="connection error";
                Log.d("JSON Exception",e.toString());
                result =e.getMessage();
            }

            return result;
        }
    }
      }