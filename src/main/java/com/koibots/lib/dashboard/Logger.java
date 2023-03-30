package com.koibots.lib.dashboard;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.hal.ThreadsJNI;

public class Logger implements AutoCloseable, Runnable{
    List<Map<String, Supplier<Object>>> logValues;
    private static OutputStream currentStream = OutputStream.kConsole;
    private Runnable logFunction;

    public static enum OutputStream {
        kUsb,
        kConsole,
        kShuffleboard,
        kDashboard
    }

    public static void initLogger() {}

    public static void setOutputStream(OutputStream newStream) {
        currentStream = newStream;
    }

    public static OutputStream getCurrentStream() {
        return currentStream;
    }

    @Override
    public void run() {
        logFunction.run();
    }

    private static void usbLogger() {

    }

    private static void consoleLogger() {

    }

    private static void dahsboardLogger() {

    }

    @Override
    public void close() throws Exception {
        
    }
}
