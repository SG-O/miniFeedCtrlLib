package de.sg_o.lib.miniFeedCtrlLib.com;

import de.sg_o.lib.miniFeedCtrlLib.common.SystemError;

public abstract class Response {
    public enum ResultType {
        ERROR,
        LONG,
        DOUBLE,
        BOOLEAN,
        NULL,
        STRING,
        ARRAY,
        OBJECT,
        BIGINT,
        BIGDECIMAL
    }
    private int id;
    private ResultType resultType = ResultType.ERROR;
    private SystemError error;

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
        return error;
    }

    public void setError(SystemError error) {
        if (error == null) error = SystemError.UNKNOWN;
        this.error = error;
    }

    public boolean isError() {
        if (error == null) return true;
        return error == SystemError.NO_ERROR;
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
    public abstract byte[] generate();
}
