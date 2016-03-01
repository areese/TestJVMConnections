package com.yahoo.test.apps;

import java.io.PrintStream;

import com.yahoo.test.monitor.DumpMBeansRunnable;
import com.yahoo.test.monitor.MonitoringTools;

public class MbeanDumpTool {
    public static void main(String[] args) {
        PrintStream out = System.out;
        MonitoringTools mt = new MonitoringTools(out);
        DumpMBeansRunnable r = new DumpMBeansRunnable(out, (null == args[0]) ? -1 : Integer.valueOf(args[0]));
        mt.foreachVM(r);
    }
}
