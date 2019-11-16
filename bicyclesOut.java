package com.example.samuelhimself.agent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class bicyclesOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bicycles_out);

        //---------------toolbar--------------------
        Toolbar toolbar =findViewById(R.id.outtool);
        setSupportActionBar(toolbar);

        Bundle extras=getIntent().getExtras();
        String meso=extras.getString("bikesout");

        TextView Tbikesout=(TextView)findViewById(R.id.textmybicyclesout);
        Tbikesout.append(meso);

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
