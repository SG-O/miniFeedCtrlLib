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

package de.sg_o.lib.miniFeedCtrlLib.com;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

@SuppressWarnings("unused")
public abstract class TransactionHandler {
    private final LinkedList<Transaction> toSend = new LinkedList<>();
    private final HashMap<Integer, Transaction> transactions = new HashMap<>();
    private int counter = 1;

    public TransactionHandler() {
    }

    public abstract Request generateRequest(Method method);
    public abstract Request generateRequest(int id, Method method);

    public Transaction putRequest(Request request) {
        Transaction trans = new Transaction(request);
        if (trans.isDone()) {
            synchronized(this) {
                toSend.add(trans);
            }
            return trans;
        }
        synchronized(this) {
            if (transactions.containsKey(request.getId())) return null;
            toSend.add(trans);
            transactions.put(trans.getId(), trans);
        }
        return trans;
    }

    public abstract Transaction parseResponse(byte[] msg);

    public Transaction removeTransaction(int id) {
        synchronized(this) {
            if (this.transactions.containsKey(id)) {
                return transactions.remove(id);
            }
        }
        return null;
    }

    public int nrToSend() {
        synchronized(this) {
            return toSend.size();
        }
    }

    public Transaction getNextToSend() {
        synchronized(this) {
            if (toSend.size() < 1) return null;
            return toSend.remove();
        }
    }

    public void CheckTimeouts(long timeout) {
        synchronized(this) {
            Iterator<Map.Entry<Integer, Transaction>> it = transactions.entrySet().iterator();
            Map.Entry<Integer, Transaction> item;
            while (it.hasNext()) {
                item = it.next();
                if (item == null) continue;
                if (item.getValue().isDone()) {
                    it.remove();
                }
                if (item.getValue().getElapsedTime() > timeout) {
                    item.getValue().fail();
                    it.remove();
                }
            }
        }
    }

    public int getAndIncCounter() {
        int tmp;
        synchronized(this) {
            tmp = this.counter;
            if (this.counter == Integer.MAX_VALUE) this.counter = 0;
            this.counter++;
        }
        return tmp;
    }
}
