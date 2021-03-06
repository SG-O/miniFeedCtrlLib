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

package de.sg_o.lib.miniFeedCtrlLib.io.dummySerial;

import de.sg_o.lib.miniFeedCtrlLib.com.Response;
import de.sg_o.lib.miniFeedCtrlLib.com.Transaction;
import de.sg_o.lib.miniFeedCtrlLib.com.TransactionHandler;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcRequest;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcResponse;
import de.sg_o.lib.miniFeedCtrlLib.common.InvalidDataException;
import de.sg_o.lib.miniFeedCtrlLib.io.IO;

import java.util.LinkedList;

public class DummySerial extends IO {
    public static final String PREFIX = "dummy";

    private final String portName;
    private boolean connected = false;
    private final DummyHardware hw;

    private final LinkedList<Response> responses = new LinkedList<>();

    public DummySerial(TransactionHandler handler, String portName, int lowerID) throws InvalidDataException {
        super(handler);
        if (handler == null) throw new NullPointerException();
        this.portName = portName;
        this.hw = new DummyHardware(lowerID);
    }

    public String[] listPorts() {
        return new String[]{PREFIX + SEPARATOR + portName};
    }

    @Override
    public void setSpeed(int speed) {
    }

    public synchronized boolean connect(String port) {
        String[] split = port.split(SEPARATOR);
        if (split.length != 2) return false;
        if (!split[0].equalsIgnoreCase(PREFIX)) return false;
        if (!this.portName.equals(split[1])) return false;
        if (isConnected()) return false;
        this.connected = true;
        return true;
    }

    public String getConnectionName() {
        if (!connected) return null;
        return PREFIX + this.portName;
    }

    public synchronized boolean isConnected() {
        return connected;
    }

    public DummyHardware getHw() {
        return hw;
    }

    public synchronized void disconnect() {
        this.connected = false;
    }

    public synchronized boolean sendNext() {
        if (!isConnected()) return false;
        Transaction t = super.getHandler().getNextToSend();
        if (t == null) return false;
        if (t.isDone()) return false;
        Response resp = null;
        if (t.getRequest() != null) {
            if (t.getRequest() instanceof JRpcRequest) {
                super.putOnConsole(((JRpcRequest)t.getRequest()).generateString(), true);
            }
            resp = hw.parseTransaction(t.getRequest().generate());
        }
        if (resp != null) {
            responses.add(resp);
        }
        return true;
    }

    public synchronized Transaction parseReceive() {
        if (!isConnected()) return null;
        if (this.responses.size() > 0) {
            Response resp = this.responses.remove();
            if (resp instanceof JRpcResponse) {
                super.putOnConsole(((JRpcResponse)resp).generateString(), false);
            }
            return super.getHandler().parseResponse(resp.generate());
        }
        return null;
    }
}
