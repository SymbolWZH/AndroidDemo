package com.origin.contactstest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    // 定义 ContentProvider 的 authorities
    private static final String AUTHORITY = "com.example.mycontentprovider";

    // 定义 UriMatcher 用于匹配 Uri
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // 定义 Uri 匹配码
    private static final int DATA_TABLE = 1;

    // 初始化 UriMatcher
    static {
        uriMatcher.addURI(AUTHORITY, "data", DATA_TABLE);
    }

    @Override
    public boolean onCreate() {
        // 初始化 ContentProvider，在这里可以进行一些初始化工作
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // 处理查询操作
        switch (uriMatcher.match(uri)) {
            case DATA_TABLE:
                // 处理针对 "data" 表的查询
                // 实现查询逻辑并返回 Cursor
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        // 返回指定 Uri 的 MIME 类型
        switch (uriMatcher.match(uri)) {
            case DATA_TABLE:
                return "vnd.android.cursor.dir/vnd." + AUTHORITY;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // 处理插入操作
        switch (uriMatcher.match(uri)) {
            case DATA_TABLE:
                // 处理针对 "data" 表的插入
                // 实现插入逻辑并返回新记录的 Uri
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // 处理删除操作
        switch (uriMatcher.match(uri)) {
            case DATA_TABLE:
                // 处理针对 "data" 表的删除
                // 实现删除逻辑并返回受影响的行数
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // 处理更新操作
        switch (uriMatcher.match(uri)) {
            case DATA_TABLE:
                // 处理针对 "data" 表的更新
                // 实现更新逻辑并返回受影响的行数
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return 0;
    }
}
