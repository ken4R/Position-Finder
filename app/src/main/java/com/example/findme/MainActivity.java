package com.example.findme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    boolean permission_sms=false;
    boolean permission_gps= false; // please why we use this //
    RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         /***traitement de permission ***/
         if ((ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
         == PackageManager.PERMISSION_GRANTED) &&  (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED))
         {
             permission_sms=true;
         }
         else {
             ActivityCompat.requestPermissions(this ,
                     new String[]{
                           Manifest.permission.SEND_SMS,
                             Manifest.permission.READ_SMS,
                             Manifest.permission.RECEIVE_SMS,
                             Manifest.permission.CALL_PHONE,
                     },
                     1);
         }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            permission_gps=true;
        }
        else {
            ActivityCompat.requestPermissions(this ,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,

                    },
                    2);
        }
         rv=findViewById(R.id.rv);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kima tosat w boite d edialogue zeda
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(MainActivity.this,Ajout.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode==1){
            if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[3]==PackageManager.PERMISSION_GRANTED  ){
               permission_sms=true;
            }
            else{
                this.finish();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        PositionManager pm=new PositionManager(this,
                PositionHelper.file_name,
                PositionHelper.version_base);
        ArrayList<Position> data=pm.selectionnertout();

        //affichage sous forme simple liste view
        // ArrayAdapter ad=new ArrayAdapter(this,
        //android.R.layout.simple_list_item_1,
        // data); //kol position y7otha fi test view
        MyRecyclerPositionAdapter ad=new MyRecyclerPositionAdapter(this,data);
        GridLayoutManager manager=new GridLayoutManager(this,1);
        rv.setLayoutManager(manager);
        rv.setAdapter(ad);

    }
}