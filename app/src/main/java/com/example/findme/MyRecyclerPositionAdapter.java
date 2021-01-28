package com.example.findme;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyRecyclerPositionAdapter
        extends RecyclerView.Adapter<MyRecyclerPositionAdapter.MyViewHolder> {
    Context con;
    ArrayList<Position> data;
    LocationFinder finder;
    double longitude = 0.0, latitude = 0.0;

    public MyRecyclerPositionAdapter(Context con, ArrayList<Position> data) {
        this.con = con;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //crrer 12 view
        LayoutInflater inf=LayoutInflater.from(con);
        CardView element=(CardView) inf.inflate(R.layout.view_position,null);
        element.setContentPadding(20,20,20,20);
        return new MyViewHolder(element); //viewholder hiya elview
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //affectation de valeur
        Position p=data.get(position);
        holder.tv_id.setText(Html.fromHtml("<b>" + p.id + "</b>"  ) );
        holder.tv_num.setText(Html.fromHtml("<b>" + "Num:" + "</b>" +p.numero ) );
        holder.tv_lat.setText(Html.fromHtml("<b>" + "Lat:" + "</b>" +p.latitude ) );
        holder.tv_lon.setText(Html.fromHtml("<b>" + "Long:" + "</b>" +p.longitude ) );
//        holder.tv_num.setText("Num: "+p.numero);
//        holder.tv_lat.setText("Lat: "+p.latitude);
//        holder.tv_lon.setText("Long: "+p.longitude);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id,tv_num,tv_lat,tv_lon;
        Button btncall,btnmap,btnsms;

        public MyViewHolder(@NonNull View element) {
            super(element);
            //reccupertation
             tv_id=element.findViewById(R.id.tv_id_view);
             tv_num=element.findViewById(R.id.tv_num_view);
             tv_lat=element.findViewById(R.id.tv_lat_view);
             tv_lon=element.findViewById(R.id.tv_lon_view);
             btncall=element.findViewById(R.id.btn_call_view);
             btnmap=element.findViewById(R.id.btn_map_view);
             btnsms=element.findViewById(R.id.btn_sms_view);
             //les actions
            btncall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // int indice=MyViewHolder.this.getAdapterPosition(); //renvoie lindce de lelement selectionner
                  //  Toast.makeText(con,"appel de :"+data.get(indice).numero, Toast.LENGTH_SHORT).show();
                   // Uri number = Uri.parse("tel:" + data.get(indice).numero);
                //    Intent dial = new Intent(Intent.ACTION_CALL, number);
                  //  con.startActivity(dial);

                    try {
                        int indexPosition=MyViewHolder.this.getAdapterPosition();
                        Intent my_callIntent = new Intent(Intent.ACTION_CALL);
                        my_callIntent.setData(Uri.parse("tel:"+data.get(indexPosition).numero));
                        //here the word 'tel' is important for making a call...
                        con.startActivity(my_callIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(con.getApplicationContext(), "Error in your phone call"+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    //intenet qui permet de lancer appel telephonique vers le numero
                    //truc1=y7otlk el num 3al interf w enti tenzel 3al appel
                    //truc2=direct yotlob
                    /* btncall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse(String.valueOf(tv_num)));
                            con.startActivity(callIntent);
                        }
                    });

                     */
                }
            });
            btnmap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int indice=getAdapterPosition();
                    Position p=data.get(indice);
                    Intent i=new Intent(con,MapsActivity.class);
                    i.putExtra("NUMERO",p.numero);
                    i.putExtra("LONGITUDE",p.longitude);
                    i.putExtra("LATITUDE",p.latitude);

                    con.startActivity(i);
                }
            });
            btnsms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int indice=getAdapterPosition();
                    Position currentPosition=data.get(indice);
                    SmsManager manager=SmsManager.getDefault();//sim1

                    finder = new LocationFinder(con);
                    if (finder.canGetLocation()) {
                        latitude = finder.getLatitude();
                        longitude = finder.getLongitude();
                        manager.sendTextMessage
                                (currentPosition.numero, //numdest
                                        null,
                                        //a faire long w lat locale te3ek
                                        "Findme#" +longitude+"#" +latitude, //poslocal:findme#long#lat
                                        null,
                                        null);


                    }


                }
            });


        }
    }
}
