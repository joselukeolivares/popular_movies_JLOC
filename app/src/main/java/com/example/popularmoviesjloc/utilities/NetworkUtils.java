package com.example.popularmoviesjloc.utilities;

import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    public static Uri getURI(String URLBase,String[] keys,String[] params){

        Uri.Builder uri=
                Uri.parse(URLBase).buildUpon();
                        //.appendQueryParameter(keys[0],params[0])
                        //.appendQueryParameter(keys[1],params[1])
                        //.build();
  for (int i=0;i<keys.length;i++){

      uri.appendQueryParameter(keys[i],params[i]);
  }

        
        //Log.i("uri:"+params[0],uri.toString());

        return uri.build();

    }

    public static URL getURL(Uri uri){
        URL url=null;
        try{
            url=new URL(uri.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }


    public static String getResponseFromHttpUrl(URL url)throws IOException {
    StringBuilder result=new StringBuilder();
        HttpURLConnection urlConnection=null;


        try{
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");

            InputStream in=new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader=new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line=reader.readLine())!=null){
                result.append(line);
            }



        }catch (IOException e){
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }


        Log.i("Response",result.toString());
        return result.toString();
    }
}
