package com.example.a2_2_stock_monitor;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class MakeHttpCallTask extends AsyncTask<String, Integer, String> {

    URL url;
    String companyCode;
    String companyName;
    String textFromWeb;
    JSONObject jsonText;

    public MakeHttpCallTask(String id, String name) {

        try {
            companyCode = id;
            companyName = name;

            url= new URL("https://financialmodelingprep.com/api/company/price/"+id);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public interface ReporterInterface{
       void httpFetchDone(String Data);
    }


    ReporterInterface callbackInterface;

    public void setCallbackInterface(ReporterInterface callbackInterface) {
        this.callbackInterface = callbackInterface;
    }



//FUNCTIONS
    private void getData()
    {
        HttpURLConnection urlConnection = null;

        //create connection
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //read stream
        try {
            textFromWeb =readStream(urlConnection.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

        //close connection
        finally {
            urlConnection.disconnect();
        }
    }



    //copied this from web
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


    private String parseJson(String jsonAsString, String filter)
    {
        int indexStart = jsonAsString.indexOf(filter)+filter.length()+3; //tämä on vissiin vähän tyhmä tapa toteuttaa tämä
        int indexEnd =  jsonAsString.indexOf(".")+3;

        String result = jsonAsString.substring(indexStart, indexEnd);

        //Log.d("OAMK", "parseJson: "+ result.indexOf(filter));

        return result;
    }


//DOING BACKGROUND
    @Override
    protected String doInBackground(String... strings) {
        getData();
        String price = parseJson(textFromWeb, "price");

        String stock = companyName+ ": "+ price;
        return stock;
    }

//ON POST EXECUTE
    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(s);
        if(callbackInterface != null)
        {
            callbackInterface.httpFetchDone(s);
        }
    }


}
