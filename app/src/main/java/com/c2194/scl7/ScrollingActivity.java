package com.c2194.scl7;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


public class ScrollingActivity extends AppCompatActivity {




    Button bu2;
    Button bu3;
    Button bu4;

    Switch sw2;
    Switch sw3;
    Switch sw5;

    EditText edText;
    EditText edText2;

    String[] txtArray;
    FileEdit fe;
    mainLib mainlib;






    String reRSIDid;


    String reClockID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        */



        mainlib = new mainLib();
        String filepath ;
        filepath = this.getFilesDir().getPath();


        fe = new FileEdit(filepath);


        String text =fe.Read();


  //      Log.e("----", "---------" + text);

        txtArray = text.split("\\|");
//
//        Log.e("----", "Long" + txtArray.length);

//        Log.e("----", "1" + txtArray[1]);
//        Log.e("----", "all" + txtArray);




        //fe.Save(txtArray);


        bu2= (Button)findViewById(R.id.button);
        bu3= (Button)findViewById(R.id.button2);
        bu4= (Button)findViewById(R.id.button4);

        sw2 = (Switch)findViewById(R.id.switch2);
        sw3 = (Switch)findViewById(R.id.switch3);
        sw5 = (Switch)findViewById(R.id.switch5);
        edText = (EditText)findViewById(R.id.editText);
        edText2 =(EditText)findViewById(R.id.editText2);



        bu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtArray[4]="1";
                ShowInit();
            }
        });

        bu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtArray[4]="2";
                ShowInit();
            }
        });

        bu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtArray[4]="3";
                ShowInit();
            }
        });

        sw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sw2.isChecked()){
                    txtArray[2]="1";
                }else{
                    txtArray[2]="0";
                }
            }
        });


        sw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sw3.isChecked()){
                    txtArray[3]="1";
                }else{
                    txtArray[3]="0";
                }
            }
        });

        sw5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sw5.isChecked()){
                    txtArray[7]="1";
                }else{
                    txtArray[7]="0";
                }
            }
        });



        edText2.setText(txtArray[6]);



        edText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                txtArray[6] = edText2.getText().toString().trim();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });









        String m_szAndroidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);




        reRSIDid = mainlib.ReRSID(m_szAndroidID);


        reClockID = mainlib.ReCLOCKID(reRSIDid);








        //这是一个简易不可逆运值   将唯一Android id  get到服务端后,将换回本数字，在服务的存储本android id，用户删除软件后，可以再次获取本值。


        edText.setHint("RSID:"+reRSIDid);






   //     Log.e("-------------------------", " rsid" + reRSIDid);
  //      Log.e("----------------------------", "click id " +  reClockID);


  //      Toast.makeText(ScrollingActivity.this,""+reClockID,
   //             Toast.LENGTH_LONG).show();





















        Button onbutton = (Button)findViewById(R.id.button3);

        onbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idStr = edText.getText().toString();

                idStr = idStr.trim();



                if(idStr.equals(reClockID)){

                    txtArray[5] = idStr;










                    startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));



                    ComponentName adminReceiver = new ComponentName(ScrollingActivity.this, ScreenOffAdminReceiver.class);

                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,  adminReceiver);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"开启后就可以使用锁屏功能了...");//显示位置见图二

                    startActivityForResult(intent, 0);




                }else {


                    if(txtArray[5].equals(reClockID)){








                    }else {


                        txtArray[5] = "0";

                    }

                }


                fe.Save(txtArray);
                ShowInit();





                if(txtArray[5].equals(reClockID)) {
                    finish();
                }




            }
        });








        ShowInit();




    }











    protected void ShowInit(){


        if(txtArray[2].equals("0")){

            sw2.setChecked(false);
        }else{
            sw2.setChecked(true);
        }


        if(txtArray[3].equals("0")){

            sw3.setChecked(false);
        }else{
            sw3.setChecked(true);
        }


        if(txtArray[7].equals("0")){

            sw5.setChecked(false);
        }else{
            sw5.setChecked(true);
        }

        if(txtArray[4].equals("1")){

            bu2.setBackgroundColor(Color.rgb(00,85,77));
            bu3.setBackgroundColor(Color.parseColor("#E2E2E2"));
            bu4.setBackgroundColor(Color.parseColor("#E2E2E2"));

        }


        if(txtArray[4].equals("2")){

            bu3.setBackgroundColor(Color.rgb(00,85,77));
            bu2.setBackgroundColor(Color.parseColor("#E2E2E2"));
            bu4.setBackgroundColor(Color.parseColor("#E2E2E2"));

        }


        if(txtArray[4].equals("3")){

            bu4.setBackgroundColor(Color.rgb(00,85,77));
            bu2.setBackgroundColor(Color.parseColor("#E2E2E2"));
            bu3.setBackgroundColor(Color.parseColor("#E2E2E2"));

        }




        if(txtArray[5].equals("0")){
            edText.setText("");
        }else{


            // edText.setText(txtArray[5]);
            edText.setText("OK");
            edText.setEnabled(false);




        }



        edText2.setText(txtArray[6]);















    }






















}
