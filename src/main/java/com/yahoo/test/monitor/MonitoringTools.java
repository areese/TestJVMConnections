// Copyright 2016 Yahoo Inc.
// Licensed under the terms of the New-BSD license. Please see LICENSE file in the project root for terms.
package com.yahoo.test.monitor;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class MonitoringTools {
    private final PrintStream out;

    public MonitoringTools(OutputStream out) {
        this.out = new PrintStream(out);
    }

    public MonitoringTools(PrintStream out) {
        this.out = out;
    }

    public void foreachVM(VMRunnable vmr) {
        for (VirtualMachineDescriptor virtualMachineDescriptor : VirtualMachine.list()) {
            vmr.preAttach(virtualMachineDescriptor);
            VirtualMachine virtualMachine = attach(virtualMachineDescriptor);
            if (null != virtualMachine) {
                vmr.attached(virtualMachine);
            }
            detachSilently(virtualMachine);
            vmr.postDetatch(virtualMachineDescriptor);
        }
    }

    public VirtualMachine attach(VirtualMachineDescriptor virtualMachineDescriptor) {
        try {

            VirtualMachine vm = VirtualMachine.attach(virtualMachineDescriptor);
            String agent =
                            vm.getSystemProperties().getProperty("java.home") + File.separator + "lib" + File.separator
                                            + "management-agent.jar";
            try {
                vm.loadAgentLibrary(agent);
            } catch (AgentLoadException e) {
            }

            return vm;
        } catch (AttachNotSupportedException | AgentInitializationException e) {
            out.println("Couldn't attach");
            e.printStackTrace(out);
        } catch (IOException ioe) {
            out.println("Exception attaching or reading a jvm.");
            ioe.printStackTrace();
        }

        return null;
    }

    public static String readSystemProperty(VirtualMachine virtualMachine, String propertyName) throws IOException {
        Properties systemProperties = virtualMachine.getSystemProperties();
        return systemProperties.getProperty(propertyName);
    }

    public void detachSilently(VirtualMachine virtualMachine) {
        if (virtualMachine != null) {
            try {
                virtualMachine.detach();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

}
