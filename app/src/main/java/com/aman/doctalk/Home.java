package com.aman.doctalk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

public class Home extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener,View.OnClickListener{
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private android.support.design.widget.FloatingActionButton fabBrowse;
    AlertDialog.Builder fileBrowserDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fabBrowse = (android.support.design.widget.FloatingActionButton)
                findViewById(R.id.fabBrowse);
        fabBrowse.setOnClickListener(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        //setUpDialog();
    }

    private void setUpDialog() {
        fileBrowserDialog = new AlertDialog.Builder(this);
        fileBrowserDialog.setTitle("Documents");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_singlechoice);
        adapter.add("File 1");
        adapter.add("File 2");
        adapter.add("File 3");
        adapter.add("File 4");
        adapter.add("File 5");
        adapter.add("File 6");
        adapter.add("File 7");
        adapter.add("File 8");
        adapter.add("File 9");
        adapter.add("File 0");

    fileBrowserDialog.setNegativeButton("Cancel",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int position) {
                    dialog.dismiss();
                }
            }
    );

        fileBrowserDialog.setAdapter(adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        String str = adapter.getItem(position);
                        AlertDialog.Builder inner = new AlertDialog.Builder(Home.this);
                        inner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        inner.show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    @Override
    public void onDrawerItemSelected(View view, int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabBrowse:
                Intent openFileBrowser = new Intent(Home.this,FileBrowser.class);
                startActivity(openFileBrowser);
                break;
        }
    }
}
