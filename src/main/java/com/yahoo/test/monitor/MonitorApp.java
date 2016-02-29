// Copyright 2016 Yahoo Inc.
// Licensed under the terms of the New-BSD license. Please see LICENSE file in the project root for terms.
package com.yahoo.test.monitor;

import java.io.PrintStream;


public class MonitorApp {

    public static void main(String[] args) {
        PrintStream out = System.out;
        MonitoringTools mt = new MonitoringTools(out);
        ShowDataRunnable sdr = new ShowDataRunnable(out);
        mt.foreachVM(sdr);
    }

}
