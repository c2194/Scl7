package com.c2194.scl7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NLService extends NotificationListenerService {



    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent arg1) {


            Log.e("-----能够跳到锁屏界面--------", "---------" + arg1);


            ////
            //
            /////////


            mainLib mainlib = new mainLib();
            String filepath ;
            filepath = getFilesDir().getPath();


            FileEdit fe = new FileEdit(filepath);


            String text =fe.Read();


            Log.e("----", "---------" + text);

            String[] txtArray = text.split("\\|");




            //获取reclockID
            //int fxnum= Integer.parseInt(txtArray[5]);




            String m_szAndroidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);




            //reRSIDid = mainlib.ReRSID(m_szAndroidID);


            String reClockID = mainlib.ReCLOCKID(m_szAndroidID);














            ////////////
            //
            //////////////////

            if(reClockID.equals(txtArray[5])) {


                Intent intent = new Intent(context, MainActivity.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle bundle=new Bundle();
                //传递name参数为tinyphp
                bundle.putString("type", txtArray[4]);
                intent.putExtras(bundle);



                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);

            }
        }
    };


    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
//        super.onNotificationPosted(sbn);
        //这里只是获取了包名和通知提示信息，其他数据可根据需求取，注意空指针就行
        String pkg = sbn.getPackageName();
        CharSequence tickerText = sbn.getNotification().tickerText;
        Log.e("-------------------------", " ------\npkg:"+pkg+"\ntickerText:"+TextUtils.isEmpty(tickerText+"-----"+tickerText));

       //if(pkg.equals())

        sendBroadcast(pkg);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
//        super.onNotificationRemoved(sbn);
        String pkg = sbn.getPackageName();
        CharSequence tickerText = sbn.getNotification().tickerText;
      //  sendBroadcast(String.format("移除通知\npkg:%s\ntickerText:%s", pkg, TextUtils.isEmpty(tickerText) ? "null" : tickerText));
    }

    private void sendBroadcast(String msg) {
        Intent intent = new Intent("com.c2194.scl7.b");
        intent.putExtra("info", msg);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }




    @Override
    public void onCreate() {
        super.onCreate();
        //屏蔽掉系统的锁屏


        IntentFilter iFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);

        this.registerReceiver(broadcastReceiver, iFilter);

        Log.e("-------------------------", " -------3333333333333333333333333333333333---");



    }





}
