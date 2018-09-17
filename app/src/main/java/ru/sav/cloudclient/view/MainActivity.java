package ru.sav.cloudclient.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;

import ru.sav.cloudclient.R;
import ru.sav.cloudclient.view.feed.FeedFragment;
import ru.sav.cloudclient.view.profile.ProfileFragment;
import ru.sav.cloudclient.view.search.SearchFragment;


public class MainActivity extends MvpAppCompatActivity {
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
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_connection:
                            connectionState = !connectionState; // меняем состояние

                            drawConnectionState();
                            break;
                        case R.id.action_feed:
                            changeFragment(2);
                            break;
                        case R.id.action_profile:
                            changeFragment(1);
                            break;
                        case R.id.action_search:
                            changeFragment(3);
                            break;
                    }
                    return true;
                });
    }

    private void changeFragment(int position) {
        Fragment fragment = null;


        if (position == 1) {
            fragment = new ProfileFragment();
        }
        if (position == 2) {
            fragment = new FeedFragment();
        }
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

    public boolean isInternetConnection() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = null;
        if (connectivityManager != null) {
            networkinfo = connectivityManager.getActiveNetworkInfo();
        }

        boolean result = (networkinfo != null && networkinfo.isConnected());

        if (!result) {
            Toast.makeText(this, R.string.msg_no_internet_connection,
                    Toast.LENGTH_LONG).show();
        }
        return result;
    }
}
