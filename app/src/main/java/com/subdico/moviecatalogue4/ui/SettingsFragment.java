package com.subdico.moviecatalogue4.ui;


import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.subdico.moviecatalogue4.R;
import com.subdico.moviecatalogue4.alarm.AlarmPreference;
import com.subdico.moviecatalogue4.alarm.AlarmReceiver;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private AlarmPreference alarmPreference;
    private AlarmReceiver alarmReceiver;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        alarmPreference = new AlarmPreference(view.getContext());
        alarmReceiver = new AlarmReceiver();

        SwitchCompat switchDailyReminder = view.findViewById(R.id.switch_daily_reminder);
        SwitchCompat switchReleaseTodayReminder = view.findViewById(R.id.switch_release_today_reminder);


        boolean isDailyReminderActivated = alarmPreference.getAppDailyReminder();
        boolean isReleaseTodayReminderActivated = alarmPreference.getAppReleaseTodayReminder();

        if (isDailyReminderActivated)
            switchDailyReminder.setChecked(true);

        if (isReleaseTodayReminderActivated)
            switchReleaseTodayReminder.setChecked(true);

        switchDailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    alarmPreference.setAppDailyReminder(true);
                    alarmReceiver.setRepeatingAlarm(getContext(), AlarmReceiver.DAILY_REMINDER, "07:00");
                } else{
                    alarmPreference.setAppDailyReminder(false);
                    alarmReceiver.cancelAlarm(getContext(), AlarmReceiver.DAILY_REMINDER);
                }
            }
        });

        switchReleaseTodayReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    alarmPreference.setAppReleaseTodayReminder(true);
                    alarmReceiver.setRepeatingAlarm(getContext(), AlarmReceiver.RELEASE_REMINDER, "08:00");
                } else{
                    alarmPreference.setAppReleaseTodayReminder(false);
                    alarmReceiver.cancelAlarm(getContext(), AlarmReceiver.RELEASE_REMINDER);
                }
            }
        });

        return view;
    }

}
