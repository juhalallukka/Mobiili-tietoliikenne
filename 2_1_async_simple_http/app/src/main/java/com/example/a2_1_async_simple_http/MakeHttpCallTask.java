package com.example.a2_1_async_simple_http;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



class MakeHttpCallTask extends AsyncTask<String, Integer, String> {

    URL url;
    String httpTextFromWeb;

    //constructor
    public MakeHttpCallTask(String urlAsString) {
        //here you could probably use the parameter to set the "url"
        try {
            url = new URL(urlAsString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public interface ReporterInterface
    {
        void httpFetchDone(String data);
    }

    ReporterInterface callbackInterface;

    public void setCallbackInterface(ReporterInterface callbackInterface) {
        this.callbackInterface = callbackInterface;
    }



//FUNCTIONS

    private void getData()
    {
        HttpURLConnection urlConnection = null;

        //open connection
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //read stream
        try {
            InputStream inputStream =  new BufferedInputStream(urlConnection.getInputStream());
            httpTextFromWeb = readStream(inputStream); //using stream reader copied from web

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


//DO IN BACKGROUND
    @Override
    protected String doInBackground(String... strings) {



        getData();
                    /*
            //this is just for debugging
        try {



            for(int i = 0; i <10; i++)
            {
                Thread.sleep(1000);
                publishProgress(new Integer(i));
                Log.d("OAMK", "doInBackground: "+i);
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } */

        //return new String("jotain netistä");
        return httpTextFromWeb;
    }

//ON POST EXECUTE
    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(s);

        if(callbackInterface != null){
            callbackInterface.httpFetchDone(s);
        }

    }
}
