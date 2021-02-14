/*
 *
 * Copyright 2021 SG-O (Joerg Bayer)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.sg_o.lib.miniFeedCtrlLib.io.serial;

import com.fazecast.jSerialComm.SerialPort;
import de.sg_o.lib.miniFeedCtrlLib.com.Transaction;
import de.sg_o.lib.miniFeedCtrlLib.com.TransactionHandler;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcRequest;
import de.sg_o.lib.miniFeedCtrlLib.io.IO;

import java.nio.charset.StandardCharsets;

@SuppressWarnings("unused")
public class Serial extends IO {
    public static final String PREFIX = "serial";

    SerialPort serialPort;
    byte[] buffer = new byte[0];

    int baudRate = 115200;
    Transaction running;

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
            portNames[i] = PREFIX + SEPARATOR + ports[i].getSystemPortName();
        }
        return portNames;
    }

    @Override
    public void setSpeed(int speed) {
        this.baudRate = speed;
    }

    @Override
    public synchronized boolean connect(String port) {
        if (isConnected()) return false;
        String[] split = port.split(SEPARATOR);
        if (split.length != 2) return false;
        if (!split[0].equalsIgnoreCase(PREFIX)) return false;
        serialPort = SerialPort.getCommPort(split[1]);
        serialPort.openPort(0);
        serialPort.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        serialPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING, 500, 0);
        buffer = new byte[0];
        return true;
    }

    @Override
    public String getConnectionName() {
        if (!isConnected()) return null;
        return PREFIX + this.serialPort.getSystemPortName();
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
        if (running != null) {
            if (!(running.isDone() || running.hasFailed())) {
                return false;
            }
        }
        Transaction t = super.getHandler().getNextToSend();
        if (t == null) return false;
        if (t.isDone()) return false;
        if (t.getRequest() != null) {
            running = t;
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
    public synchronized Transaction parseReceive() {
        if (!isConnected()) return null;
        if (serialPort.bytesAvailable() > 0) {
            int toRead = serialPort.bytesAvailable();
            int offset = buffer.length;
            byte[] buf = new byte[offset + toRead];
            System.arraycopy(buffer, 0, buf, 0, offset);
            serialPort.readBytes(buf, toRead, offset);
            this.buffer = buf;
        }
        if (buffer.length < 2) return null;
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
                        String msgString = new String(msg, StandardCharsets.ISO_8859_1);
                        super.putOnConsole(msgString, false);
                        return super.getHandler().parseResponse(msg);
                    }
                    i++;
                }
            }
        }
        return null;
    }
}
