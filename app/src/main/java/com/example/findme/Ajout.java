package com.example.findme;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class Ajout extends AppCompatActivity {
    EditText ednum,edlong,edlat;
    Button btnqte,btnval;
    LocationFinder finder;
    double longitude = 0.0, latitude = 0.0;
    private FusedLocationProviderClient mFusedLocationClient;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);
        ednum=findViewById(R.id.ednum_aj);
        edlong=findViewById(R.id.edlong_aj);
        edlat=findViewById(R.id.edlat_aj);
        btnqte=findViewById(R.id.btnqte_aj);
        btnval=findViewById(R.id.btnval_aj);
        btnqte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ajout.this.finish();
            }
        });
        btnval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Integer n=ednum.getText();
                /*int n;
                try {
                    n = Integer.parseInt(ednum.getText().toString());
                }
                catch (NumberFormatException e)
                {
                    n = 0;
                }*/
                Log.d("myTag", "This is my message");

                String n=ednum.getText().toString();
                Log.d("*** nummm ****", n);
                String lon=edlong.getText().toString();
                Log.d("*** longituted ****", lon);
                String lat=edlat.getText().toString();
                Log.d("*** latitue ****", lat);
                PositionManager pm=new PositionManager(Ajout.this,"mespositions.db",1);
                //pm.ouvrir("FindMe_BD",0);
                pm.inserer(n,lon,lat);
                Toast.makeText(Ajout.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });
        Bundle b=this.getIntent().getExtras();
        if (b!=null)
        {

            //sms recu les valeurne5ouhom mn lemsg
            String body=b.getString("BODY");
            //findme#long#lat
            String t[]=body.split("#");
            edlong.setText(t[1]);
            edlat.setText(t[2]);
            String number=b.getString("NUMBER");
            ednum.setText(number);

        }
        else {
            //owner methods after making some research on net
            finder = new LocationFinder(this);
            if (finder.canGetLocation()) {
                latitude = finder.getLatitude();
                longitude = finder.getLongitude();
                edlat.setText(latitude+"");
                edlong.setText(longitude+"");
                ednum.setText("+21624683248"); //a faire num el puce te3ek
            }
            /*
            Log.d("longitude", "manually");
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Ajout.this);
           // FusedLocationProviderClient mclient=
               //     LocationServices.getFusedLocationProviderClient(Ajout.this);
            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Log.d("longitude", location.getLongitude()+"");
                          edlat.setText(location.getLatitude()+"");
                          edlong.setText(location.getLongitude()+"");
                          ednum.setText("+21624683248"); //a faire num el puce te3ek
                    }
                }
            });
            //acontinuer apartie du records
            LocationRequest request=LocationRequest.create()
                    .setInterval(10)
                    .setSmallestDisplacement(10);
            mFusedLocationClient.requestLocationUpdates(request,new LocationCallback(){
                @Override
                public void onLocationResult(LocationResult location) {
                    if (location != null) {
                        Log.d("longitude", location.getLastLocation().getLongitude()+"");
                        edlat.setText(location.getLastLocation().getLatitude()+"");
                        edlong.setText(location.getLastLocation().getLongitude()+"");
                        ednum.setText("+21624683248");
                    }
                  //
                }
            }, null);
        */}

    }

}