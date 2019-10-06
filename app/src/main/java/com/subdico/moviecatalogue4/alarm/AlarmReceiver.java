package com.subdico.moviecatalogue4.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.subdico.moviecatalogue4.MainActivity;
import com.subdico.moviecatalogue4.R;
import com.subdico.moviecatalogue4.model.ListData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String EXTRA_TYPE = "type";

    public static final String DAILY_REMINDER = "daily_reminder";
    public static final String RELEASE_REMINDER = "release_reminder";

    public static final int ID_DAILY_REMINDER = 101;
    public static int ID_RELEASE_REMINDER = 201;
    public static int ID_RELEASE_REMINDER_DIFF = 0;
    private String TIME_FORMAT = "HH:mm";

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        if (type != null){
            if (type.equalsIgnoreCase(DAILY_REMINDER)) {
                showDailyReminder(context);
            } else{
                new ReleaseApi(context);
            }
        }
    }


    public void setRepeatingAlarm(Context context, String type, String time) {

        if (isDateInvalid(time, TIME_FORMAT)) return;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);

        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        int requestCode = type.equals(DAILY_REMINDER) ? ID_DAILY_REMINDER : ID_RELEASE_REMINDER;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        String localeType = type.equals(DAILY_REMINDER) ?
                context.getString(R.string.daily_reminder) :
                context.getString(R.string.release_today_reminder);

        Toast.makeText(context, String.format(context.getResources().getString(R.string.alarm_turned_on),localeType), Toast.LENGTH_SHORT).show();
    }




    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    public static void onReceiveReleaseToday(Context context, ArrayList<ListData> items) {
        Log.d("XXX", "onReceiveReleaseToday:");
        for (int i = 0; i < items.size(); ++i) {
            String title = items.get(i).getName();
            String message = String.format(context.getResources().getString(R.string.has_been_release), title);
            showReleaseReminder(context, title, message);
        }

        // Reset ulang
        ID_RELEASE_REMINDER -= ID_RELEASE_REMINDER_DIFF;
        ID_RELEASE_REMINDER_DIFF = 0;
    }

    private void showDailyReminder(Context context) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "Daily Reminder Channel";

        String title = context.getResources().getString(R.string.app_name);
        String message = context.getResources().getString(R.string.daily_notif_message);

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, ID_RELEASE_REMINDER, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(ID_DAILY_REMINDER, notification);
        }

    }

    private static void showReleaseReminder(Context context, String title, String message) {
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "Release Today Reminder Channel";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(ID_RELEASE_REMINDER++, notification);
            ++ID_RELEASE_REMINDER_DIFF;
        }

    }
    public void cancelAlarm(Context context, String type) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equals(DAILY_REMINDER) ? ID_DAILY_REMINDER : (ID_RELEASE_REMINDER - ID_RELEASE_REMINDER_DIFF);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null)
            alarmManager.cancel(pendingIntent);

        String localeType = type.equals(DAILY_REMINDER) ?
                context.getString(R.string.daily_reminder) :
                context.getString(R.string.release_today_reminder);

        Toast.makeText(context, String.format(context.getResources().getString(R.string.alarm_turned_off),localeType), Toast.LENGTH_SHORT).show();
    }


}
