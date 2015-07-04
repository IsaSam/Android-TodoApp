package com.codepath.todoapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class TodoAppActivity extends ActionBarActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView list_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_app);
        list_items = (ListView) findViewById(R.id.list_items);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        list_items.setAdapter(itemsAdapter);
        RemoveItems();
    }

    public void AddItem(View v){
        EditText edit_item = (EditText) findViewById(R.id.edit_item);
        String text_idem = edit_item.getText().toString();
        itemsAdapter.add(text_idem);
        edit_item.setText("");
        writeItems();
    }

    private void RemoveItems(){
        list_items.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );
    }

    private void readItems(){
        File FileDir =  getFilesDir();
        File TodoFile = new File(FileDir, "Todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(TodoFile));
        }catch (IOException e){
            items = new ArrayList<String>();
        }
    }
    private  void writeItems(){
        File FileDir = getFilesDir();
        File TodoFile = new File(FileDir, "Todo.txt");
        try{
            FileUtils.writeLines(TodoFile, items);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_app, menu);
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
