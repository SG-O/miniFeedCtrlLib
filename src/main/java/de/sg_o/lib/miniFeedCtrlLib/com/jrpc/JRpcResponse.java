package de.sg_o.lib.miniFeedCtrlLib.com.jrpc;

import de.sg_o.lib.miniFeedCtrlLib.com.Response;
import de.sg_o.lib.miniFeedCtrlLib.com.ResultType;
import de.sg_o.lib.miniFeedCtrlLib.common.SystemError;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class JRpcResponse extends Response {
    private JSONArray result = new JSONArray();

    public JRpcResponse(int id) {
        super(id);
    }

    public JRpcResponse(JSONObject msg) {
        super(-1);
        if (msg == null) throw new NullPointerException();
        if (!msg.has("jsonrpc")) throw new JSONException("Not a JSON RPC message");
        super.setId(msg.optInt("id", -1));
        if (msg.has("error")) {
            super.setError(SystemError.fromCode((short)msg.getJSONObject("error").optInt("code", 254)));
        } else {
            parseResult(msg);
        }
    }

    private void parseResult(JSONObject msg) {
        if (!msg.has("result")) {
            super.setError(SystemError.UNKNOWN);
            return;
        }
        super.setError(SystemError.NO_ERROR);
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
            super.setResultType(ResultType.ARRAY);
            return;
        }
        super.setResultType(parseType(result.opt(0)));

    }

    public JSONArray getResult() {
        if (super.isError()) return null;
        return result;
    }

    public ResultType getResultType() {
        if (super.isError()) return ResultType.ERROR;
        return super.getResultType();
    }

    public long getResultAsLong() {
        if (super.getResultType() != ResultType.LONG) return 0;
        return result.optLong(0, 0);
    }

    public String getResultAsString() {
        if (super.getResultType() != ResultType.STRING) return null;
        return result.optString(0);
    }

    public long[] getResultAsLongArray() {
        if (super.getResultType() != ResultType.ARRAY) return null;
        long[] longs = new long[result.length()];
        for(int i = 0; i < result.length(); i++) {
            if (parseType(result.opt(i)) != ResultType.LONG) {
                longs[i] = 0;
            }
            longs[i] = result.optLong(i, 0);
        }
        return longs;
    }

    public byte[] getResultAsByteArray() {
        if (super.getResultType() != ResultType.ARRAY) return null;
        byte[] longs = new byte[result.length()];
        for(int i = 0; i < result.length(); i++) {
            if (parseType(result.opt(i)) != ResultType.LONG) {
                longs[i] = 0;
            }
            longs[i] = (byte)(result.optLong(i, 0) & 0xFFL);
        }
        return longs;
    }

    public void resultPutUnsignedByte(short data) {
        result.put(data & 0xFF);
    }
    public void resultPutUnsignedShort(int data) {
        result.put(data & 0xFFFF);
    }
    public void resultPutUnsignedInt(long data) {
        result.put(data & 0xFFFFFFFFL);
    }

    public void resultPutByte(byte data) {
        result.put(data);
    }

    public void resultPutShort(short data) {
        result.put(data);
    }

    public void resultPutInt(int data) {
        result.put(data);
    }

    public void resultPutLong(long data) {
        result.put(data);
    }

    public void resultPutString(String data) {
        result.put(data);
    }

    public short[] getResultAsShortArray() {
        if (super.getResultType() != ResultType.ARRAY) return null;
        short[] longs = new short[result.length()];
        for(int i = 0; i < result.length(); i++) {
            if (parseType(result.opt(i)) != ResultType.LONG) {
                longs[i] = 0;
            }
            longs[i] = (short)(result.optLong(i, 0) & 0xFFFFL);
        }
        return longs;
    }

    public int[] getResultAsIntArray() {
        if (super.getResultType() != ResultType.ARRAY) return null;
        int[] longs = new int[result.length()];
        for(int i = 0; i < result.length(); i++) {
            if (parseType(result.opt(i)) != ResultType.LONG) {
                longs[i] = 0;
            }
            longs[i] = (int)(result.optLong(i, 0) & 0xFFFFFFFFL);
        }
        return longs;
    }

    public JSONObject generateJSON() {
        final JSONObject msg = new JSONObject();
        msg.put("jsonrpc", "2.0");
        if (isError()) {
            JSONObject error = new JSONObject();
            error.put("code", super.getError().getCode());
            error.put("message", super.getError().toString());
            msg.put("error", error);
        } else {
            msg.put("result", this.result);
        }
        if (super.getId() < 1) return null;
        msg.put("id", super.getId());
        return msg;
    }

    public String generateString() {
        return generateJSON().toString();
    }

    public byte[] generate() {
        String str = generateString();
        CharsetEncoder enc = StandardCharsets.ISO_8859_1.newEncoder();
        int len = str.length();
        byte[] buf = new byte[len + 1];
        ByteBuffer byteBuf = ByteBuffer.wrap(buf);
        enc.encode(CharBuffer.wrap(str), byteBuf, true);
        buf[len] = 0;
        return buf;
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
        sb.append("id=").append(super.getId());
        sb.append(", error=").append(super.getError());
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
