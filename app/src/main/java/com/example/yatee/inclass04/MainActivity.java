package com.example.yatee.inclass04;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    int spinnerPosition = 0;
    String[] newChannels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newChannels = getResources().getStringArray(R.array.news_array);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerPosition = position;
                Log.d("Spinner :: ", ""+position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final Button btnGetNews = (Button) findViewById(R.id.button);
        btnGetNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button :: ", ""+newChannels[spinnerPosition]+"");

            }
        });


        ImageView first = (ImageView) findViewById(R.id.btnFirst);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setSelection(0);
                spinnerPosition = 0;
                btnGetNews.performClick();
            }
        });
        ImageView prev = (ImageView) findViewById(R.id.btnPrev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setSelection((spinnerPosition == 0? 0:spinnerPosition-1));
                spinnerPosition = (spinnerPosition == 0? 0:spinnerPosition-1);
                btnGetNews.performClick();
            }
        });
        ImageView next = (ImageView) findViewById(R.id.btnNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setSelection((spinnerPosition == 4? 4:spinnerPosition+1));
                spinnerPosition = (spinnerPosition == 4? 4:spinnerPosition+1);
                btnGetNews.performClick();
            }
        });
        ImageView last = (ImageView) findViewById(R.id.btnLast);
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setSelection(4);
                spinnerPosition = 4;
                btnGetNews.performClick();
            }
        });
        final Button finish = (Button) findViewById(R.id.btnFinish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    class GetNewsArticles extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            BufferedReader br=null;
            try {
                URL url=new URL(params[0]);
                HttpURLConnection con= (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb=new StringBuilder();
                String line="";
                while ((line=br.readLine())!=null){
                    sb.append(line+"\n");
                }
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(br!=null)
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("Demo",s);
            super.onPostExecute(s);
        }
    }

}

