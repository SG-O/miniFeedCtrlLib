package de.sg_o.lib.miniFeedCtrlLib.io.serial;

import com.fazecast.jSerialComm.SerialPort;
import de.sg_o.lib.miniFeedCtrlLib.com.Transaction;
import de.sg_o.lib.miniFeedCtrlLib.com.TransactionHandler;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcRequest;
import de.sg_o.lib.miniFeedCtrlLib.io.IO;

import java.nio.charset.StandardCharsets;

public class Serial extends IO {
    SerialPort serialPort;
    byte[] buffer = new byte[0];

    int baudRate = 115200;


    public Serial(TransactionHandler handler) {
        super(handler);
    }

    public Serial(TransactionHandler handler, int baudRate) {
        super(handler);
        this.baudRate = baudRate;
    }

    @Override
    public String[] listPorts() {
        SerialPort[] ports = SerialPort.getCommPorts();
        String[] portNames = new String[ports.length];
        for (int i = 0; i < ports.length; i++) {
            portNames[i] = ports[i].getSystemPortName();
        }
        return portNames;
    }

    @Override
    public synchronized boolean connect(String port) {
        if (isConnected()) return false;
        serialPort = SerialPort.getCommPort(port);
        serialPort.openPort(0);
        serialPort.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        serialPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING, 500, 0);
        return false;
    }

    @Override
    public synchronized void disconnect() {
        if (serialPort != null) {
            serialPort.closePort();
            serialPort = null;
        }
    }

    @Override
    public synchronized boolean isConnected() {
        if (serialPort == null) return false;
        return serialPort.isOpen();
    }

    @Override
    public synchronized boolean sendNext() {
        if (!isConnected()) return false;
        Transaction t = super.getHandler().getNextToSend();
        if (t == null) return false;
        if (t.isDone()) return false;
        if (t.getRequest() != null) {
            if (t.getRequest() instanceof JRpcRequest) {
                super.putOnConsole(((JRpcRequest)t.getRequest()).generateString(), true);
            }
            byte[] send = t.getRequest().generate();
            int out = serialPort.writeBytes(send, send.length);
            if (out == -1) {
                t.fail();
            }
        }
        return true;
    }

    @Override
    public synchronized void parseReceive() {
        if (serialPort.bytesAvailable() > 0) {
            int toRead = serialPort.bytesAvailable();
            int offset = buffer.length;
            byte[] buf = new byte[offset + toRead];
            System.arraycopy(buffer, 0, buf, 0, offset);
            serialPort.readBytes(buf, toRead, offset);
            this.buffer = buf;
        }
        if (buffer.length < 2) return;
        System.out.println(new String(buffer, StandardCharsets.ISO_8859_1));
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] != '{') {
                while (i < buffer.length) {
                    if (buffer[i] == 0) {
                        break;
                    }
                    i++;
                }
            } else {
                int start = i;
                while (i < buffer.length) {
                    if (buffer[i] == 0) {
                        int length = (i - start) + 1;
                        byte[] msg = new byte[length];
                        byte[] newBuf = new byte[(buffer.length - i) - 1];
                        System.arraycopy(buffer, start, msg, 0, length);
                        System.arraycopy(buffer, i + 1, newBuf, 0, newBuf.length);
                        this.buffer = newBuf;
                        i = -1;
                        String msgStrg = new String(msg, StandardCharsets.ISO_8859_1);
                        super.putOnConsole(msgStrg, false);
                        super.getHandler().parseResponse(msg);
                        break;
                    }
                    i++;
                }
            }
        }
    }
}
