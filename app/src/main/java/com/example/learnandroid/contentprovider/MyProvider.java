package com.example.learnandroid.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyProvider extends ContentProvider {

    public static final int TABLE1_DIR = 0;
    public static final int TABLE1_ITEM = 1;
    public static final int TABLE2_DIR = 2;
    public static final int TABLE2_ITEM = 3;

    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.learnandroid.contentprovider", "table1", TABLE1_DIR);
        uriMatcher.addURI("com.example.learnandroid.contentprovider", "table1/#", TABLE1_ITEM);
        uriMatcher.addURI("com.example.learnandroid.contentprovider", "table2", TABLE2_DIR);
        uriMatcher.addURI("com.example.learnandroid.contentprovider", "table2/#", TABLE2_ITEM);
    }
    /*
     * 初始化内容提供器的时候调用。通常会在这里完成对数据库的创建和升级等操作，
     * 返回 true 表示内容提供器初始化成功，返回 false 则表示失败。注意，
     * 只有当存在 ContentResolver 尝试访问我们程序中的数据时，内容提供器才会被初始化。
     * */

    @Override
    public boolean onCreate() {
        return false;
    }

    /*
     * 从内容提供器中查询数据。使用 uri 参数来确定查询哪张表，projection 参数用于确 定查询哪些列，
     * selection 和 selectionArgs 参数用于约束查询哪些行，sortOrder 参数用于 对结果进行排序，
     * 查询的结果存放在 Cursor 对象中返回。
     * */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                // 查询table1表中的所有数据

                break;
            case TABLE1_ITEM:
                // 查询table1表中的单条数据

                break;
            case TABLE2_DIR:
                // 查询table2表中的所有数据

                break;
            case TABLE2_ITEM:
                // 查询table2表中的单条数据

                break;
            default:
                break;
        }

        return null;
    }

    /*
     * 根据传入的内容 URI 来返回相应的 MIME 类型。 可以看到，几乎每一个方法都会带有 Uri 这个参数，
     * 这个参数也正是调用 ContentResolver的增删改查方法时传递过来的。而现在，我们需要对传入的 Uri 参数进行解析，
     * 从中分析出 调用方期望访问的表和数据。
     * */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.app.provider.table1";
            case TABLE1_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.app.provider.table1";
            case TABLE2_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.app.provider.table2";
            case TABLE2_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.app.provider.table2";
            default:
                break;
        }
        return null;
    }


    /*
     * 向内容提供器中添加一条数据。使用 uri 参数来确定要添加到的表，待添加的数据 保存在 values 参数中。
     * 添加完成后，返回一个用于表示这条新记录的 URI。
     *
     * */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    /*
     * 从内容提供器中删除数据。使用 uri 参数来确定删除哪一张表中的数据，
     * selection 和 selectionArgs 参数用于约束删除哪些行，被删除的行数将作为返回值返回。
     *
     * */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    /*
     * 更新内容提供器中已有的数据。使用 uri 参数来确定更新哪一张表中的数据，新数 据保存在 values 参数中，
     * selection 和 selectionArgs 参数用于约束更新哪些行，受影响的 行数将作为返回值返回。
     *
     * */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
