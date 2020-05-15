package com.example.learnandroid.activity.filepersistence;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.learnandroid.R;
import com.example.learnandroid.base.BaseActivity;

public class SharedPreferencesActivity extends BaseActivity {

   /*   1. Context 类中的 getSharedPreferences()方法 此方法接收两个参数，
           第一个参数用于指定 SharedPreferences 文件的名称，如果指定的文件不存在则会创建一个，
           SharedPreferences 文件都是存放在/data/data/<package name>/shared_prefs/目录下的。
           第二个参数用于指定操作模式，主要有两种模式可以选 择，MODE_PRIVATE 和 MODE_MULTI_PROCESS。
           MODE_PRIVATE 仍然是默认的操 作模式，和直接传入 0 效果是相同的，表示只有当前的应用程序才可以
           对这个 SharedPreferences 文件进行读写。MODE_MULTI_PROCESS 则一般是用于会有多个进程中
           对同一个 SharedPreferences 文件进行读写的情况。类似地，MODE_WORLD_READABLE 和 MODE_WORLD_WRITEABLE
           这两种模式已在 Android 4.2 版本中被废弃。
        2. Activity 类中的 getPreferences()方法这个方法和 Context 中的 getSharedPreferences()方法很相似，
           不过它只接收一个操作模式参数，因为使用这个方法时会自动将当前活动的类名作为 SharedPreferences 的文 件名。
        3. PreferenceManager 类中的 getDefaultSharedPreferences()方法这是一个静态方法，它接收一个 Context 参数，
           并自动使用当前应用程序的包名作 为前缀来命名 SharedPreferences 文件。得到了 SharedPreferences 对象之后，
           就可以开始向 SharedPreferences 文件中存储数据了，
        主要可以分为三步实现。
        1. 调用 SharedPreferences 对象的 edit()方法来获取一个 SharedPreferences.Editor 对象。
        2. 向 SharedPreferences.Editor 对象中添加数据，比如添加一个布尔型数据就使用
           putBoolean 方法，添加一个字符串则使用 putString()方法，以此类推。
        3. 调用 commit()方法将添加的数据提交，从而完成数据存储操作。
*/


    private Button saveData;
    private Button restoreData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);
        restoreData = (Button) findViewById(R.id.restore_data);
        saveData = (Button) findViewById(R.id.save_data);

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putString("name", "Tom");
                editor.putInt("age", 28);
                editor.putBoolean("married", false);
                editor.commit();

            }
        });
        restoreData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String name = pref.getString("name", "");
                int age = pref.getInt("age", 0);
                boolean married = pref.getBoolean("married", false);

                Log.d("Test", "name is " + name);
                Log.d("Test", "age is " + age);
                Log.d("Test", "married is " + married);

            }
        });


    }
}
