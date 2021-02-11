package de.sg_o.lib.miniFeedCtrlLib.com;

import java.util.HashMap;
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
        if (transactions.containsKey(request.getId())) return null;
        synchronized(this) {
            toSend.add(trans);
            transactions.put(trans.getId(), trans);
        }
        return trans;
    }

    public abstract void parseResponse(byte[] msg);

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
            for (Map.Entry<Integer, Transaction> entry : transactions.entrySet()) {
                if (entry.getValue().isDone()) {
                    transactions.remove(entry.getKey());
                }
                if (entry.getValue().getElapsedTime() > timeout) {
                    entry.getValue().fail();
                    transactions.remove(entry.getKey());
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
