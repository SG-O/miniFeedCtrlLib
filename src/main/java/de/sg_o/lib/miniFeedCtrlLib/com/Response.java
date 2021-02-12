package de.sg_o.lib.miniFeedCtrlLib.com;

import de.sg_o.lib.miniFeedCtrlLib.common.SystemError;

public abstract class Response {
    private int id;
    private ResultType resultType = ResultType.ERROR;
    private SystemError error = SystemError.NO_ERROR;

    public Response(int id) {
        if (id < 1) id = -1;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SystemError getError() {
        if (error == null) return SystemError.UNKNOWN;
        return error;
    }

    public void setError(SystemError error) {
        if (error == null) error = SystemError.UNKNOWN;
        this.error = error;
    }

    public boolean isError() {
        if (this.error == null) return true;
        return this.error != SystemError.NO_ERROR;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        if (resultType == null) resultType = ResultType.ERROR;
        this.resultType = resultType;
    }

    public abstract long getResultAsLong();
    public abstract String getResultAsString();
    public abstract long[] getResultAsLongArray();
    public abstract byte[] getResultAsByteArray();
    public abstract short[] getResultAsShortArray();
    public abstract int[] getResultAsIntArray();

    public abstract void resultPutUnsignedByte(short data);
    public abstract void resultPutUnsignedShort(int data);
    public abstract void resultPutUnsignedInt(long data);
    public abstract void resultPutByte(byte data);
    public abstract void resultPutShort(short data);
    public abstract void resultPutInt(int data);
    public abstract void resultPutLong(long data);
    public abstract void resultPutString(String data);

    public abstract byte[] generate();
}
