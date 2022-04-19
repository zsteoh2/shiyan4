package cn.edu.hqu.cst.shiyan4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static TextView textview1;
    static TextView textview2;
    static TextView textview3;

    boolean bound;
    MyServicec service1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnUnbind = (Button) findViewById(R.id.btnUnbind);
        Button btnBind = (Button) findViewById(R.id.btnBind);
        Button btnShowTime = (Button) findViewById(R.id.btnShowTime);
        Button btnShowStatus = (Button)findViewById(R.id.btnShowStatus);
        textview1 = findViewById(R.id.tvServiceInfo);
        textview2 = findViewById(R.id.textView2);
        textview3 = findViewById(R.id.textView3);


        textview1.setText("Current Time : " + new Date());

        btnUnbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bound)
                {
                    unbindService(connection);
                    bound=false;
                    textview2.setText("Service not bound");


                }

            }
        });

        btnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MyServicec.class);
                bindService(intent, connection ,BIND_AUTO_CREATE);
            }
        });

        btnShowTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bound){service1.updateTime();}

            }
        });

        btnShowStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bound)
                    Toast.makeText(MainActivity.this,"Service, Count = " + new MyServicec().binder1.getCount(),Toast.LENGTH_SHORT).show();
            }
        });


    }
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyServicec.MyBinder binder = (MyServicec.MyBinder) iBinder;
            service1 = binder.getService();
            bound = true;
            textview2.setText("Service bound");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bound = false;
            textview2.setText("Service not bound") ;
        }
    };
}