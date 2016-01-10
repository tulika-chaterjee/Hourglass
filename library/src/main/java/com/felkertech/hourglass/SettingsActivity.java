package com.felkertech.hourglass;

import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.SeekBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.felkertech.materialpreferencesactivity.MaterialPreferencesActivity;
import com.felkertech.wearsettingsmanager.WearSettingsManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by guest1 on 1/9/2016.
 */
public class SettingsActivity extends MaterialPreferencesActivity
        implements GoogleApiClient.ConnectionCallbacks, OnConnectionFailedListener {
    private WearSettingsManager sm;
    private GoogleApiClient gapi;

    @Override
    public void onPreferencesLoaded(MaterialPreferencesFragment fragment) {
        sm = new WearSettingsManager(this);
        gapi = new GoogleApiClient.Builder(this)
                .addApiIfAvailable(Wearable.API)
                .build();
        gapi.connect();

        fragment.bindSummary(R.string.hourglass_date_format, RADIO_LIST_PREF, R.array.hourglass_date_format_list);
        fragment.bindSummary(R.string.hourglass_time_format, RADIO_LIST_PREF, R.array.hourglass_time_format_list);
        fragment.enablePreference(R.string.hourglass_debug_mode, R.string.hourglass_debug_date);
        fragment.enablePreference(R.string.hourglass_debug_mode, R.string.hourglass_debug_time);
        fragment.enablePreference(R.string.hourglass_debug_mode, R.string.hourglass_ambient);
        fragment.findPreference(getString(R.string.hourglass_debug_time)).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                MaterialDialog timeDialog = new MaterialDialog.Builder(SettingsActivity.this)
                        .title("Change the Time")
                        .customView(R.layout.hourglass_time, false)
                        .show();
                ((SeekBar) timeDialog.getCustomView().findViewById(R.id.hour)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        sm.setInt(R.string.hourglass_debug_time_hour, progress);
                        sm.pushData();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
                ((SeekBar) timeDialog.getCustomView().findViewById(R.id.minute)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        sm.setInt(R.string.hourglass_debug_time_minute, progress);
                        sm.pushData();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
                ((SeekBar) timeDialog.getCustomView().findViewById(R.id.second)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        sm.setInt(R.string.hourglass_debug_time_second, progress);
                        sm.pushData();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
                return false;
            }
        });
        fragment.findPreference(getString(R.string.hourglass_debug_date)).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                MaterialDialog timeDialog = new MaterialDialog.Builder(SettingsActivity.this)
                        .title("Change the Time")
                        .customView(R.layout.hourglass_date, false)
                        .show();
                ((SeekBar) timeDialog.getCustomView().findViewById(R.id.year)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        sm.setInt(R.string.hourglass_debug_date_year, progress);
                        sm.pushData();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
                ((SeekBar) timeDialog.getCustomView().findViewById(R.id.month)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        sm.setInt(R.string.hourglass_debug_date_month, progress);
                        sm.pushData();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
                ((SeekBar) timeDialog.getCustomView().findViewById(R.id.date)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        sm.setInt(R.string.hourglass_debug_date_date, progress);
                        sm.pushData();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
                return false;
            }
        });
        int[] switches = new int[]{
                R.string.hourglass_ambient,
                R.string.hourglass_analog_smooth_scroll,
                R.string.hourglass_debug_mode
        };
        for(int i: switches) {
            fragment.findPreference(getString(i)).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    sm.pushData();
                    return false;
                }
            });
        }
        fragment.setRefreshTargets(new com.jenzz.materialpreference.Preference[] {
                (com.jenzz.materialpreference.Preference) fragment.findPreference(getString(R.string.hourglass_debug_mode))
        });
    }

    @Override
    public int getBackgroundColor() {
        return R.color.colorPrimary;
    }

    @Override
    public int getPreferencesXml() {
        return R.xml.hourglass_preferences;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        sm.setSyncableSettingsManager(gapi);
        sm.pushData();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
