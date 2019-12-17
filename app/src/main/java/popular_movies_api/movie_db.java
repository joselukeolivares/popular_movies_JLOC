package popular_movies_api;

import android.net.Network;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.MalformedParametersException;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.popularmoviesjloc.MainActivity;
import com.example.popularmoviesjloc.utilities.NetworkUtils;


public class movie_db extends AsyncTask<URL,Integer,String> {
    String JsonResult=null;
    MainActivity father;

    public movie_db(MainActivity ma){
    father=ma;

    }

    @Override
    protected String doInBackground(URL... urls) {
        String result=null;
        try{
            result= NetworkUtils.getResponseFromHttpUrl(urls[0]);
        }catch(IOException e){
            e.printStackTrace();
        }


        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        father.stringToJson(s);
    }
}
