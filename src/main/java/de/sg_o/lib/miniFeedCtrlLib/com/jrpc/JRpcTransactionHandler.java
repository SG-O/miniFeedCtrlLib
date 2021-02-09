package de.sg_o.lib.miniFeedCtrlLib.com.jrpc;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class JRpcTransactionHandler {
    private final LinkedList<JRpcTransaction> toSend = new LinkedList<>();
    private final HashMap<Integer, JRpcTransaction> transactions = new HashMap<>();
    private int counter = 1;

    public JRpcTransactionHandler() {
    }

    public JRpcTransaction putRequest(JRpcRequest request) {
        JRpcTransaction trans = new JRpcTransaction(request);
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

    public void parseResponse(String msg) {
        try {
            JSONObject response = new JSONObject(msg);
            int id = response.optInt("id", -1);
            if (id < 1) return;
            synchronized(this) {
                if (transactions.containsKey(id)) {
                    transactions.get(id).setResponse(msg);
                    transactions.remove(id);
                }
            }
        } catch (NullPointerException | JSONException ignore){
        }
    }

    public int nrToSend() {
        synchronized(this) {
            return toSend.size();
        }
    }

    public JRpcTransaction getNextToSend() {
        synchronized(this) {
            if (toSend.size() < 1) return null;
            return toSend.remove();
        }
    }

    public void CheckTimeouts(long timeout) {
        synchronized(this) {
            for (Map.Entry<Integer, JRpcTransaction> entry : transactions.entrySet()) {
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
