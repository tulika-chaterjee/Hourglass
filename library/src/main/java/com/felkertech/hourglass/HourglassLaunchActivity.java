package com.felkertech.hourglass;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.felkertech.hourglass.R;

public abstract class HourglassLaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        if(getIssueTracker().isEmpty()) {
            findViewById(R.id.issue_tracker).setVisibility(View.GONE);
        } else {
            findViewById(R.id.issue_tracker).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(getIssueTracker()));
                    startActivity(i);
                }
            });
        }

        if(getSettingsActivity() == null) {
            findViewById(R.id.settings).setVisibility(View.GONE);
        } else {
            findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(HourglassLaunchActivity.this, getSettingsActivity());
                    startActivity(i);
                }
            });
        }

        ((ImageView) findViewById(R.id.watchface)).setImageDrawable(getWatchfacePreview());
    }

    public abstract String getIssueTracker();
    public abstract Class getSettingsActivity();
    public abstract Drawable getWatchfacePreview();
    public int getLayout() {
        return R.layout.activity_generic_main;
    }
}
