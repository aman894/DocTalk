package com.aman.doctalk;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.artifex.mupdfdemo.MuPDFActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileBrowser extends Activity implements AdapterView.OnItemClickListener{
    ArrayList<String> listItems,listshow;
    String ROOT_PATH1 = "/storage/emulated/0";
    String ROOT_PATH2 = "/storage/sdcard1";
    private ListView listView1;
    List<list> rowitems;
    static int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);
        setRoot();
    }

    private void setRoot() {
        listItems = new ArrayList<String>();
        listItems.add("<-BACK");
        listItems.add(ROOT_PATH1);
        listItems.add(ROOT_PATH2);
         String[] titles=new String[]{"","Internal storage","SD Card"};
         Integer[] images={R.drawable.back,R.drawable.file,R.drawable.file};

        rowitems = new ArrayList<list>();
        for (int i = 0; i < titles.length; i++) {
            list item = new list(images[i], titles[i]);
            rowitems.add(item);
        }

        listView1 = (ListView) findViewById(R.id.list);
        listAdapter adapter = new listAdapter(this,
                R.layout.file_list_row, rowitems);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(this);
    }
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        int selectedRow = (int)id;

        if(selectedRow == 0){
            setRoot();
        }else{
            File file = new File(listItems.get(selectedRow));

            if(file.isDirectory()){
                k=0;
                getFiles(file.listFiles());
            }else {
                //opening  pdf files
                if (file.getPath().matches(".*?\\.pdf")) {
                    try {
                        Uri filePath = Uri.parse(file.getPath());
                        Intent openMuPDF = new Intent(FileBrowser.this, MuPDFActivity.class);
                        openMuPDF.setAction(Intent.ACTION_VIEW);
                        openMuPDF.setData(filePath);
                        startActivity(openMuPDF);
                    }


                    catch (Exception e) {
                        e.printStackTrace();
                    }
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
        listItems.add("<-");
        listshow = new ArrayList<String>();
        listshow.add(" ");


        for(File file : files) {
            String path,s="";
            path = file.getPath();
            if ((path.contains("storage")) && (!path.contains("usbdisk"))) {
                listItems.add(file.getPath());
                path=file.getPath();
                 int l=path.length();
                 char c=path.charAt(l-1);
                for(int i=(l-2);c!='/' ;i--)
                {
                    s=c+s;
                    c=path.charAt(i);

                }
                k++;
                listshow.add(s);
               }
        }
        String[] titles=new String[k];
        Integer[] images=new Integer[k];
        for(int i=0;i<k;i++)
        {
            titles[i]=listshow.get(i);
            if(listshow.get(i)==" ")
                images[i]=R.drawable.back;
            else if(listshow.get(i).contains(".pdf"))
                images[i]=R.drawable.pdf;
            else
                images[i]=R.drawable.file;
        }
        rowitems = new ArrayList<list>();
        for (int i = 0; i < titles.length; i++) {
            list item = new list(images[i], titles[i]);
            rowitems.add(item);
        }

        listView1 = (ListView) findViewById(R.id.list);
        listAdapter adapter = new listAdapter(this,
                R.layout.file_list_row, rowitems);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(this);
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
