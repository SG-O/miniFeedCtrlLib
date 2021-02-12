package de.sg_o.lib.miniFeedCtrlLib.io;

import de.sg_o.lib.miniFeedCtrlLib.com.TransactionHandler;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcRequest;
import de.sg_o.lib.miniFeedCtrlLib.util.ByteArray;

import java.util.LinkedList;

public abstract class IO {
    private static final int MAX_CONSOLE_LENGTH = 127;

    private final TransactionHandler handler;
    private final LinkedList<String> console = new LinkedList<>();

    public IO(TransactionHandler handler) {
        this.handler = handler;
    }

    public abstract String[] listPorts();
    public abstract boolean connect(String port);
    public abstract void disconnect();
    public abstract boolean isConnected();
    public abstract boolean sendNext();
    public abstract void parseReceive();

    public TransactionHandler getHandler() {
        return handler;
    }

    public void putOnConsole(byte[] msg, boolean send) {
        if (send) {
            console.add("-->" + ByteArray.bytesToHex(msg));
        } else {
            console.add("<--" + ByteArray.bytesToHex(msg));
        }
        if (console.size() > MAX_CONSOLE_LENGTH) console.remove();
    }

    public void putOnConsole(String msg, boolean send) {
        if (send) {
            console.add("-->" + msg);
        } else {
            console.add("<--" + msg);
        }
        if (console.size() > MAX_CONSOLE_LENGTH) console.remove();
    }

    public String getConsoleOutput() {
        StringBuilder builder = new StringBuilder();
        for (String s: console) {
            builder.append(s).append('\n');
        }
        return builder.toString();
    }
}
