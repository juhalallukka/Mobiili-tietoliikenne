package com.example.a2_1_async_simple_http;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MakeHttpCallTask.ReporterInterface {

    TextView textView;
    Button buttonAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView =findViewById(R.id.textViewMain);
        textView.setMovementMethod(new ScrollingMovementMethod());
        //textView.setOnClickListener(this);

        buttonAsync = findViewById(R.id.buttonAsync);
        buttonAsync.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.buttonAsync){
            MakeHttpCallTask task =  new MakeHttpCallTask("https://www.oamk.fi/fi/oamkilaisille");
            task.setCallbackInterface(this);
            task.execute();
        }
    }


    @Override
    public void httpFetchDone(String data) {
        textView.setText(data);
    }
}
