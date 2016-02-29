package com.yahoo.test.monitor;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public interface VMRunnable {

    void preAttach(VirtualMachineDescriptor virtualMachineDescriptor);

    void attached(VirtualMachine virtualMachine);

    void postDetatch(VirtualMachineDescriptor virtualMachineDescriptor);

}
