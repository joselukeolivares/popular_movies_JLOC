package com.example.popularmoviesjloc.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.popularmoviesjloc.MainActivity;
import com.example.popularmoviesjloc.R;

public class NotificationBuilder {

    private static final int MOVIE_DB_NOTIFICATION_ID=1988;

    private static final String MOVIE_NOTIFICATION_CHANNEL_ID="MOVIE ADD_REMOVED_DB";

    static String titleAdd="The movie was added like favorite: ";
    static String titleRem="The movie was removed like favorite: ";
    static String title;

    public static void movie_modified_db(Context context,boolean added,String name){

        if(added){
            title=titleAdd.concat(name);
        }else{
            title=titleRem.concat(name);
        }

        Log.i(context.getClass().getName(),"Notification Builder");

        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel mChannel=new NotificationChannel(
                    MOVIE_NOTIFICATION_CHANNEL_ID,
                    "primary",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(
                context,
                MOVIE_NOTIFICATION_CHANNEL_ID
                )
                .setColor(ContextCompat.getColor(context,R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_star)
                .setLargeIcon(largeIcon(context))
                .setContentTitle("Popular Movies")
                .setContentText(title!=null?title:"List of favorite movies was modified!")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        title!=null?title:"List of favorite movies was modified!"
                ))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true)
                ;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN&&Build.VERSION.SDK_INT<Build.VERSION_CODES.O){
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(MOVIE_DB_NOTIFICATION_ID,notificationBuilder.build());
    }

    public static PendingIntent contentIntent(Context context){
        Intent intent= new Intent(context, MainActivity.class);

        return  PendingIntent.getActivity(
                context,
                MOVIE_DB_NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    public static Bitmap largeIcon(Context context){
        Resources res=context.getResources();

        Bitmap largeIcon= BitmapFactory.decodeResource(res,R.drawable.ic_star);
        return largeIcon;
    }

}
