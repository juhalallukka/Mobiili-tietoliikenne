package com.example.a1_3_simple_http;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    EditText editText;
    String urlString;
    URL url;
    String httpTextFromWeb;
    Button buttonGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textViewMain);
        editText = findViewById(R.id.editTextMain);
        buttonGet = findViewById(R.id.buttonGet);

        //textView.setOnClickListener(this);
        buttonGet.setOnClickListener(this);
        textView.setMovementMethod(new ScrollingMovementMethod());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        urlString = "https://www.oamk.fi/fi/oamkilaisille";
        editText.setText(urlString);





        //createConnection();



    }

    void createConnection()
    {
        try {
            urlString = editText.getText().toString();
            url = new URL(urlString);

            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    httpTextFromWeb = readStream(inputStream);
                    setText();

                } catch (IOException e) {
                    e.printStackTrace();
                }



            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                urlConnection.disconnect();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }








    }

    //copied his from web
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

    private void setText()
    {
        if(httpTextFromWeb != null && httpTextFromWeb.length() > 0)
        {

            textView.setText(httpTextFromWeb);
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == buttonGet.getId())
        {
            createConnection();
        }

        if(v.getId() == textView.getId())
        {
            createConnection();
            //setText();


        }
    }
}
