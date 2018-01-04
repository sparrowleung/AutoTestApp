package com.example.a37289.autotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRun = (Button) findViewById(R.id.main_run);
        mRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runMyUiautomator(v);
            }
        });

    }

    public void runMyUiautomator(View v){
        new UiautomatorThread().start();
    }

    /**
     * 运行uiautomator是个费时的操作，不应该放在主线程，因此另起一个线程运行
     */
    class UiautomatorThread extends Thread {
        @Override
        public void run() {
            super.run();
            String command=generateCommand("com.example.mytest", "ExampleInstrumentedTest", "useAppContext");
            CMDUtils.CMD_Result rs= CMDUtils.runCMD(command,true,true);
            Log.e("test1", "run: " + rs.error + "-------" + rs.success);
        }

        /**
         * 生成命令
         * @param pkgName 包名
         * @param clsName 类名
         * @param mtdName 方法名
         * @return
         */
        public  String generateCommand(String pkgName, String clsName, String mtdName) {
            String command = "am instrument  --user 0 -w -r   -e debug false -e class "
                    + pkgName + "." + clsName + "#" + mtdName + " "
                    + pkgName + ".test/android.support.test.runner.AndroidJUnitRunner";
            Log.e("test1: ", command);
            return command;
        }
    }
}
