package com.example.a2_2_stock_monitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MakeHttpCallTask.ReporterInterface {

    TextView textView;
    Button buttonGet;

    EditText editTextName;
    EditText editTextId;
    Button buttonAdd;

    ArrayList<Stock> stocks;
    //ArrayList<String> stocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textViewMain);
        buttonGet = findViewById(R.id.buttonGet);

        editTextId = findViewById(R.id.editTextId);
        editTextName = findViewById(R.id.editTextName);
        buttonAdd = findViewById(R.id.buttonAdd);

        buttonGet.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);

        /*
        String nokia = "NOK";
        String apple = "AAPL";
        String google = "GOOGL";
        String fb ="FB";

        stocks = new ArrayList<>();
        stocks.add(nokia);
        stocks.add(apple);
        stocks.add(google);
        stocks.add(fb);
        */

        Stock stock1 = new Stock("Nokia", "NOK");
        Stock stock2 = new Stock("Apple", "AAPL");
        Stock stock3 = new Stock("Google", "GOOGL");
        Stock stock4 = new Stock("Facebook", "FB");

        stocks = new ArrayList<>();
        stocks.add(stock1);
        stocks.add(stock2);
        stocks.add(stock3);
        stocks.add(stock4);



    }

    @Override
    public void onClick(View v) {


        //GET
        if(v.getId() == buttonGet.getId())
        {
            textView.setText("");
            //textView.setText("toimii");
          for(int i = 0; i < stocks.size(); i++)
          {
              /*
              * parametrin ois varmaan hyvä olla arraylist muodossa, niin task vois tehä taustalla rauhassa kutsut omaan tahtiin,
              * nyt ilmeisesti avataan monta yhteyttä yhtä aikaa ja meinaa välillä kaatua
              * */

              MakeHttpCallTask task = new MakeHttpCallTask(stocks.get(i).getId(), stocks.get(i).getName());
              task.setCallbackInterface(this);
              task.execute();
          }
        }

        //ADD

        if(v.getId() == buttonAdd.getId())
        {
            String newName = editTextName.getText().toString();
            String newId = editTextId.getText().toString();

            if(newId != null && newName.length() >0 && newId != null && newId.length() > 0)
            {
                Stock newStock = new Stock(newName, newId);
                stocks.add(newStock);

                editTextName.setText("");
                editTextId.setText("");
            }
        }

    }

    @Override
    public void httpFetchDone(String Data) {
        textView.append(Data +"\n");
    }
}
