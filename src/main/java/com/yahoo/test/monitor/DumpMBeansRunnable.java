package com.yahoo.test.monitor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Set;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class DumpMBeansRunnable implements VMRunnable {
    private final PrintStream out;
    private final int onlyPid;
    private final String onlyPidString;

    public DumpMBeansRunnable(PrintStream out) {
        this(out, -1);
    }

    public DumpMBeansRunnable(OutputStream out) {
        this(new PrintStream(out), -1);
    }

    public DumpMBeansRunnable(PrintStream printStream, int onlyPid) {
        this.out = printStream;
        this.onlyPid = onlyPid;
        this.onlyPidString = Integer.toString(onlyPid);
    }

    @Override
    public void preAttach(VirtualMachineDescriptor virtualMachineDescriptor) {
        out.println("============ Show JVM: pid = " + virtualMachineDescriptor.id() + " "
                        + virtualMachineDescriptor.displayName());
    }

    @Override
    public void attached(VirtualMachine virtualMachine) {
        if (-1 != this.onlyPid && !onlyPidString.equals(virtualMachine.id())) {
            return;
        }
        try {

            String localAgent = virtualMachine.startLocalManagementAgent();

            JMXConnector jmxConnector = JMXConnectorFactory.connect(new JMXServiceURL(localAgent));

            MBeanServerConnection mBeanServerConnection = jmxConnector.getMBeanServerConnection();

            // now we can finally iterate.
            Set<ObjectName> queryNames = mBeanServerConnection.queryNames(null, null);
            for (ObjectName on : queryNames) {
                out.println(on);

                try {
                    MBeanInfo mBeanInfo = mBeanServerConnection.getMBeanInfo(on);
                    if (null != mBeanInfo) {
                        for (MBeanAttributeInfo a : mBeanInfo.getAttributes()) {
                            out.println(a.getName());
                        }
                    }
                } catch (InstanceNotFoundException | IntrospectionException | ReflectionException e) {
                    e.printStackTrace(out);
                }
            }
        } catch (IOException e) {
            out.println("Reading system property failed");
            e.printStackTrace(out);
        }
    }

    @Override
    public void postDetatch(VirtualMachineDescriptor virtualMachineDescriptor) {

    }
}
