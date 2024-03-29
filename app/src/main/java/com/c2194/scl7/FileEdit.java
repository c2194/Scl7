package com.c2194.scl7;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class FileEdit {


String fileName = "data.txt";
String filePath;



    public  FileEdit(String upThis){

        filePath = upThis;



        File file = new File(filePath+fileName);
        if(!file.exists()){

            String initstr= "|StarT|1|1|1|0|7|0|7|string1|string2|string3|b1.gif|b2.gif|b3.gif|EnD|";
            // a1.gif 12

// |StarT|[2]:呼吸屏幕开关 |[3] 是否显示提醒 |[4] 效果编号 |[5] SCID |5|6|7|string1|string2|string3|EnD|
           /// throw new RuntimeException("要读取的文件不存在");
           //文件不存在 创建文件设定初始值

            writeTxtToFile(initstr,filePath,fileName);

        }







    }


   public String Read(){

      File file = new File(filePath + fileName);

   return  getFileContent(file);
   }


    private String getFileContent(File file) {
        String content = "";
        if (!file.isDirectory()) {  //检查此路径名的文件是否是一个目录(文件夹)
            if (file.getName().endsWith("txt")) {//文件格式为""文件
                try {
                    InputStream instream = new FileInputStream(file);
                    if (instream != null) {
                        InputStreamReader inputreader
                                = new InputStreamReader(instream, "UTF-8");
                        BufferedReader buffreader = new BufferedReader(inputreader);
                        String line = "";
                        //分行读取
                        while ((line = buffreader.readLine()) != null) {
                            content += line + "\n";
                        }
                        instream.close();//关闭输入流
                    }
                } catch (java.io.FileNotFoundException e) {
                    Log.d("TestFile", "The File doesn't not exist.");
                } catch (IOException e) {
                    Log.d("TestFile", e.getMessage());
                }
            }
        }
        return content;
    }



public void Save( String[] stringArray){
        int strlong;
        strlong = stringArray.length;
        String wStr="";


        for(String inStr:stringArray){

            wStr=wStr+inStr+"|";

        }




        writeTxtToFile(wStr,filePath,fileName);


}


    private void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(0);
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }

//生成文件

    private File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

//生成文件夹

    private static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }







}
