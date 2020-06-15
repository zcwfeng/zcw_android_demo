package top.zcwfeng.customui.http;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 整体的运作
 */
public class ThreadManager {
    //    单例

    public static ThreadManager getInstance(){
        return SingletonHolder.threadManager;
    }
    private static class SingletonHolder{
        private static final ThreadManager threadManager = new ThreadManager();
    }


//    private enum EnumHolder{
//        INSTANCE;
//
//        private ThreadManager instance = null;
//        private EnumHolder(){
//            instance = new ThreadManager());
//        }
//
//        private ThreadManager getInstance(){
//            return instance;
//        }
//    }
//
//    public static ThreadManager getInstance(){
//        return EnumHolder.INSTANCE.getInstance();
//    }


//    private static ThreadManager threadManager;
//    public static ThreadManager getInstance(){
//        synchronized (ThreadManager.class) {
//            if(threadManager == null) {
//                return new ThreadManager();
//            }
//        }
//        return threadManager;
//    }


//    private static volatile ThreadManager threadManager;
//    public static ThreadManager getInstance() {
//        if(threadManager == null) {
//            synchronized (ThreadManager.class){
//                if(threadManager == null) {
//                    threadManager = new ThreadManager();
//                }
//            }
//        }
//        return threadManager;
//    }

    // 定义一个请求队列
    // TODO: 2020/6/5 阻塞队列
    private LinkedBlockingDeque<Runnable> mQueue = new LinkedBlockingDeque<>();

    public void addTask(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        System.out.println("阻塞队列");
        mQueue.add(runnable);

    }

    // 定义线程池 看原理
    // TODO: 2020/6/5 线程池处理中心
    private ThreadPoolExecutor threadPoolExecutor;

    private ThreadManager() {

        threadPoolExecutor = new ThreadPoolExecutor(3,
                10, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4) {

                }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                addTask(r);
            }
        });

        threadPoolExecutor.execute(mRunnable);

    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Runnable task = mQueue.take();
                    threadPoolExecutor.execute(task);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


}
