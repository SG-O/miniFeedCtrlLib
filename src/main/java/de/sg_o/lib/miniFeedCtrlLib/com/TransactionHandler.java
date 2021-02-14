package de.sg_o.lib.miniFeedCtrlLib.com;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

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
