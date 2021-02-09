package de.sg_o.lib.miniFeedCtrlLib.com;

public abstract class Request {
    private int id;
    private Method method;

    public Request(int id, Method method) {
        if (id < 1) id = -1;
        this.id = id;
        if (method == null) this.method = Method.UNKNOWN;
        this.method = method;
    }

    public Request(byte[] msg) {
        this.id = -1;
        this.method = Method.UNKNOWN;
    }

    public int getId() {
        return this.id;
    }

    public Method getMethod() {
        return this.method;
    }

    public abstract byte[] generate();
}
