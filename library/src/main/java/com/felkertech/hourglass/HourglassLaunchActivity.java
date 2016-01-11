package com.felkertech.hourglass;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.felkertech.hourglass.R;
import com.felkertech.hourglassc.ConnectionUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

public abstract class HourglassLaunchActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleApiClient gapi;

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

        findViewById(R.id.select_watchface).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wearIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.wearable.app");
                startActivity(wearIntent);
            }
        });

        ((ImageView) findViewById(R.id.watchface)).setImageDrawable(getWatchfacePreview());

        gapi = new GoogleApiClient.Builder(this)
                .addApiIfAvailable(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        getSupportActionBar().setTitle(R.string.app_name);
    }

    public void onResume() {
        super.onResume();
        gapi.connect();
    }

    public void onPause() {
        super.onPause();
        //Send a notification to the watch for quick opening
        ConnectionUtils.NodeManager nodeManager = new ConnectionUtils.NodeManager(gapi, "", "/mobile-listener-service");
        nodeManager.sendMessage(getString(R.string.hourglass_message_mobile_stop));
        gapi.disconnect();
    }

    public abstract String getIssueTracker();
    public abstract Class getSettingsActivity();
    public abstract Drawable getWatchfacePreview();
    public int getLayout() {
        return R.layout.activity_generic_main;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        ConnectionUtils.NodeManager nodeManager = new ConnectionUtils.NodeManager(gapi, "", "/mobile-listener-service");
        nodeManager.sendMessage(getString(R.string.hourglass_message_mobile_resume));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
