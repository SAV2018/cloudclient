package ru.sav.cloudclient.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;

import ru.sav.cloudclient.R;
import ru.sav.cloudclient.view.feed.FeedFragment;
import ru.sav.cloudclient.view.profile.ProfileFragment;
import ru.sav.cloudclient.view.search.SearchFragment;


public class MainActivity extends MvpAppCompatActivity {
    private static final String TAG = "MainActivity";
    private MenuItem itemConnection; // пункт меню Connection в Bottom Navigation
    private boolean connectionState; // состояние соединения с сервером
    FragmentManager fragmentManager;
    private int menuPosition;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        if (bundle != null) {
            menuPosition = bundle.getInt("menuPosition", 0);
            Log.d(TAG, "onCreate: bundle.menuPosition = " + menuPosition);
        } else {
            menuPosition = 0;
        }

        init(bundle);
    }

    private void init(Bundle bundle) {
        connectionState = false;
        fragmentManager = getSupportFragmentManager();
        if (bundle == null) changeFragment(menuPosition);

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
        Fragment fragment;

        Log.d(TAG, "changeFragment: ");

        this.menuPosition = position;
        switch (position) {
            case 1:
                fragment = new ProfileFragment();
                break;
            case 2:
                fragment = new FeedFragment();
                break;
            case 3:
                fragment = new SearchFragment();
                break;
            default:
                fragment = new StartFragment();
        }
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    void drawConnectionState() { // показываем состояние соединения
        String message;
        Integer resource;

        if (connectionState) {
            message = "Connecting to cloud server…";
            resource = R.drawable.ic_cloud_24;
        } else {
            message = "Disconnecting…";
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

    public static void showErrMsg(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("ERROR: ")
                .setMessage(message)
                .setIcon(R.drawable.ic_error_24)
                .setCancelable(false)
                .setNegativeButton(R.string.dialog_ok_button, (dialog, id) -> dialog.cancel());
        builder.create().show();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("menuPosition", menuPosition);

        Log.d(TAG, "onSaveInstanceState: bundle.menuPosition = " + menuPosition);
    }
}