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

package de.sg_o.lib.miniFeedCtrlLib.io;

import de.sg_o.lib.miniFeedCtrlLib.com.Transaction;
import de.sg_o.lib.miniFeedCtrlLib.com.TransactionHandler;
import de.sg_o.lib.miniFeedCtrlLib.util.ByteArray;

import java.util.LinkedList;

@SuppressWarnings("unused")
public abstract class IO {
    private static final int MAX_CONSOLE_LENGTH = 127;
    public static final String SEPARATOR = "://";

    private final TransactionHandler handler;
    private final LinkedList<String> console = new LinkedList<>();

    public IO(TransactionHandler handler) {
        this.handler = handler;
    }

    public abstract String[] listPorts();
    public abstract void setSpeed(int speed);
    public abstract boolean connect(String port);
    public abstract String getConnectionName();
    public abstract void disconnect();
    public abstract boolean isConnected();
    public abstract boolean sendNext();
    public abstract Transaction parseReceive();

    public TransactionHandler getHandler() {
        return handler;
    }

    protected void putOnConsole(byte[] msg, boolean send) {
        if (send) {
            console.add("-->" + ByteArray.bytesToHex(msg));
        } else {
            console.add("<--" + ByteArray.bytesToHex(msg));
        }
        if (console.size() > MAX_CONSOLE_LENGTH) console.remove();
    }

    protected void putOnConsole(String msg, boolean send) {
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
