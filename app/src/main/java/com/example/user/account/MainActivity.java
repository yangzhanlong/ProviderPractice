package com.example.user.account;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyOpenHelper myOpenHelper = new MyOpenHelper(getApplicationContext());
        SQLiteDatabase db = myOpenHelper.getReadableDatabase();

        Cursor cursor = db.query("info", null, null, null, null, null, null);
        if (cursor!=null && cursor.getCount()>0) {
            while(cursor.moveToNext()){

                String name = cursor.getString(1);
                String phone = cursor.getString(2);

                System.out.println("name"+name+"----"+phone);
            }
        }
    }
}
