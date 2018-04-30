package com.example.derek.myapplication;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Date currentTime = Calendar.getInstance().getTime();
                TextView textView = (TextView) findViewById(R.id.stateText);
                textView.setText(currentTime.toString());

                String display = "";
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                File directory = new File(path);
                File[] files = directory.listFiles();
                for (int i = 0; i < files.length; i++) {
                    display += files[i] + "\n";
                }
/*
                try {
                    KeyStore keystore = KeyStore.getInstance("PKCS12");
                    FileInputStream fis = new FileInputStream(certFile);
                    keystore.load(fis, "".toCharArray());
                } catch (Exception e) {
                    e.printStackTrace();
                }
*/
                if (isExternalStorageReadable())
                    textView.setText(
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + " " +
                                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + " " +
                                    display
                    );
                else textView.setText("NO READ");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
