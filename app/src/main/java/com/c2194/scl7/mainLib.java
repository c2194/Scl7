package com.c2194.scl7;


import android.util.Log;

import java.util.ArrayList;

public class mainLib {





    public String ReRSID(String m_szAndroidID){



        // String m_szAndroidID = Secure.getString(getContentResolver(), Secure.ANDROID_ID);

        //String aa="8d2aec7ce7e2b328";

        byte ss[] = m_szAndroidID.getBytes();

        int issadd=0;
        int iasien=0;
        int iasob=0;
        for (byte iss:ss){

            iasien++;

            iasob= (int)(iss*1000)/3;

            issadd = issadd+iasob;



        }


        int ggn1 = issadd *10 /3;

        String reissStr = Integer.toString(ggn1);


        return  reissStr;




    }


    public String ReCLOCKID(String m_szAndroidID){




       // String m_szAndroidID = Secure.getString(getContentResolver(), Secure.ANDROID_ID);

        //String aa="8d2aec7ce7e2b328";

        byte ss[] = m_szAndroidID.getBytes();

        int issadd=0;
        int iasien=0;
        int iasob=0;
        for (byte iss:ss){

            iasien++;

            iasob= (int)(iss*1000)/3;
        //    Log.e("----", "-----issadd = issadd+iasob; ----" + iasob );
            issadd = issadd+iasob;



        }



        //Log.e("----", "-----issadd = issadd+iasob;  rsid  ----" + issadd );


        int ggn1 = issadd *10 /3;

        String reissStr = Integer.toString(ggn1);

        //String reissStr ="233458";
        byte ress[] = reissStr.getBytes();

        int ressintarr[]={9,5,2,7};

        int ress_g1=0,ress_g2=0,ress_g3=0;

        for(byte reiss:ress ){

            int reissint = (int)reiss;

       //     Log.e("----", "-----issadd = issadd+iasob;  rsid  ----" + reissint );
            ress_g1 =0;
            for(int i=0;i<reissint;i++){

                ressintarr[ress_g1]++;

                if(ressintarr[ress_g1]>9) ressintarr[ress_g1]=0;


                ress_g1++;
                if (ress_g1 ==4){
                    ress_g1=0;
                }

            }



            if(ress_g1 == 0){


                ressintarr[0] = rechint( ressintarr[0],2,0);
                ressintarr[1] = rechint( ressintarr[1],3,1);
                ressintarr[2] = rechint( ressintarr[2],4,1);
                ressintarr[3] = rechint( ressintarr[3],5,1);


            }


            if(ress_g1 == 1){


                ressintarr[0] = rechint( ressintarr[0],1,0);
                ressintarr[1] = rechint( ressintarr[1],2,0);
                ressintarr[2] = rechint( ressintarr[2],3,1);
                ressintarr[3] = rechint( ressintarr[3],4,1);


            }


            if(ress_g1 == 2){


                ressintarr[0] = rechint( ressintarr[0],1,1);
                ressintarr[1] = rechint( ressintarr[1],2,1);
                ressintarr[2] = rechint( ressintarr[2],3,0);
                ressintarr[3] = rechint( ressintarr[3],4,0);


            }

            if(ress_g1 == 3){


                ressintarr[0] = rechint( ressintarr[0],4,1);
                ressintarr[1] = rechint( ressintarr[1],3,0);
                ressintarr[2] = rechint( ressintarr[2],2,0);
                ressintarr[3] = rechint( ressintarr[3],1,1);


            }








        }





        return ""+ressintarr[0]+ ressintarr[1]+ ressintarr[2]+ ressintarr[3];








    }


    protected int rechint(int intin,int intinlong,int cz){



        for (int i=0;i<intinlong;i++){


            if(cz==1){
                intin++;
                if(intin>9) intin = 0;
            }else{
                intin--;
                if(intin<0) intin = 9;
            }

        }



        return intin;

    }





    public  String EnCode(String str ,String codeStr){

        char[] strCharArr,codeCharArr;

        strCharArr = str.toCharArray();
        int strLong = str.length();
        codeCharArr = codeStr.toCharArray();




        ArrayList List = new ArrayList();

        int ran2 = (int) (Math.random()*(999-1)+1);
        AddArrNumChar(ran2,List);
        ran2 = (int) (Math.random()*(999-1)+1);
        AddArrNumChar(ran2,List);

        AddArrNumChar(999,List);  //和值

        int enStart = (int) (Math.random()*(195-0)+0);
        AddArrNumChar(enStart,List);  // 编码起点


        int yz = strLong+codeCharArr[enStart];

        AddArrNumChar(yz,List);

        int dwei=0;

        for (int i=0;i<strLong;i++){

            if(enStart+i > codeCharArr.length - 1){

                dwei = enStart +i -codeCharArr.length;

            }else{

                dwei= enStart+i;

            }


            int tasc = strCharArr[i] + codeCharArr[dwei] + 169;

            AddArrNumChar(tasc,List);


        }



        ran2 = (int) (Math.random()*(999-1)+1);
        AddArrNumChar(ran2,List);
        ran2 = (int) (Math.random()*(999-1)+1);
        AddArrNumChar(ran2,List);


        String re = "";
        for(Object data:List){
            String value= data.toString();
            re=re+value;
        
        }

        return re;
    }

    protected  void  AddArrNumChar(int nu,ArrayList List ){

        String str = Integer.toString(nu);
        char[] StrArr = str.toCharArray();

        if(StrArr.length==1){

            List.add("0");
            List.add("0");
            List.add(StrArr[0]);

        }

        if(StrArr.length==2){

            List.add("0");
            List.add(StrArr[0]);
            List.add(StrArr[1]);

        }
        if(StrArr.length==3){


            List.add(StrArr[0]);
            List.add(StrArr[1]);
            List.add(StrArr[2]);

        }

    }




    public  String UnCode(String str ,String codeStr){


        char[] strCharArr,codeCharArr;



        strCharArr = str.toCharArray();
        int strLong = str.length();
        codeCharArr = codeStr.toCharArray();
        int enCodeLen = codeCharArr.length;


        //$mask = intval($stArr[2]); //效验值

        //$enpo = intval($stArr[3]); //编码器字典起始位置

        //$ustrLen = intval($stArr[4])-ord($enarrsp[$enpo]); // 字符串长度


        int mask = Reint(strCharArr,3*2);
        int enpo = Reint(strCharArr,3*3);
        int ustrLen = Reint(strCharArr,3*4)-codeCharArr[enpo];

        String returnStr = "";
        int thPo=0;
        int tpa =0;
        int ten =0;

        char adChar=0;

        for(int i=0;i<ustrLen;i++){
            if(i+enpo > enCodeLen-1){
                thPo = i+enpo-enCodeLen;

            }else{
                thPo = i+enpo;

            }

            tpa=Reint(strCharArr,3*(5+i));

            ten = codeCharArr[thPo];

            adChar = (char) ((char)tpa-ten-169);


            returnStr = returnStr + adChar;

            





        }

   /*
        $returnStr="";


        for($i=0;$i<$ustrLen;$i++){


            if($i+$enpo>$enCodeLen-1){
                $thPo= $i+$enpo-$enCodeLen;
            }else{
                $thPo = $i+$enpo;
            }

            $tpa = intval($stArr[5+$i]);


            $ten = ord($enarrsp[$thPo]);
            //     echo "- tpa $tpa  ten $ten  thPo $thPo -  ";
            $returnStr = $returnStr . chr($tpa - $ten -169);




        }


*/








        return returnStr;
    }

    protected int Reint(char[] strCharArr,int po){
        char[] charr= new char[3];

        charr[0]=strCharArr[po];
        charr[1]=strCharArr[po+1];
        charr[2]=strCharArr[po+2];

        String tstr = String.copyValueOf(charr);

        return Integer.parseInt(tstr);
    }




}
