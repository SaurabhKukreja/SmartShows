package com.kukroid.smartshows;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    public Executor getMainThreadIO() {
        return mainThreadIO;
    }

    private Executor diskIO;
    private Executor networkIO;
    private Executor mainThreadIO;
    private static AppExecutors executors;

    AppExecutors(Executor diskIO, Executor networkIO, Executor mainThreadIO) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThreadIO = mainThreadIO;
    }


    public static AppExecutors getExecutors(){
        if(executors == null){
            synchronized (new Object()){
                executors = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return executors;
    }


    private static class MainThreadExecutor implements Executor{

        private Handler handler =  new Handler(Looper.myLooper());
        @Override
        public void execute(@NonNull Runnable runnable) {
            handler.post(runnable);
        }
    }
}
