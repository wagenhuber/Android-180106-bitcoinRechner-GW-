package com.example.guenther.android_180106_bitcoinrechner_gw;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends Activity {


    private Button buttonGetRate;
    private TextView textViewLabelBTC, textViewScreenBTC;
    private double kursEuro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonGetRate = findViewById(R.id.buttonGetRate);
        textViewLabelBTC = findViewById(R.id.textViewLabelBTC);
        textViewScreenBTC = findViewById(R.id.textViewScreenBTC);


        buttonGetRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDownloadThread myDownloadThread = new MyDownloadThread();
                myDownloadThread.execute();


            }
        });

    }//ende "onCreate"


    /**
     * Methode zum Abruf von Daten aus dem Internet
     */

    public class MyDownloadThread extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                url = new URL("https://bitaps.com/api/ticker/average");
                urlConnection = url.openConnection();
                inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String jsonZeile = null;
                String zeile = null;
                while ((zeile = bufferedReader.readLine()) != null) {
                    jsonZeile = zeile;
                }
                JSONObject jsonObject = new JSONObject(jsonZeile);
                JSONObject fx_rates = jsonObject.getJSONObject("fx_rates");
                kursEuro = fx_rates.getDouble("eur");
            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            textViewScreenBTC.setText(String.valueOf(kursEuro));
        }
    }

}
