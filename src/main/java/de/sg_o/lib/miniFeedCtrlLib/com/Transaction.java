package de.sg_o.lib.miniFeedCtrlLib.com;

public class Transaction {
    private final Request request;
    private Response response;
    private final long startTime;
    private boolean failed = false;

    public Transaction(Request request) {
        this.request = request;
        this.response = null;
        this.startTime = System.currentTimeMillis();
    }

    public Request getRequest() {
        return this.request;
    }

    public int getId() {
        if (this.request == null) return -1;
        return this.request.getId();
    }

    public Response getResponse() {
        return this.response;
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    public boolean isDone() {
        if (this.failed) return false;
        if (this.request == null) return true;
        if (this.request.getId() < 1) return true;
        return this.response != null;
    }

    public boolean hasFailed() {
        return this.failed;
    }

    public byte[] generateRequest(boolean named) {
        if (this.request == null) return null;
        return this.request.generate();
    }

    public void fail() {
        if (isDone()) return;
        this.failed = true;
    }

    public void setResponse(Response response) {
        if (response == null) {
            fail();
            return;
        }
        if (isDone()) return;
        if (response.getId() != this.request.getId()) {
            fail();
            return;
        }
        this.response = response;
    }
}
