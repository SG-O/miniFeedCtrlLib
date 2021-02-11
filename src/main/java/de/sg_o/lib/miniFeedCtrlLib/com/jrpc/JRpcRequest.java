package de.sg_o.lib.miniFeedCtrlLib.com.jrpc;

import de.sg_o.lib.miniFeedCtrlLib.com.Method;
import de.sg_o.lib.miniFeedCtrlLib.com.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.Objects;

public class JRpcRequest extends Request {
    private JSONObject data = new JSONObject();
    private JSONArray orderedData = new JSONArray();
    private boolean namedDataOutput = false;

    public JRpcRequest(int id, Method method) {
        super(id, method);
    }

    public JRpcRequest(byte[] msg) {
        super(-1, Method.UNKNOWN);
        if (msg == null) throw new NullPointerException();
        String msgStrg = new String(msg, StandardCharsets.ISO_8859_1);
        JSONObject msgObj = new JSONObject(msgStrg);
        if (!msgObj.has("jsonrpc")) throw new JSONException("Not a JSON RPC message");
        if (!msgObj.has("method")) throw new JSONException("Not a JSON RPC request");
        super.setMethod(Method.fromMethod(msgObj.getString("method")));
        super.setId(msgObj.optInt("id", -1));
        if (msgObj.has("params")) {
            JSONObject tmpO = msgObj.optJSONObject("params");
            JSONArray tmpA = msgObj.optJSONArray("params");
            if (tmpO != null) {
                this.data = tmpO;
            }
            if (tmpA != null) {
                this.orderedData = tmpA;
            }
        }
    }

    public void setNamedDataOutput(boolean namedDataOutput) {
        this.namedDataOutput = namedDataOutput;
    }

    public JSONObject getData() {
        return data;
    }

    public JSONArray getOrderedData() {
        return orderedData;
    }

    public void addDataNumber(String key, long data) {
        this.data.put(key, data);
        this.orderedData.put(data);
    }

    public void addDataString(String key, String data) {
        this.data.put(key, data);
        this.orderedData.put(data);
    }

    public JSONObject generateJSON() {
        final JSONObject msg = new JSONObject();
        msg.put("jsonrpc", "2.0");
        msg.put("method", super.getMethod().getMethod());
        if (this.namedDataOutput) {
            if (this.data.length() > 0) msg.put("params", this.data);
        } else {
            if (this.orderedData.length() > 0) msg.put("params", this.orderedData);
        }
        if (super.getId() > 0) msg.put("id", super.getId());
        return msg;
    }

    public byte[] generate() {
        return generateString().getBytes(StandardCharsets.ISO_8859_1);
    }

    public String generateString() {
        return generateJSON().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JRpcRequest request = (JRpcRequest) o;
        return getId() == request.getId() && Objects.equals(getMethod(), request.getMethod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMethod());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Request{");
        sb.append("id=").append(super.getId());
        sb.append(", method='").append(super.getMethod().getMethod()).append('\'');
        if (data.length() > orderedData.length()){
            sb.append(", data=").append(data);
        } else {
            sb.append(", data=").append(orderedData);
        }
        sb.append('}');
        return sb.toString();
    }
}
