package com.example.samuelhimself.agent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class revenue extends AppCompatActivity {

    String meso;
    static JSONObject jObj = null;
    static String json = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue);

        //---------------toolbar--------------------
        Toolbar toolbar =findViewById(R.id.revenuetool);
        setSupportActionBar(toolbar);

        Bundle extras=getIntent().getExtras();
        meso=extras.getString("rev");

//        Toast.makeText(getApplicationContext(),meso,Toast.LENGTH_SHORT).show();
        try {
            jObj = new JSONObject(meso);

            String mytotal=jObj.getString("total");

            TextView Trev=(TextView)findViewById(R.id.textViewrevenue);
            Trev.append(mytotal+" UGX");

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
}
