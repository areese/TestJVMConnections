// Copyright 2016 Yahoo Inc.
// Licensed under the terms of the New-BSD license. Please see LICENSE file in the project root for terms.
package com.yahoo.test.monitor;


public class MonitorApp {

    public static void main(String[] args) {
        MonitoringTools mt = new MonitoringTools(System.out);
        mt.showData();
    }

}
