package com.enjoy.asminject;


public class InjectTest {

    @ASMTest
    public static void main(String[] args) throws InterruptedException {
        long l = System.currentTimeMillis();

        //do something
        Thread.sleep(1_000);

        long e = System.currentTimeMillis();
        System.out.println("execute:"+(e-l)+" ms.");
    }

}
