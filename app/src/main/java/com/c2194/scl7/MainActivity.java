package com.c2194.scl7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    GifImageView gifImageView;
    GifDrawable gifFromAssets;

    TextView tview1;
    TextView tview2;
    ImageView iv2;
    ImageView iv3;
    ImageView iv4;
    private  static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String str = intent.getStringExtra("info");

            Log.e("----", " INFO " + str);


            if(str.equals("com.android.dialer")){
                Intent intent2 = new Intent(Intent.ACTION_CALL_BUTTON);
                startActivity(intent2);
                finish();

            }




            if(str.equals("com.tencent.mobileqq")){

                iv4.setAlpha(255);


            }



            if(str.equals("com.tencent.mm")){

                iv2.setAlpha(255);


            }
            if(str.equals("com.android.mms")){

                iv3.setAlpha(255);


            }



        }
    };






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//关键代码


        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



        setContentView(R.layout.activity_main);


        gifImageView = (GifImageView)findViewById(R.id.gifView);

        try {

            gifFromAssets = new GifDrawable(getAssets(),"a4.gif");

            gifImageView.setImageDrawable(gifFromAssets);

        } catch (IOException e) {
            e.printStackTrace();
        }




        IntentFilter iFilter2 = new IntentFilter(Intent.ACTION_SCREEN_ON);

        this.registerReceiver(broadcastReceiver2, iFilter2);



        //注册本地广播监听
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.c2194.scl7.b");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);









        tview1 = (TextView) findViewById(R.id.textView);
        tview2 = (TextView) findViewById(R.id.textView2);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Electrolize-Regular.ttf");
        tview1.setTypeface(typeface);

        setTime();


        iv2 = (ImageView)findViewById(R.id.imageView2);
        iv4 = (ImageView)findViewById(R.id.imageView4);
        iv3 = (ImageView)findViewById(R.id.imageView3);


        //  try {
        // GifDrawable gifFromAssets = new GifDrawable(getAssets(),"a4.gif" );
        //  gifImageView.setImageDrawable(gifFromAssets);



        //  } catch (IOException e) {
        //     e.printStackTrace();
        // }
        iv2.setAlpha(90);
        iv3.setAlpha(90);
        iv4.setAlpha(90);


        Log.e("----", "---------" + "00000000000");



       // startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);




    }




    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    public void setTime(){

        tview2.setText(DateString.StringData());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        Date date = new Date(System.currentTimeMillis());
        tview1.setText(simpleDateFormat.format(date));








    }


    private BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent arg1) {


           // edraw1.init();

            Log.e("----", "---------" + "ON");

            gifFromAssets.reset();




        }
    };




}
