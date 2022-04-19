package cn.edu.hqu.cst.shiyan4;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Date;

public class MyServicec extends Service {

    private int count=0;
    MyBinder binder1 = new MyBinder();
    private boolean quit = false;

    @Override
    public void onCreate()
    {
        super.onCreate();

        new Thread(){
            public void run()
            {
                while(!quit)
                {
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e)
                    {

                    }
                    count++;
                }
            }
        }.start();
    }

    public class MyBinder extends Binder
    {

        MyServicec getService(){
            return MyServicec.this;
        }
        public int getCount()
        {
            return count;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder1;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        quit=true;
    }

    public void updateTime()
        {
            MainActivity.textview1.setText("Current Time : " + new Date());

        }
    }

