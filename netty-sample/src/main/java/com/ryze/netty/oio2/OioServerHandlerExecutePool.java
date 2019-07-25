package com.ryze.netty.oio2;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * Created by xueLai on 2019/7/25.
 */
public class OioServerHandlerExecutePool {

    private ExecutorService executorService;
    private static final int DEFAULT_COREPOOLSIZE;

    static {
        DEFAULT_COREPOOLSIZE = Runtime.getRuntime().availableProcessors() * 2;
    }

    public OioServerHandlerExecutePool(int maxPoolSize, int queueSize) {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build();
        this.executorService = new ThreadPoolExecutor(DEFAULT_COREPOOLSIZE, maxPoolSize,
                120L,TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize),factory,new ThreadPoolExecutor.AbortPolicy());
    }

    public void execute(Runnable runnable){
        executorService.execute(runnable);
    }
}
