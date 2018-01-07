package com.hewozou.learntoread;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class ListViewActivity extends Activity {
    ArrayList<String> list;
    Intent intent;
    ListView listv;
    SQLiteDatabase database;
    Mysql sql;//工具类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sql = new Mysql(this, "1.db", 1);
        database = sql.getReadableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list);
        listv = findViewById(R.id.ShowAll);
        intent = getIntent();
        list = intent.getStringArrayListExtra("name");
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.listitem, R.id.textItem, list);
        listv.setAdapter(adapter);
        //放入数据
        listv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("To", list.get(position));
                ListViewActivity.this.setResult(1, intent);
                ListViewActivity.this.finish();
            }
        });
        listv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListViewActivity.this);
                builder.setMessage("删除当前汉字？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String sqlText = list.get(position);

                                String deletesql = "delete from my where China = '" + sqlText + "'";

                                database.execSQL(deletesql);

                                Toast.makeText(ListViewActivity.this, "删除" + "" + "成功", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .create()
                        .show();


                return true;
            }
        });
        adapter.notifyDataSetChanged();

    }
}
