package com.example.srcn4.linesendingtest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // LINEのアプリID
    private static final String LINE_APP_ID = "jp.naver.line.android";
    // LINEで送る用の改行コード
    private static final String NEW_LINE_FOR_LINE = "%0D%0A";
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 入力されたテキストボックスを取得
        editText = (EditText) findViewById(R.id.edit_text1);
    }

    public void sendLineMsg(View v) {

        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(LINE_APP_ID, PackageManager.GET_META_DATA);
            //インストールされてたら、LINEへ
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("line://msg/text/" + "中間地点は…" + NEW_LINE_FOR_LINE
                    + editText.getText() + NEW_LINE_FOR_LINE
                    + "だよ！" + NEW_LINE_FOR_LINE
                    + "from 中間地点アプリ"
            ));
            startActivity(intent);
        } catch(PackageManager.NameNotFoundException e) {
            //インストールされてなかったら、インストールを要求する
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("LINEが見つかりません。")
                    .setMessage("LINEをインストールしてやり直して下さい。")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 特に何もしない
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }
}
