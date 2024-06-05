package com.example.weatherapplication;

import static com.android.volley.Response.*;
import static com.squareup.picasso.Picasso.*;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID="Weather Channel";
    private static final int id=10;

    Button b1,b2,b3;
    TextView t1,t3,t4,t5,t6,t7;
    ImageView img;
    EditText edt;
    String city_name="Dhaka";
    String welcome="Welcome To Our Application";







    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=findViewById(R.id.bt1);
        b2=findViewById(R.id.bloc);
        b3=findViewById(R.id.bex);

        t1=findViewById(R.id.cty);

        t3=findViewById(R.id.temp);
        t4=findViewById(R.id.desc);
        t5=findViewById(R.id.t1);
        t6=findViewById(R.id.t2);
        t7=findViewById(R.id.t3);

        img=findViewById(R.id.wicon);



        edt=findViewById(R.id.edt);



        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String currentTime = simpleDateFormat.format(calendar.getTime());
                    // show_notify("Hello From FI");
                handler.postDelayed(this,  3600000);
            }
        };
        handler.post(runnable);

        show_notify(welcome);





        b1.setOnClickListener(view->weather());
        b2.setOnClickListener(view->curr_location());
        b3.setOnClickListener(view->exit());




    }
    void show_notify(String txt){
        Drawable logo= ResourcesCompat.getDrawable(getResources(),R.drawable.wlogo,null);

        BitmapDrawable bitmapDrawable=(BitmapDrawable) logo;

        assert bitmapDrawable != null;
        Bitmap Largicon=bitmapDrawable.getBitmap();


        NotificationManager nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification=new Notification.Builder(this)
                    .setLargeIcon(Largicon)
                    .setSmallIcon(R.drawable.wlogo)
                    .setContentText(txt)
                    .setSubText("New Message")
                    .setChannelId(CHANNEL_ID)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"New Channel",NotificationManager.IMPORTANCE_HIGH));
        }
        else{
            notification=new Notification.Builder(this)
                    .setLargeIcon(Largicon)
                    .setSmallIcon(R.drawable.wlogo)
                    .setContentText("Weather Notification")
                    .setSubText("Today Weather is 38°C")
                    .build();

        }
        nm.notify(id,notification);

    }

    @Override
    public void onBackPressed() {
        exit();
    }
    void exit(){
        AlertDialog.Builder exdialog =new AlertDialog.Builder(MainActivity.this);
        exdialog.setTitle("Exit?");
        exdialog.setIcon(R.drawable.wlogo);
        exdialog.setMessage("Are you sure about your Exit?");
        exdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.super.onBackPressed();
            }
        });
        exdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "canceled", Toast.LENGTH_SHORT).show();
            }
        });
        exdialog.show();



    }

    void curr_location(){
        Toast.makeText(MainActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();
    }
    public void weather() {
       // showDate_Time();
        city_name=edt.getText().toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city_name+"&appid=17ec87e12ecb6c07d786580355973f43";

        RequestQueue queue=Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("weather");
                    JSONObject weatherObject=jsonArray.getJSONObject(0);
                    String description=weatherObject.getString("description");
                    String icon = weatherObject.getString("icon");
                    String iconUrl = "https://openweathermap.org/img/w/" + icon + ".png";

                    JSONObject object=response.getJSONObject("main");
                    String temp1=object.getString("temp");
                    Double temp2=Double.parseDouble(temp1)-273.15;
                    String tmp= temp2.toString().substring(0,5)+"°C";
                    float pressure = object.getInt("pressure");
                    int humidity = object.getInt("humidity");
                    JSONObject jsonObjectWind = response.getJSONObject("wind");
                    String wind_speed= jsonObjectWind.getString("speed");
                    //picasso
                    Picasso.get().load(iconUrl).placeholder(R.drawable.loading).error(R.drawable.error).into(img);

                    t1.setText(city_name);
                    t3.setText(tmp);
                    t4.setText(description);
                    String press="Pressure : "+pressure+"hPa";
                    String hum="Humidity : "+humidity+"%";
                    String ws="Wind Speed : "+wind_speed+"m/s";
                    t5.setText(hum);
                    t6.setText(press);
                    t7.setText(ws);









                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this,"Error message",Toast.LENGTH_SHORT).show();
                }


            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);

    }


}