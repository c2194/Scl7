package com.c2194.scl7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import android.content.Context;
import android.os.PowerManager;
import android.os.SystemClock;
import android.widget.Toast;


import static android.os.PowerManager.*;

public class MainActivity extends AppCompatActivity implements android.view.GestureDetector.OnGestureListener {
    GifImageView gifImageView;
    GifDrawable gifFromAssets;

    TextView tview1;
    TextView tview2;
    ImageView iv2;
    ImageView iv3;
    ImageView iv4;

    Thread mThread;

    private int lastX;
    private int lastY;
    private VelocityTracker velocityTracker;



    GestureDetector detector;


    public static final  String TAG="MainActivity";

    private  static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;

    int clsec=10;


    int fingerCode = 1;

    int face=0;





    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String str = intent.getStringExtra("info");

       //     Log.e("----", " INFO " + str);


            boolean ScrrenOn = false;


            if(str.equals("com.android.dialer")){
                Intent intent2 = new Intent(Intent.ACTION_CALL_BUTTON);
                startActivity(intent2);
                finish();

            }




            if(str.equals("com.tencent.mobileqq")){

                iv4.setAlpha(255);
                ScrrenOn = true;


            }



            if(str.equals("com.tencent.mm")){

                iv2.setAlpha(255);

                ScrrenOn =true;


            }
            if(str.equals("com.android.mms")){

                iv3.setAlpha(255);

                ScrrenOn = true;


            }


            if(ScrrenOn){

                turnOnScreen();
            }



        }
    };



    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;
    DevicePolicyManager policyManager;
    ComponentName adminReceiver;


    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fingerCode=3;
        this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//关键代码



        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        adminReceiver = new ComponentName(MainActivity.this, ScreenOffAdminReceiver.class);

        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        policyManager = (DevicePolicyManager) MainActivity.this.getSystemService(Context.DEVICE_POLICY_SERVICE);



        setContentView(R.layout.activity_main);



        if(velocityTracker == null) {//velocityTracker对象为空 获取velocityTracker对象
            velocityTracker = VelocityTracker.obtain();
        }else {//velocityTracker对象不为空 将velocityTracker对象重置为初始状态
            velocityTracker.clear();
        }


        gifImageView = (GifImageView)findViewById(R.id.gifView);
        String showMess = "";
        try {

            Bundle bundle = this.getIntent().getExtras();
            //接收name值
            String typ = bundle.getString("type");

            String asname="a1.gif";
            if(typ.equals("1")) asname="a1.gif";
            if(typ.equals("2")) asname="a2.gif";
            if(typ.equals("3")) asname="a3.gif";


     //       Log.e("-----进到oncreate--------", "---------" );

            gifFromAssets = new GifDrawable(getAssets(),asname);
            gifFromAssets.stop();




            gifImageView.setImageDrawable(gifFromAssets);


            showMess = bundle.getString("mess");
            clsec = Integer.parseInt(bundle.getString("sec"));
            face = Integer.parseInt(bundle.getString("face"));






        } catch (IOException e) {
            e.printStackTrace();
        }






        //
        //  启动时钟线程 每秒一次事件
        //
        mThread = new TimThread(1000);
        mThread.start();




        //手势检测
        detector = new GestureDetector(this, this);



        //////////////////////////////
        ///
        //////////////////////////////




        boolean admin = policyManager.isAdminActive(adminReceiver);
        if (admin) {
            policyManager.lockNow();
            // handler.sendEmptyMessageDelayed(1,3000);
        } else {
            Toast.makeText(MainActivity.this,"没有设备管理权限",
                    Toast.LENGTH_LONG).show();



            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,  adminReceiver);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"开启后就可以使用锁屏功能了...");//显示位置见图二

            startActivityForResult(intent, 0);


        }







        ///////////////////////////////////
        ///
        //////////////////////////////////




        IntentFilter iFilter2 = new IntentFilter(Intent.ACTION_SCREEN_ON);
        IntentFilter iFilter3 = new IntentFilter(Intent.ACTION_USER_PRESENT);





        unReg();
        this.registerReceiver(broadcastReceiver2, iFilter2);
        this.registerReceiver(broadcastReceiver3, iFilter3);



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


        if (showMess.equals("1")){
            iv2.setVisibility(View.VISIBLE);
            iv3.setVisibility(View.VISIBLE);
            iv4.setVisibility(View.VISIBLE);
        }else{
            iv2.setVisibility(View.GONE);
            iv3.setVisibility(View.GONE);
            iv4.setVisibility(View.GONE);

        }


        //  try {
        // GifDrawable gifFromAssets = new GifDrawable(getAssets(),"a4.gif" );
        //  gifImageView.setImageDrawable(gifFromAssets);



        //  } catch (IOException e) {
        //     e.printStackTrace();
        // }
        iv2.setAlpha(90);
        iv3.setAlpha(90);
        iv4.setAlpha(90);


   //     Log.e("----", "---------" + "00000000000");



       // startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);




////////////////////////////
        //  onTonchg
        /////////////////////////

/*
        gifImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {




                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                   // xDown = motionEvent.getX();
                   // yDown = motionEvent.getY();
                    Log.v("OnTouchListener", "Down"+"--X-" + motionEvent.getX() + "----Y--" + motionEvent.getY());

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {// 松开处理
                    //获取松开时的x坐标
                    Log.v("OnTouchListener", "Up"+"--X-" + motionEvent.getX() + "----Y--" + motionEvent.getY());

                    //   if (isLongClickModule) {
                     //   isLongClickModule = false;
                   //     isLongClicking = false;
                  //  }

                }







                Log.e("----", "--X-" + motionEvent.getX() + "----Y--" + motionEvent.getY());
                return false;



            }
        });


*/


    }

protected  void unReg(){



    try {
        unregisterReceiver(broadcastReceiver2);
        unregisterReceiver(broadcastReceiver3);
    } catch (IllegalArgumentException e) {
        if (e.getMessage().contains("Receiver not registered")) {
            // Ignore this exception. This is exactly what is desired
        } else {
            // unexpected, re-throw
            throw e;
        }
    }




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

      //      Log.e("----", "---------" + "ON");

            scrSec=0;
            scrSecClock=1;





            setTime();

             gifFromAssets.reset();
             secAdd =0;










        }
    };


    private BroadcastReceiver broadcastReceiver3 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent arg1) {


            // edraw1.init();




               if(face>0) {
     //              Log.e("----", "------关闭 1 ---" + "1");

                   scrSec=0;
                   scrSecClock=0;

                   secAdd = 100;
                   finish();

               }








        }
    };















    int secAdd=0;
    boolean screenOnOff = true;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:



                    if(scrSecClock ==1 ){
                        if(scrSec == 1000) scrSec=1000;
                        scrSec++;
                    }





                    secAdd++;

                    if(secAdd>10000){
                        secAdd =0;
                    }






                    if(secAdd == clsec  ) {
                        secAdd++;

                        //  wakeLock.acquire();
      //                  Log.e("----", "---------" + "SCREEN OFF");
                        gifFromAssets.reset();
                        gifFromAssets.stop();

                        boolean admin = policyManager.isAdminActive(adminReceiver);
                        if (admin) {
                            if(isTopActivity()) {

                                policyManager.lockNow();






                            }
                            // handler.sendEmptyMessageDelayed(1,3000);


                        } else {
                            Toast.makeText(MainActivity.this, "没有设备管理权限",
                                    Toast.LENGTH_LONG).show();
                        }

                    }






                    break;
            }

        }
    };

int scrSec =0;
int scrSecClock=0;






    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        return detector.onTouchEvent(me);
    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        float minMove = 420; // 最小滑动距离
        float minVelocity = 0; // 最小滑动速度
        float beginX = e1.getX();
        float endX = e2.getX();
        float beginY = e1.getY();
        float endY = e2.getY();

        if (beginX - endX > minMove && Math.abs(velocityX) > minVelocity) { // 左滑
            finish();
            //Toast.makeText(this, velocityX + "左滑", Toast.LENGTH_SHORT).show();
        } else if (endX - beginX > minMove && Math.abs(velocityX) > minVelocity) { // 右滑
            finish();
            // Toast.makeText(this, velocityX + "右滑", Toast.LENGTH_SHORT).show();
        } else if (beginY - endY > minMove && Math.abs(velocityY) > minVelocity) { // 上滑
            finish();
            //Toast.makeText(this, velocityX + "上滑", Toast.LENGTH_SHORT).show();
        } else if (endY - beginY > minMove && Math.abs(velocityY) > minVelocity) { // 下滑
            finish();
            //Toast.makeText(this, velocityX + "下滑", Toast.LENGTH_SHORT).show();
        }



        return false;
    }


    public class TimThread extends Thread {

        int msec=0;

        public  TimThread(int sec){
            msec = sec;
        }


        @Override
        public void run() {
            while (true) {

                try {
                    Thread.sleep(msec);//每隔1s执行一次
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }






    }











    /**
     * 检测用户是否开启了超级管理员
     */
    private void isOpen() {
        if(policyManager.isAdminActive(adminReceiver)){//判断超级管理员是否激活

            Toast.makeText(MainActivity.this,"设备已被激活",
                    Toast.LENGTH_LONG).show();

        }else{

            Toast.makeText(MainActivity.this,"设备没有被激活",
                    Toast.LENGTH_LONG).show();

        }
    }



    @SuppressLint("InvalidWakeLockTag")
    public void turnOnScreen() {
        // turn on screen
        Log.v("ProximityActivity", "ON!");
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag");
        mWakeLock.acquire();
        mWakeLock.release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isOpen();
    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }

//        unregisterReceiver(this.broadcastReceiver);
        unregisterReceiver(this.broadcastReceiver2);
        unregisterReceiver(this.broadcastReceiver3);

    }




    private boolean isTopActivity()
    {
        boolean isTop = false;
        ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
      //  DebugLog.d(TAG, "isTopActivity = " + cn.getClassName());
        if (cn.getClassName().contains(TAG))
        {
            isTop = true;
        }
       // DebugLog.d(TAG, "isTop = " + isTop);
        return isTop;
    }







}
