package ru.sav.cloudclient.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import ru.sav.cloudclient.R;
import ru.sav.cloudclient.view.fragment.SearchFragment;
import rx.Observable;
import rx.Observer;


public class MainActivity extends AppCompatActivity {
    private MenuItem itemConnection; // пункт меню Connection в Bottom Navigation
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
                        case R.id.action_search:
                            changeFragment(3);
                            break;
                    }
                    return true;
                }
            });
    }

    private void changeFragment(int position) {
        Fragment fragment = null;

        if (position == 3) {
            fragment = new SearchFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    void drawConnectionState() { // показываем состояние соединения
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
