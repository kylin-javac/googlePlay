package ligang.huse.cn.googleplay.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 创建时间 javac on 2016/5/1.
 * <p/>
 * 文  件 googlePlay
 * <p/>
 * 描  述 线程管理器
 */
public class ThreadManager {
    /**
     * 单例模式，确保每次只产生一个线程
     */
    private static ThreadPool threadPool = null;

    public static ThreadPool getInstance() {
        if (threadPool == null) {
            synchronized (ThreadManager.class) {
                if (threadPool == null) {
                    threadPool = new ThreadPool(10, 10, 1L);
                }
            }
        }
        return threadPool;

    }


    public static class ThreadPool {//线程池
        private int corePoolSize;// 核心线程数
        private int maximumPoolSize;// 最大线程数
        private long keepAliveTime;// 休息时间
        private ThreadPoolExecutor mExecutor;


        public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;

        }


        public void execute(Runnable r) {
            mExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                    TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy());
            // 参1:核心线程数;
            // 参2:最大线程数;
            // 参3:线程休眠时间;
            // 参4:时间单位;
            // 参5:线程队列;
            // 参6:生产线程的工厂;
            // 参7:线程异常处理策略

            // 线程池执行一个Runnable对象, 具体运行时机线程池说了算
            mExecutor.execute(r);

        }
        //取消任务
        public void cancel(Runnable r){
            if(mExecutor!=null){
                mExecutor.getQueue().remove(r);
            }
        }

    }






}
