package com.example.tuanq;

import javafx.fxml.FXML;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThread {
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static ExecutorService getExecutorService() {
        return executorService;
    }
}
