package com.hewozou.learntoread;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    SQLiteDatabase database;
    Mysql sql;//工具类
    TextView et;
    Cursor cursor;
    int index = -1;
    String str;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.TextView);
        sql = new Mysql(this, "1.db", 1);
        database = sql.getReadableDatabase();
        cursor = database.rawQuery("select * from my", null);
    }

    public void upChar(View view) {
        //TODO 保存字符
        final EditText ed = new EditText(this);
        ed.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});//设置输入字符数量为1
        new AlertDialog.Builder(this).setTitle("插入")
                .setIcon(android.R.drawable.ic_menu_manage)
                .setView(ed)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String etd = ed.getText().toString();
                        et.setText(etd);
                        try {
                            if (!ed.getText().toString().equals("")) {
                                String insertsql = "insert into my(China) values('" + etd + "')";
                                database.execSQL(insertsql);
                                Toast.makeText(MainActivity.this, "存储成功!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "数据无效!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(MainActivity.this, "数据已存在!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();

    }

    public void previousChar(View view) {
        //TODO 上一条
        et = findViewById(R.id.TextView);
        if (!(index < 0)) {
            index--;
        }
        if (cursor.moveToPosition(index)) {
            str = cursor.getString(0);
            et.setText(str);
        } else {
            Toast.makeText(MainActivity.this, "已经到达最前面!", Toast.LENGTH_SHORT).show();
        }
    }

    public void nextChar(View view) {
        //TODO 下一条
        et = findViewById(R.id.TextView);

        if (index != cursor.getCount()) {
            index++;
        }
        //true则重新获取结果集并根据标识继续移动游标
        else {
            cursor = database.rawQuery("select * from my", null);
        }
        //移动游标的位置
        if (cursor.moveToPosition(index)) {
            str = cursor.getString(0);
            et.setText(str);
        }
        //游标到达末尾
        else {
            Toast.makeText(MainActivity.this, "已经是最后一条数据!", Toast.LENGTH_SHORT).show();
        }
    }

    public void ShowAll(View view) {
        all();
        Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
        intent.putStringArrayListExtra("name", list);
        MainActivity.this.startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == 1) {
            et.setText(data.getStringExtra("To"));
        }
    }

    public void all() {
        cursor = database.rawQuery("select * from my", null);
        list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}