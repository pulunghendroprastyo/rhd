package com.example.pulung.rhd;

import android.content.ContentResolver;
import android.content.Context;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by pulung on 26/03/18.
 */

public class Fungsi {


    String splitMentah[],splitWaktu[];
    String st;
    private Context mcontext;
    Date sebelum,sekarang;

    private static final String TAG = "fileOperations";
    private static final String FILENAME = "myFile.txt";


    public String getString (Context context, String URLpage){
        this.mcontext = context;

        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(URLpage);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();
            iStream.close();
            urlConnection.disconnect();

        }catch(Exception e){
            Log.d("Exception", e.toString());
        }
        return data;

    }
    public String getPostString (String URLpage, ArrayList myArray){

        InputStream isPost;
        String resultPost="";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(URLpage);
            httppost.setEntity(new UrlEncodedFormEntity(myArray));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            st = EntityUtils.toString(response.getEntity());
            Log.v("log_tag", "In the try Loop" + st);
            isPost = entity.getContent();

        } catch (Exception e) {

            Log.v("log_tag", "Error in http connection " + e.toString());
        }
        //convert response to string
        try {
            isPost = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(isPost, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line= reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isPost.close();

            resultPost= sb.toString();

        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());


        }
        return resultPost;
    }

    public void sendData(String URLpage, ArrayList myArray){
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(URLpage);
            httppost.setEntity(new UrlEncodedFormEntity(myArray));
            HttpResponse response = httpclient.execute(httppost);
            st = EntityUtils.toString(response.getEntity());
            Log.v("log_tag", "In the try Loop" + st);

        } catch (Exception e) {

            Log.v("log_tag", "Error in http connection " + e.toString());
        }

//        Toast.makeText(context, st, Toast.LENGTH_SHORT).show();
    }
    public int getSelisih (String waktuTanam){

        final Calendar kalender = Calendar.getInstance();
        //Setting format kalender
        SimpleDateFormat formatKalender = new SimpleDateFormat("yyyy-MM-dd");
        //Ambil tanggal terkini dan set variabel string sesuai format di atas
        final String hariIni = formatKalender.format(kalender.getTime());

        try {
            sebelum = formatKalender.parse(hariIni);
            sekarang = formatKalender.parse(waktuTanam);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long duration  = sekarang.getTime() - sebelum.getTime() ;

        long selisih = TimeUnit.MILLISECONDS.toDays(duration);

        //Toast.makeText(getApplicationContext(), "Selisih Hari : " + String.valueOf(selisih) , Toast.LENGTH_SHORT).show();
        return Integer.parseInt(String.valueOf(selisih));

    }

    public void writeToFile(Context context, String data, String fileName) {
        //File sdcard = Environment.getExternalStorageDirectory();
        File path = context.getFilesDir();
        File file = new File(path, fileName);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            stream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public String readFromFile(Context context, String fileName) {
        //File sdcard = Environment.getExternalStorageDirectory();
        File path = context.getFilesDir();
        File file = new File(path, fileName);

        StringBuilder text = new StringBuilder();
        try {


            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                Log.i("Test", "text : "+text+" : end");
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();

        }



        return text.toString();
    }

    public boolean gpsCheck(Context context) {

        ContentResolver contentResolver = context
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {

            return false;

        }

    }






}


