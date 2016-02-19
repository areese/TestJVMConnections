// Copyright 2016 Yahoo Inc.
// Licensed under the terms of the New-BSD license. Please see LICENSE file in the project root for terms.
package com.yahoo.test.apps;


public interface AppMBean {

    public int getValue();

    public boolean getStopped();

    public void stop(boolean stop);
}
