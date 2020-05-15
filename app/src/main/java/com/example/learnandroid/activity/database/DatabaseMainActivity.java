package com.example.learnandroid.activity.database;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.learnandroid.R;
import com.example.learnandroid.base.BaseActivity;
import com.example.learnandroid.database.book.MyDatabaseHelper;

public class DatabaseMainActivity extends BaseActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_main);
        //dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,1);

        //升级数据库  修改版本号为2
        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
        Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });
        //添加数据
        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        //更新数据
        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        //删除数据
        Button deleteButton = (Button) findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
        // 查询数据
        Button queryButton = (Button) findViewById(R.id.query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //使用事务
        Button replaceData = (Button) findViewById(R.id.replace_data);
        replaceData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // 开启事务
                db.beginTransaction();
                try {
                    db.delete("Book", null, null);
                    if (true) {
                     // 在这里手动抛出一个异常，让事务失败
                        throw new NullPointerException();
                    }
                    ContentValues values = new ContentValues();
                    values.put("name", "Game of Thrones");
                    values.put("author", "George Martin");
                    values.put("pages", 720);
                    values.put("price", 20.85);
                    db.insert("Book", null, values);
                    db.setTransactionSuccessful(); // 事务已经执行成功
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.endTransaction(); // 结束事务
                }


            }
        });

    }

    public void add() {
        /*第一个参数是表名，我们希望向哪张表里添加数据，这里就传入该表的名字。
        第二个 参数用于在未指定添加数据的情况下给某些可为空的列自动赋值 NULL，一般我们用不到这 个功能，直接传入 null 即可。
        第三个参数是一个 ContentValues 对象，它提供了一系列的 put() 方法重载，用于向 ContentValues 中添加数据，
        只需要将表中的每个列名以及相应的待添加 数据传入即可。*/

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        // 开始组装第一条数据
        values.put("name", "The Da Vinci Code");
        values.put("author", "Dan Brown");
        values.put("pages", 454);
        values.put("price", 16.96);
        // 插入第一条数据
        db.insert("Book", null, values);
        values.clear();
        // 开始组装第二条数据
        values.put("name", "The Lost Symbol");
        values.put("author", "Dan Brown");
        values.put("pages", 510);
        values.put("price", 19.95);
        db.insert("Book", null, values); // 插入第二条数据


        //使用SQL
        //db.execSQL("insert into Book (name, author, pages, price) values (?, ?, ?, ?)", new String[]{"The Da Vinci Code", "Dan Brown", "454", "16.96"});
        //db.execSQL("insert into Book (name, author, pages, price) values (?, ?, ?, ?)", new String[]{"The Lost Symbol", "Dan Brown", "510", "19.95"});

    }

    public void update() {
       /* 第一个参数和 insert()方法一样，也是表名，在这里指定去更新哪张表里的数据。
        第二个参数是 ContentValues 对象，要把更新数据在这里组装进去。
        第三、第四个参数 用于去约束更新某一行或某几行中的数据，不指定的话默认就是更新所有行。*/
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("price", 10.96);
        db.update("Book", values, "name=?", new String[]{"The Da Vinci Code"});

        //使用SQL
        //db.execSQL("update Book set price = ? where name = ?", new String[] { "10.99", "The Da Vinci Code" });

    }

    public void delete() {
        /*这个方法接收三个参数，
        第一 个参数仍然是表名，这个已经没什么好说的了，
        第二、第三个参数又是用于去约束删除某一 行或某几行的数据，不指定的话默认就是删除所有行。*/

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Book", "pages>?", new String[]{"500"});


        //db.execSQL("delete from Book where pages > ?", new String[] { "500" });
    }

    public void query() {
        /*SQLiteDatabase 中还提供了一个 query()方法用于对数据进行查询。 这个方法的参数非常复杂，
        最短的一个方法重载也需要传入七个参数。那我们就先来看一下 这七个参数各自的含义吧，
        第一个参数不用说，当然还是表名，表示我们希望从哪张表中查 询数据。
        第二个参数用于指定去查询哪几列，如果不指定则默认查询所有列。
        第三、第四个 参数用于去约束查询某一行或某几行的数据，不指定则默认是查询所有行的数据。
        第五个参 数用于指定需要去 group by 的列，不指定则表示不对查询结果进行 group by 操作。
        第六个参 数用于对 group by 之后的数据进行进一步的过滤，不指定则表示不进行过滤。
        第七个参数用 于指定查询结果的排序方式，不指定则表示使用默认的排序方式。*/
/*

        query()方法参数             对应 SQL 部分                        描述
            table               from table_name                    指定查询的表名
           columns              select column1, column2            指定查询的列名
          selection             where column = value               指定 where 的约束条件
        selectionArgs                 -                            为 where 中的占位符提供具体的值
           groupBy              group by column                    指定需要 group by 的列
           having               having column = value              对 group by 后的结果进一步约束
           orderBy              order by column1, column2          指定查询结果的排序方式
*/


       /*
        eg:
        查询完之后就得到了一个Cursor对象，接着我们调用它的moveToFirst()方法将数
        据的指针移动到第一行的位置，然后进入了一个循环当中，去遍历查询到的每一行数据。
        在 这个循环中可以通过 Cursor 的 getColumnIndex()方法获取到某一列在表中对应的位置索引，
        然后将这个索引传入到相应的取值方法中，就可以得到从数据库中读取到的数据了。接着我 们使用 Log
        的方式将取出的数据打印出来，借此来检查一下读取工作有没有成功完成。最后 别忘了调用 close()方法来关闭 Cursor。
        */


        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 查询Book表中所有的数据
        Cursor cursor = db.query("Book", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {

            do {
                // 遍历Cursor对象，取出数据并打印
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                Log.d("DatabaseMainActivity", "book name is " + name);
                Log.d("DatabaseMainActivity", "book author is " + author);
                Log.d("DatabaseMainActivity", "book pages is " + pages);
                Log.d("DatabaseMainActivity", "book price is " + price);


            } while (cursor.moveToNext());


        }
        cursor.close();


        //db.rawQuery("select * from Book", null);


    }

}
