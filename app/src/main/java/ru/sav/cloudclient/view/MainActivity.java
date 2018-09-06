package ru.sav.cloudclient.view;

import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.util.TimeUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import ru.sav.cloudclient.R;

public class MainActivity extends AppCompatActivity {
    private MenuItem itemConnection; // пункт Connection в Bottom Navigation
    private boolean connectionState; // состояние соединения с Cloud Server

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        connectionState = false;

        final BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        itemConnection = bottomNavigation.getMenu().findItem(R.id.action_connection);

        bottomNavigation.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_connection:
                            connectionState = !connectionState; // меняем состояние

                            drawConnectionState();
                            break;
                        case R.id.action_upload:

                            break;
                        case R.id.action_download:

                            break;
                    }
                    return true;
                }
            });
    }

    void drawConnectionState() {
        String message;
        Integer resource;

        if (connectionState) {
            message ="Connecting to cloud server…";
            resource = R.drawable.ic_cloud_24;
        } else {
            message ="Disconnecting…";
            resource = R.drawable.ic_cloud_off_24;
        }

        Toast toast = Toast.makeText(getApplicationContext(),
                message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();

        itemConnection.setIcon(resource);
    }
}
