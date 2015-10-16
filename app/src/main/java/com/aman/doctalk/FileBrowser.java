package com.aman.doctalk;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.artifex.mupdfdemo.MuPDFActivity;

import java.io.File;
import java.util.ArrayList;

public class FileBrowser extends ListActivity {
    ArrayList<String> listItems,listshow;
    String ROOT_PATH1 = "/storage/emulated/0";
    String ROOT_PATH2 = "/storage/sdcard1";
    String parent="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);
        setRoot();
    }

    private void setRoot() {
        listItems = new ArrayList<String>();
        listshow = new ArrayList<String>();

        listItems.add("<-BACK");
        listItems.add(ROOT_PATH1);
        listItems.add(ROOT_PATH2);
        listshow.add("<-");
        listshow.add("Internal Storage");
        listshow.add("SD Card");

        ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,R.layout.file_list_row, listshow);
        setListAdapter(fileList);
    }
    //on clicking any item in the dialog
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        int selectedRow = (int)id;

        if(selectedRow == 0){
            setRoot();
        }else{
            File file = new File(listItems.get(selectedRow));
            if(file.isDirectory()){
                getFiles(file.listFiles());
            }else{
                //opening  pdf files
                if(file.getPath().matches(".*?\\.pdf")){
                    Uri filePath = Uri.parse(file.getPath());
                    Intent openMuPDF = new Intent(FileBrowser.this, MuPDFActivity.class);
                    openMuPDF.setAction(Intent.ACTION_VIEW);
                    openMuPDF.setData(filePath);
                    startActivity(openMuPDF);
                }
                //neither pdf file nor directory
                else{
                    new AlertDialog.Builder(FileBrowser.this)
                            .setTitle("Not a pdf file")
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

    //getting files from the passed path and storing in array list
    private void getFiles(File[] files){
        listItems = new ArrayList<String>();
        listItems.add("<-BACK");
        listshow = new ArrayList<String>();
        listshow.add("<-");

        String path,s="";
        for(File file : files) {
            // String state = Environment.getExternalStorageDirectory();
            path = file.getPath();
            if ((path.contains("storage")) && (!path.contains("usbdisk"))) {
                listItems.add(file.getPath());
                path=file.getPath();
                int l=path.length();
                char c=path.charAt(l-1);
                for(int i=(l-2);c!='/' || i>=0 ;i--)
                {
                    s=c+s;
                }

            listshow.add(s);
            }
        }
        ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,R.layout.file_list_row, listshow);
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
}
