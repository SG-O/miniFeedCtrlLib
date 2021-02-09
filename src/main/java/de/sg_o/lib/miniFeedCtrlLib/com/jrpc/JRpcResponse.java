package de.sg_o.lib.miniFeedCtrlLib.com.jrpc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class JRpcResponse {
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
    private JRpcError error;
    private JSONArray result = new JSONArray();
    private ResultType resultType = ResultType.ERROR;

    public JRpcResponse(int id) {
        if (id < 1) id = -1;
        this.id = id;
    }

    public JRpcResponse(JSONObject msg) {
        if (msg == null) throw new NullPointerException();
        if (!msg.has("jsonrpc")) throw new JSONException("Not a JSON RPC message");
        this.id = msg.optInt("id", -1);
        if (msg.has("error")) {
            this.error = new JRpcError(msg.optJSONObject("error"));
        } else {
            parseResult(msg);
        }
    }

    private void parseResult(JSONObject msg) {
        if (!msg.has("result")) {
            this.error = new JRpcError(null);
            return;
        }
        this.error = null;
        JSONArray tmpA = msg.optJSONArray("result");
        if (tmpA != null) {
            this.result = tmpA;
            parseResultType();
            return;
        }
        Object tmpO = msg.opt("result");
        if (tmpO != null) {
            this.result.put(tmpO);
            parseResultType();
        }
    }

    private void parseResultType() {
        if (result.length() > 1) {
            this.resultType = ResultType.ARRAY;
            return;
        }
        this.resultType = parseType(result.opt(0));

    }

    public boolean isError() {
        return error != null;
    }

    public int getId() {
        return id;
    }

    public JRpcError getError() {
        return error;
    }

    public JSONArray getResult() {
        if (isError()) return null;
        return result;
    }

    public ResultType getResultType() {
        if (isError()) return ResultType.ERROR;
        return resultType;
    }

    public long getAsLong() {
        if (resultType != ResultType.LONG) return 0;
        return result.optLong(0, 0);
    }

    public String getAsString() {
        if (resultType != ResultType.STRING) return null;
        return result.optString(0);
    }

    public long[] getAsLongArray() {
        if (resultType != ResultType.ARRAY) return null;
        long[] longs = new long[result.length()];
        for(int i = 0; i < result.length(); i++) {
            if (parseType(result.opt(i)) != ResultType.LONG) {
                longs[i] = 0;
            }
            longs[i] = result.optLong(i, 0);
        }
        return longs;
    }

    public byte[] getAsByteArray() {
        if (resultType != ResultType.ARRAY) return null;
        byte[] longs = new byte[result.length()];
        for(int i = 0; i < result.length(); i++) {
            if (parseType(result.opt(i)) != ResultType.LONG) {
                longs[i] = 0;
            }
            longs[i] = (byte)(result.optLong(i, 0) & 0xFFL);
        }
        return longs;
    }

    public short[] getAsShortArray() {
        if (resultType != ResultType.ARRAY) return null;
        short[] longs = new short[result.length()];
        for(int i = 0; i < result.length(); i++) {
            if (parseType(result.opt(i)) != ResultType.LONG) {
                longs[i] = 0;
            }
            longs[i] = (short)(result.optLong(i, 0) & 0xFFFFL);
        }
        return longs;
    }

    public int[] getAsIntArray() {
        if (resultType != ResultType.ARRAY) return null;
        int[] longs = new int[result.length()];
        for(int i = 0; i < result.length(); i++) {
            if (parseType(result.opt(i)) != ResultType.LONG) {
                longs[i] = 0;
            }
            longs[i] = (int)(result.optLong(i, 0) & 0xFFFFFFFFL);
        }
        return longs;
    }

    public JSONObject generate() {
        final JSONObject msg = new JSONObject();
        msg.put("jsonrpc", "2.0");
        if (isError()) {
            msg.put("error", error.generate());
        } else {
            msg.put("result", this.result);
        }
        if (this.id < 1) return null;
        msg.put("id", this.id);
        return msg;
    }

    public String generateString() {
        return generate().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JRpcResponse response = (JRpcResponse) o;
        return getId() == response.getId() && Objects.equals(isError(), response.isError());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isError());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Response{");
        sb.append("id=").append(id);
        sb.append(", error=").append(error);
        sb.append(", result=").append(result);
        sb.append('}');
        return sb.toString();
    }

    public static ResultType parseType(Object data) {
        if (data == null) return ResultType.NULL;
        if (data instanceof Boolean) return ResultType.BOOLEAN;
        if (data instanceof BigDecimal) {
            if (((BigDecimal)data).precision() > 15) {
                return ResultType.BIGDECIMAL;
            } else {
                return ResultType.DOUBLE;
            }
        }
        if ((data instanceof Float) || (data instanceof Double)) {
            return ResultType.DOUBLE;
        }
        if (data instanceof BigInteger) return ResultType.BIGINT;
        if (data instanceof Number) return ResultType.LONG;
        if (data instanceof String) return ResultType.STRING;
        if (data instanceof JSONArray) return ResultType.ARRAY;
        if (data instanceof JSONObject) return ResultType.OBJECT;
        return ResultType.NULL;
    }
}
