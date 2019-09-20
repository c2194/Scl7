package com.c2194.scl7;


import android.util.Log;

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

            Log.e("----", "-----issadd = issadd+iasob;  rsid  ----" + reissint );
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



}
