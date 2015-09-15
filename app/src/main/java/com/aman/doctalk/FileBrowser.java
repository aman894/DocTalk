package com.aman.doctalk;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class FileBrowser extends ListActivity {
    ArrayList<String> listItems;
    String rootPath1 = "/storage/emulated/0";
    String rootPath2 = "/storage/sdcard1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);
        setRoot();
    }

    private void setRoot() {
        listItems = new ArrayList<String>();
        listItems.add("..");
        listItems.add(rootPath1);
        listItems.add(rootPath2);
        ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,R.layout.file_list_row, listItems);
        setListAdapter(fileList);
    }


    private void getFiles(File[] files){
        listItems = new ArrayList<String>();
        listItems.add("..");
        for(File file : files){
            // String state = Environment.getExternalStorageDirectory();
            String path=file.getPath();
            if((path.contains("storage"))&&(!path.contains("usbdisk")))
                listItems.add(file.getPath());

        }
        ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,R.layout.file_list_row, listItems);
        setListAdapter(fileList);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_browser, menu);
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
    protected void onListItemClick(ListView l, View v, int position, long id){
        int selectedRow = (int)id;
        if(selectedRow == 0){
            //getFiles(new File("/storage").listFiles());
            setRoot();
        }else{
            File file = new File(listItems.get(selectedRow));
            if(file.isDirectory()){
                getFiles(file.listFiles());
            }else{
                new AlertDialog.Builder(FileBrowser.this)
                        .setTitle("This file is not a directory")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int button){
                                //do nothing
                            }
                        })
                        .show();
            }
        }
    }
}
