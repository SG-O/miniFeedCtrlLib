package de.sg_o.lib.miniFeedCtrlLib.com.jrpc;

import org.json.JSONException;
import org.json.JSONObject;

public class JRpcTransaction {
    private final JRpcRequest request;
    private JRpcResponse response;
    private final long startTime;
    private boolean failed = false;

    public JRpcTransaction(JRpcRequest request) {
        this.request = request;
        this.response = null;
        this.startTime = System.currentTimeMillis();
    }

    public JRpcRequest getRequest() {
        return this.request;
    }

    public int getId() {
        if (this.request == null) return -1;
        return this.request.getId();
    }

    public JRpcResponse getResponse() {
        return this.response;
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    public boolean isDone() {
        if (this.request == null) return true;
        if (this.request.getId() < 1) return true;
        return this.response != null;
    }

    public boolean hasFailed() {
        return this.failed;
    }

    public String generateRequest(boolean named) {
        if (this.request == null) return null;
        return this.request.generateString(named);
    }

    public void fail() {
        if (isDone()) return;
        this.failed = true;
    }

    public void setResponse(String msg) {
        if (msg == null) {
            fail();
            return;
        }
        if (isDone()) return;
        try {
            JRpcResponse response = new JRpcResponse(new JSONObject(msg));
            if (response.getId() != this.request.getId()) {
                fail();
                return;
            }
            this.response = response;
        } catch (NullPointerException | JSONException ignore){
            fail();
        }
    }
}
