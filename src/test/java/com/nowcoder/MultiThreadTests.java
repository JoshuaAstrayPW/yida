package com.nowcoder;

/**
 * Created by fangxu on 2017/2/12.
 */
class MyThread extends Thread{
    private int tid;

    public MyThread(int tid){
        this.tid = tid;
    }
    @Override
    public void run(){
        try{
            for(int i = 0; i < 10; ++i){
                Thread.sleep(1000);
                System.out.print(String.format("%d:%d \n",tid,i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
public class MultiThreadTests {


    public static void testThread() {
        for (int i = 0; i < 10; ++i) {
            final  int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        for(int j = 0; j < 10; ++j){
                            Thread.sleep(1000);
                            System.out.print(String.format("T2 %d: %d \n",finalI,j));
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            } ).start();
        }
    }
    private static  Object obj = new Object();

 public static void testSynchronized1(){
     synchronized (obj){
         try{
             for(int j = 0; j < 10; ++j){
                 Thread.sleep(1000);
                 System.out.print(String.format("T3 %d \n",j));
             }
         }catch(Exception e){
             e.printStackTrace();
         }
     }
 }
    public static void testSynchronized2(){
        synchronized (obj){
            try{
                for(int j = 0; j < 10; ++j){
                    Thread.sleep(1000);
                    System.out.print(String.format("T4 %d \n",j));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void testSynchronized(){
        for(int i = 0; i < 10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    testSynchronized1();
                    testSynchronized2();
                }
            }).start();
        }
    }
    public  static void main(String[] argv){
        //testThread();
    testSynchronized();
    }
}
