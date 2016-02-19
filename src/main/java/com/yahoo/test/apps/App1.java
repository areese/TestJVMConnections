// Copyright 2016 Yahoo Inc.
// Licensed under the terms of the New-BSD license. Please see LICENSE file in the project root for terms.
package com.yahoo.test.apps;

import java.lang.management.ManagementFactory;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

public class App1 implements Runnable, AppMBean {
    private volatile boolean stop = false;
    private volatile int value = 0;

    public static void main(String[] args) throws Exception {
        App1 app = new App1();
        app.register();
        app.run();
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                value++;
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public boolean getStopped() {
        return stop;
    }

    @Override
    public void stop(boolean stop) {
        this.stop = stop;
    }

    public void register() throws MalformedObjectNameException, InstanceAlreadyExistsException,
                    MBeanRegistrationException, NotCompliantMBeanException {
        String objectName = "com.yahoo.test.apps.App1:type=App";
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName mbeanName = new ObjectName(objectName);

        App mbean = new App(this);

        server.registerMBean(mbean, mbeanName);
        Set<ObjectInstance> instances = server.queryMBeans(new ObjectName(objectName), null);
        ObjectInstance instance = (ObjectInstance) instances.toArray()[0];

        System.out.println("Class Name: " + instance.getClassName());
        System.out.println("Object Name: " + instance.getObjectName());

    }
}
