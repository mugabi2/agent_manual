package com.example.samuelhimself.agent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class bicyclesIn extends AppCompatActivity {

    static String json = "";
    static JSONObject jObj = null;

    String pkafrica,pkcedat,pkcomplex,pkfema,pklibrary,pklivingstone,pklumumba,pkmaingate,pkmarystuart,pkmitchell,pknkrumah,pkuh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bicycles_in);

        Bundle extras=getIntent().getExtras();
        String meso=extras.getString("bikesin");

        //---------------toolbar--------------------
        Toolbar toolbar =findViewById(R.id.intool);
        setSupportActionBar(toolbar);

        try {
            jObj = new JSONObject(meso);
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

            TextView Taf=(TextView)findViewById(R.id.textafricav);
            TextView Tcd=(TextView)findViewById(R.id.textcedatv);
            TextView Tit=(TextView)findViewById(R.id.textcomplexv);
            TextView Tfm=(TextView)findViewById(R.id.textfemav);
            TextView Tlb=(TextView)findViewById(R.id.textlibraryv);
            TextView Tlv=(TextView)findViewById(R.id.textlivingstonev);
            TextView Tlm=(TextView)findViewById(R.id.textlumumbav);
            TextView Tmg=(TextView)findViewById(R.id.textmaingatev);
            TextView Tms=(TextView)findViewById(R.id.textmarystuartv);
            TextView Tmt=(TextView)findViewById(R.id.textmitchellv);
            TextView Tnk=(TextView)findViewById(R.id.textnkrumahv);
            TextView Tuh=(TextView)findViewById(R.id.textuhv);

            Taf.append(pkafrica);
            Tcd.append(pkcedat);
            Tit.append(pkcomplex);
            Tfm.append(pkfema);
            Tlb.append(pklibrary);
            Tlv.append(pklivingstone);
            Tlm.append(pklumumba);
            Tmg.append(pkmaingate);
            Tms.append(pkmarystuart);
            Tmt.append(pkmitchell);
            Tnk.append(pknkrumah);
            Tuh.append(pkuh);




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
