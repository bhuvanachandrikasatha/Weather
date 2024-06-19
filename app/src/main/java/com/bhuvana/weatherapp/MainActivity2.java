package com.bhuvana.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.BreakIterator;
import java.text.DecimalFormat;

public class MainActivity2 extends AppCompatActivity {
    ImageView vpic, vtmax, vtmin, vfeelslike, vpressure, vhumidity;
    TextView vtmaxtv, vtmintv, vfeelsliketv, vpressuretv, vhumiditytv;
    TextView vtempv, vfeelslikev, vtmaxv, vtminv, vpressurev, vhumidityv;
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        vpic = findViewById(R.id.pic);
        vtmax = findViewById(R.id.tmax);
        vtmin = findViewById(R.id.tmin);
        vfeelslike = findViewById(R.id.feelslike);
        vpressure = findViewById(R.id.pressure);
        vhumidity = findViewById(R.id.humidity);
        vtmaxtv = findViewById(R.id.tmaxtv);
        vtmintv = findViewById(R.id.tmintv);
        vfeelsliketv = findViewById(R.id.feelsliketv);
        vpressuretv = findViewById(R.id.pressuretv);
        vhumiditytv = findViewById(R.id.humiditytv);
        vtempv = findViewById(R.id.tempv);
        vtmaxv = findViewById(R.id.tmaxv);
        vtminv = findViewById(R.id.tminv);
        vfeelslikev = findViewById(R.id.feelslikev);
        vpressurev = findViewById(R.id.pressurev);
        vhumidityv = findViewById(R.id.humidityv);
        //
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double temp = extras.getDouble("temp");//
            vtempv.setText(df.format(extras.getDouble("temp")) + " °C");
            vfeelslikev.setText(df.format(extras.getDouble("feels_like")) + " °C");
            vtmaxv.setText(df.format(extras.getDouble("temp_max")) + " °C");
            vtminv.setText(df.format(extras.getDouble("temp_min")) + " °C");
            vpressurev.setText(String.valueOf(extras.getFloat("pressure")) + " hPa");
            vhumidityv.setText(String.valueOf(extras.getInt("humidity")) + " %");
            if (temp > 30) {
                vpic.setImageResource(R.drawable.sunny);
            } else if (temp > 20) {
                vpic.setImageResource(R.drawable.clouds);
            } else if (temp > 10) {
                vpic.setImageResource(R.drawable.rainy);
            } else {
                vpic.setImageResource(R.drawable.snowy);
            }
        }
    }
}