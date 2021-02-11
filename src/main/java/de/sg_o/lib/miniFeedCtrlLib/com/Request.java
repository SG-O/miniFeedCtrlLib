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

    public void setId(int id) {
        if (id < 1) id = -1;
        this.id = id;
    }

    public abstract void addDataNumber(String key, long data);

    public abstract void addDataString(String key, String data);

    public abstract void setNamedDataOutput(boolean namedDataOutput);

    public void setMethod(Method method) {
        if (method == null) this.method = Method.UNKNOWN;
        this.method = method;
    }

    public int getId() {
        return this.id;
    }

    public Method getMethod() {
        return this.method;
    }

    public abstract byte[] generate();
}
