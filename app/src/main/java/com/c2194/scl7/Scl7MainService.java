package com.c2194.scl7;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;



    public class Scl7MainService extends NotificationListenerService {



        @Override
        public void onNotificationPosted(StatusBarNotification sbn) {
//        super.onNotificationPosted(sbn);
            //这里只是获取了包名和通知提示信息，其他数据可根据需求取，注意空指针就行
            String pkg = sbn.getPackageName();
            CharSequence tickerText = sbn.getNotification().tickerText;
            Log.e("-------------------------", " ------\npkg:"+pkg+"\ntickerText:"+TextUtils.isEmpty(tickerText+"-----"+tickerText));
            sendBroadcast(String.format("显示通知\npkg:%s\ntickerText:%s", pkg, TextUtils.isEmpty(tickerText) ? "null" : tickerText));

            // 服务过程都写到这里，




        }

        @Override
        public void onNotificationRemoved(StatusBarNotification sbn) {
//        super.onNotificationRemoved(sbn);
            String pkg = sbn.getPackageName();
            CharSequence tickerText = sbn.getNotification().tickerText;
            sendBroadcast(String.format("移除通知\npkg:%s\ntickerText:%s", pkg, TextUtils.isEmpty(tickerText) ? "null" : tickerText));
        }

        private void sendBroadcast(String msg) {
            Intent intent = new Intent(getPackageName());
            intent.putExtra("text", msg);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }




        @Override
        public void onCreate() {
            super.onCreate();
            //屏蔽掉系统的锁屏

            Log.e("-------------------------", " -------3333333333333333333333333333333333---");



        }





    }
