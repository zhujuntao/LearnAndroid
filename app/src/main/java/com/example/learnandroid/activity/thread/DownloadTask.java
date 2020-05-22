package com.example.learnandroid.activity.thread;

import android.os.AsyncTask;
import android.widget.Toast;

public class DownloadTask extends AsyncTask<Void, Integer, Boolean> {

    @Override
    protected void onPreExecute() {
        //progressDialog.show(); // 显示进度对话框

    }

    @Override
    protected Boolean doInBackground(Void... voids) {
//        try {
//            while (true) {
//                // int downloadPercent = doDownload(); // 这是一个虚构的方法 publishProgress(downloadPercent);
//               if (downloadPercent >= 100) {
//                   break;
//                }
//            }
//        } catch (Exception e) {
//            return false;
//        }
//
//        return true;

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {


        // 在这里更新下载进度
        //progressDialog.setMessage("Downloaded " + values[0] + "%");


    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
       /* progressDialog.dismiss(); // 关闭进度对话框 // 在这里提示下载结果
        if (result) {
            Toast.makeText(context, "Download succeeded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, " Download failed",
                    Toast.LENGTH_SHORT).show();
        }*/
    }

//    启动这个任务
//    new DownloadTask().execute();
}
