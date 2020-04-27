package com.example.a3_1_volley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonGet;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGet = findViewById(R.id.buttonGet);
        textView = findViewById(R.id.textViewMain);

        buttonGet.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == buttonGet.getId() )
        {
            textView.setText("Toimii");
        }
    }
}
