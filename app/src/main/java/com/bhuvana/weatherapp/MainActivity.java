package com.bhuvana.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.PixelCopy;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    Button b;
    AutoCompleteTextView e;
    String url;
    DecimalFormat df=new DecimalFormat("#.##");
    class getweather extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls){
            StringBuilder result=new StringBuilder();
            try
            {
                URL url=new URL(urls[0]);
                HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream=urlConnection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));

                String line="";
                while ((line=reader.readLine())!=null)
                {
                    result.append(line).append("\n");
                }
                return result.toString();
            }catch(Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            String output= "";
            try{
                JSONObject jsonObject=new JSONObject(result);
                //from here
                JSONObject jsonObjectMain =jsonObject.getJSONObject("main");
                double xtemp=jsonObjectMain.getDouble("temp") - 273.15;
                double xfeelsLike=jsonObjectMain.getDouble("feels_like")-273.15;
                double xtempmax=jsonObjectMain.getDouble("temp_max")-273.15;
                double xtempmin=jsonObjectMain.getDouble("temp_min")-273.15;
                float xpressure=jsonObjectMain.getInt("pressure");
                int xhumidity=jsonObjectMain.getInt("humidity");

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("temp", xtemp );
                intent.putExtra("feels_like", xfeelsLike);
                intent.putExtra("temp_max", xtempmax);
                intent.putExtra("temp_min", xtempmin);
                intent.putExtra("pressure", xpressure);
                intent.putExtra("humidity", xhumidity);
                startActivity(intent);

            }catch(JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = findViewById(R.id.getb);
        e = findViewById(R.id.etloc);
        final String[] temp={""};
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city=e.getText().toString();
                try {
                    if (city.equals("")) {
                        Toast.makeText(getApplicationContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        //Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                        //startActivity(intent);
                        url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=yourapikey";
                    }
                    //class to get weather details
                    getweather task = new getweather();
                    temp[0] = task.execute(url).get();
                }catch(ExecutionException e)
                {
                    e.printStackTrace();;
                }catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
        String[] itemarray = getResources().getStringArray(R.array.cities);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, itemarray);
        e.setAdapter(arrayAdapter);
    }

}
