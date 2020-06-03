package top.zcwfeng.customui.asmtest;

class InjectTest {


    @ASMTest
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Thread.sleep(3000);
        long endTime = System.currentTimeMillis();
        System.out.println("execute time=" + (endTime - startTime));
    }

    void method() {

    }
}
