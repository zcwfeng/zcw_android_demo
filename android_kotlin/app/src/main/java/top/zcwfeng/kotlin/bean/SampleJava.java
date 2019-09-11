package top.zcwfeng.kotlin.bean;

public class SampleJava {
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        int[] a= new int[100_000_000];
        for(int i = 0;i<100_000_000;i++){
            a[i] = i;
        }
        System.out.println(System.currentTimeMillis());

    }
}
