package com.felkertech.sample.hourglass;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.felkertech.hourglass.HourglassLaunchActivity;
import com.felkertech.hourglass.SettingsActivity;

public class MainActivity extends HourglassLaunchActivity {

    @Override
    public String getIssueTracker() {
        return "https://github.com/Fleker/Hourglass";
    }

    @Override
    public Class getSettingsActivity() {
        return SettingsActivity.class;
    }

    @Override
    public Drawable getWatchfacePreview() {
        return getResources().getDrawable(R.drawable.common_google_signin_btn_icon_dark);
    }
}
