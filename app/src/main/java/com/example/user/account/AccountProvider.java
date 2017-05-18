package com.example.user.account;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class AccountProvider extends ContentProvider{
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int QUERYSUCESS = 0;
    private static final int INSERTSUCESS = 1;
    private static final int DELETESUCESS = 2;
    private static final int UPDATESUCESS = 3;
    private MyOpenHelper mMyOpenHelper;

    static {
        // 参数1 authority 这个参数和清单文件里定义的一样
        // 参数2 path 组成uri content://com.account.provider/query
        // 参数3 code 匹配码
        uriMatcher.addURI("com.account.provider", "query", QUERYSUCESS);
        uriMatcher.addURI("com.account.provider", "insert", INSERTSUCESS);
        uriMatcher.addURI("com.account.provider", "delete", DELETESUCESS);
        uriMatcher.addURI("com.account.provider", "update", UPDATESUCESS);
    }

    @Override
    public boolean onCreate() {
        mMyOpenHelper = new MyOpenHelper(getContext());

        return false;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int code = uriMatcher.match(uri);
        if (code == QUERYSUCESS) {
            //说明路径匹配成功  把query方法给实现   数据库的查询方法  对数据库进行查询的操作  想操作数据库必须的获得sqltiedatabase对象
            SQLiteDatabase db = mMyOpenHelper.getReadableDatabase();
            Cursor cursor = db.query("info", projection, selection, selectionArgs, null, null, sortOrder);

            //数据库被人操作 了 自己发送一条消息
            getContext().getContentResolver().notifyChange(uri, null);

            return cursor;
        } else {
            //路径不匹配
            throw new IllegalArgumentException("Query路径不匹配");
        }
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int code = uriMatcher.match(uri);
        if (code == INSERTSUCESS) {
            SQLiteDatabase db = mMyOpenHelper.getReadableDatabase();
            //返回值代表新 插入行数的id
            long id = db.insert("info", null, values);

            Uri uri2 = Uri.parse("com.account.provider/" + id);
            return uri2;
        } else {
            //路径不匹配
            throw new IllegalArgumentException("Insert路径不匹配");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int code = uriMatcher.match(uri);
        if (code == DELETESUCESS) {
            SQLiteDatabase db = mMyOpenHelper.getReadableDatabase();

            //代表影响的行数
            int delete = db.delete("info", selection, selectionArgs);
            return delete;
        } else {
            //路径不匹配
            throw new IllegalArgumentException("Delete路径不匹配");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int code = uriMatcher.match(uri);
        if (code == UPDATESUCESS) {
            SQLiteDatabase db = mMyOpenHelper.getReadableDatabase();
            int update = db.update("info", values, selection, selectionArgs);
            return update;
        } else {
            //路径不匹配
            throw new IllegalArgumentException("Update路径不匹配");
        }
    }
}
