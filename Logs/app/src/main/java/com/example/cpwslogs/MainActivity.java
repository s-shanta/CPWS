package com.example.cpwslogs;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cpwslogs.databases.DBHelper;
import com.example.cpwslogs.fragments.HomeFragment;
import com.example.cpwslogs.fragments.LogListFragment;
import com.example.cpwslogs.fragments.ProfileFragment;
import com.example.cpwslogs.fragments.ShedEditFragment;
import com.example.cpwslogs.interfaces.OnNavigationButtonClickListener;
import com.example.cpwslogs.interfaces.OnShedItemClickListener;
import com.example.cpwslogs.models.ShedLog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements OnShedItemClickListener,
        OnNavigationButtonClickListener {

    public static List<ShedLog> logs = new ArrayList<>();
    public static boolean isDataSaved = true;

    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DBHelper(MainActivity.this);

        logs.addAll(helper.getAllLogs());

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.mainActivityFragmentHolder, new HomeFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {

        if (isDataSaved) {

            logs.clear();
            super.onBackPressed();


        } else {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Database not saved")
                    .setMessage("Save entries to DB first?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for (ShedLog log : logs) {
                                helper.insertIntoDb(log);
                            }
                            isDataSaved = true;
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isDataSaved = true;
                            onBackPressed();
                        }
                    })
                    .create()
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_send:

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Are you sure? This will delete all entries.")
                        .setMessage("Save entries first?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (helper.deleteAllLogs())
                                    Toast.makeText(MainActivity.this, "All data deleted", Toast.LENGTH_SHORT).show();
                                logs.clear();
                                isDataSaved = true;
                            }
                        })
                        .setNegativeButton("CANCEL", null)
                        .create()
                        .show();
                return true;
            case R.id.action_profile:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainActivityFragmentHolder, new ProfileFragment())
                        .commit();
                return true;
            case R.id.action_save:
                for (ShedLog log : logs) {
                    helper.insertIntoDb(log);
                }
                isDataSaved = true;
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onShedItemClicked(int index) {
        Fragment fragment = ShedEditFragment.newInstance(index);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainActivityFragmentHolder, fragment)
                .commit();
    }

    @Override
    public void onHomeButtonClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainActivityFragmentHolder, new HomeFragment())
                .commit();
    }

    @Override
    public void onPreviousButtonClicked() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainActivityFragmentHolder, new HomeFragment())
                .commit();
    }

    @Override
    public void onNextButtonClicked() {

    }

    @Override
    public void onShowLogListButtonClicked(int shedIndex) {
        Fragment fragment = LogListFragment.newInstance(shedIndex);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainActivityFragmentHolder, fragment)
                .commit();
    }
}
