package com.ajapps.weatherapplication;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);

        Bundle extras = this.getIntent().getExtras();
        String location = extras.getString("location");
        try {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String weather = getWeather(location);
            TextView viewById = findViewById(R.id.weatherInfo);
            viewById.setText(location);
            viewById.append("\n");
            viewById.append(weather);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getWeather(String location) throws Exception {
        //Add your APPID here
        final String APPID = "929a0df0aa90cba43a529736da6542be";

        //Construct and instantiate the URL
        final String REQUEST = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric",
                location, APPID);
        final URL weatherURL = new URL(REQUEST);

        //Open the connection
        HttpURLConnection conn = (HttpURLConnection) weatherURL.openConnection();

        //Buffer for the incoming data
        byte[] buff = new byte[4 * 1024];

        //Open the stream for reading
        try {
            InputStream is = new BufferedInputStream(conn.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            //Create a 4K buffer to hold data
            int count = 0;
            //Read the data into the buffer, EOF = -1
            while ((count = is.read(buff)) != -1)
                bos.write(buff, 0, count);
            bos.flush();
            buff = bos.toByteArray();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Use to hold the final result
        StringBuilder sb = new StringBuilder();

        JSONObject result = new JSONObject(new String(buff));
        JSONObject jObj = result.getJSONObject("coord");

        //Get location attribute
        sb.append(String.format("Location: lat: %f, lon: %f\n",
                jObj.getDouble("lat"), jObj.getDouble("lon")));

        //Get the weather attribute
        JSONArray jArr = result.getJSONArray("weather");
        for (int i = 0; i < jArr.length(); i++) {
            jObj = jArr.getJSONObject(i);
            sb.append(String.format("Weather: %s - %s\n",
                    jObj.getString("main"), jObj.getString("description")));
        }

        //Get the temperate attribute
        jObj = result.getJSONObject("main");
        sb.append(String.format("Temperature: %f\n", jObj.getDouble("temp")));
        sb.append(String.format("Min temp: %f\n", jObj.getDouble("temp_min")));
        sb.append(String.format("Max temp: %f\n", jObj.getDouble("temp_max")));

        //Return the result
        return (sb.toString());

    }
}
