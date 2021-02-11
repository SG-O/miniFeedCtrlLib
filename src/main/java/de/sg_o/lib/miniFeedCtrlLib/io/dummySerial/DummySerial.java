package de.sg_o.lib.miniFeedCtrlLib.io.dummySerial;

import de.sg_o.lib.miniFeedCtrlLib.com.Transaction;
import de.sg_o.lib.miniFeedCtrlLib.com.TransactionHandler;

public class DummySerial {
    private String portName;
    private TransactionHandler handler;
    private boolean connected = false;

    public DummySerial(TransactionHandler handler, String portName) {
        if (handler == null) throw new NullPointerException();
        this.handler = handler;
        this.portName = portName;
    }

    public String[] listPorts() {
        String[] ports = {portName};
        return ports;
    }

    public boolean connect(String port) {
        if (this.portName != port) return false;
        if (!this.connected) return false;
        this.connected = true;
        return true;
    }

    public void disconnect() {
        this.connected = false;
    }

    public boolean sendNext() {
        Transaction t = handler.getNextToSend();
        if (t == null) return false;
        return true;
    }

    public void parseReceive() {

    }
}
