package com.golftec.video.production;


/**
 * Created by ThuPT on 12/28/2015.
 */
public class Test {

    public static void main(String args[]) throws InterruptedException {
        Test test = new Test();
        test.mainMethod();
    }

    public Integer longRunningMethod(String param1, Object parm2) throws InterruptedException {
        System.out.println("Long running method");
        Thread.sleep(5000);
        //long process
        return 1;
    }

    public void otherMethod() {
        System.out.println("Other Method");
    }


    public void mainMethod() throws InterruptedException {
        System.out.println("Main Method start");
        asyncServiceMethod("testing", new Object());
        otherMethod();
    }

    public void asyncServiceMethod(final String parm1, final Object obj) {
        Runnable task = new Runnable() {

            @Override
            public void run() {
                try {
                    longRunningMethod(parm1, obj);
                } catch (Exception ex) {
                    //handle error which cannot be thrown back
                }
            }
        };
        new Thread(task, "ServiceThread").start();
    }

}

