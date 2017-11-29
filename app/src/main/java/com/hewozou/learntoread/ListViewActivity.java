package com.hewozou.learntoread;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ListViewActivity extends Activity {
    ArrayList<String> list;
    Intent intent;
    ListView listv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list);
        listv = findViewById(R.id.ShowAll);
        intent = getIntent();
        list = intent.getStringArrayListExtra("name");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.listitem, R.id.textItem, list);
        listv.setAdapter(adapter);
        listv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("To", list.get(position));
                ListViewActivity.this.setResult(1, intent);
                ListViewActivity.this.finish();
            }
        });
    }
}
