// Copyright 2016 Yahoo Inc.
// Licensed under the terms of the New-BSD license. Please see LICENSE file in the project root for terms.
package com.yahoo.test.apps;

public class App implements AppMBean {
    private final App1 app;

    public App(App1 app) {
        this.app = app;
    }

    @Override
    public int getValue() {
        return app.getValue();
    }

    @Override
    public boolean getStopped() {
        return app.getStopped();
    }

    @Override
    public void stop(boolean stop) {
        app.stop(stop);
    }
}
