package com.yahoo.test.monitor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class ShowDataRunnable implements VMRunnable {
    private final PrintStream out;

    public ShowDataRunnable(PrintStream out) {
        this.out = out;
    }

    public ShowDataRunnable(OutputStream out) {
        this.out = new PrintStream(out);
    }

    @Override
    public void preAttach(VirtualMachineDescriptor virtualMachineDescriptor) {
        out.println("============ Show JVM: pid = " + virtualMachineDescriptor.id() + " "
                        + virtualMachineDescriptor.displayName());
    }

    @Override
    public void attached(VirtualMachine virtualMachine) {
        try {
            out.println("     Java version = " + MonitoringTools.readSystemProperty(virtualMachine, "java.version"));
            out.println("     Java command = " + MonitoringTools.readSystemProperty(virtualMachine, "sun.java.command"));
        } catch (IOException e) {
            out.println("Reading system property failed");
            e.printStackTrace(out);
        }
    }

    @Override
    public void postDetatch(VirtualMachineDescriptor virtualMachineDescriptor) {

    }
}
