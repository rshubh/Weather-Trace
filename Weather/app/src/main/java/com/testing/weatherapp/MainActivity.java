package com.testing.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
   String compare = new String("Haze");
   String c2 = new String("Sunny");
   String c3 = new String("Clear");
    Button button;
EditText city;
TextView result;
ImageView imageView ;
TextView longitude;
TextView latitude;
//https://api.openweathermap.org/data/2.5/weather?q=Delhi&appid=03060b773d87474b78d9b224b62e6955
    String baseUrl ="https:api.openweathermap.org/data/2.5/weather?q=";
    String API ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button =(Button) findViewById(R.id.button);
        city = (EditText) findViewById(R.id.cityname);
        result =(TextView)findViewById(R.id.result);
        longitude = (TextView)findViewById(R.id.longitude);
        latitude = (TextView)findViewById(R.id.latitude);
        imageView =(ImageView)findViewById(R.id.image);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myUrl = baseUrl + city.getText().toString()+API;
                Log.i("URL","URL"+myUrl);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Log.i("JSON", "JSON: " + jsonObject);

                                try {
                                    String info = jsonObject.getString("weather");
                                    Log.i("INFO", "INFO: "+ info);

                                    JSONArray ar = new JSONArray(info);

                                    for (int i = 0; i < ar.length(); i++){
                                        JSONObject parObj = ar.getJSONObject(i);
                                        String myWeather = parObj.getString("main");
                                        if(myWeather.equals(compare)){
                                            imageView.setImageResource(R.drawable.haze);
                                            Log.d("He","Kl");
                                        }
                                        else if (myWeather.equals(c2))
                                        {
                                            imageView.setImageResource(R.drawable.sunny);
                                        }
                                        else if(myWeather.equals(c3)){
                                            imageView.setImageResource(R.drawable.cloud);
                                        }
                                        else{
                                            imageView.setImageResource(R.drawable.image);

                                        }

                                        result.setText(myWeather);
                                        Log.i("ID", "ID: " + parObj.getString("id"));
                                        Log.i("MAIN", "MAIN: " + parObj.getString("main"));
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                               try {
                                    String coor = jsonObject.getString("coord");
                                    Log.i("COOR", "COOR: " + coor);
                                    JSONObject co = new JSONObject(coor);

                                    String lon = co.getString("lon");
                                    String lat = co.getString("lat");
                                    longitude.setText(lon);
                                    latitude.setText(lat);

                                    Log.i("LON", "LON: " + lon);
                                    Log.i("LAT", "LAT: " + lat);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                               }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.i("Error", "Something went wrong" + volleyError);
                            }
                        }
                );
                MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);
            }
        });
    }
}
