package de.sg_o.lib.miniFeedCtrlLib.com.jrpc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.util.Objects;

public class JRpcRequest {
    private int id;
    private String method;
    private JSONObject data = new JSONObject();
    private JSONArray orderedData = new JSONArray();

    public JRpcRequest(int id, String method) {
        if (id < 1) id = -1;
        if (method == null) throw new NullPointerException();
        if (method.length() < 1) throw new InvalidParameterException();
        this.id = id;
        this.method = method;
    }

    public JRpcRequest(JSONObject msg) {
        if (msg == null) throw new NullPointerException();
        if (!msg.has("jsonrpc")) throw new JSONException("Not a JSON RPC message");
        if (!msg.has("method")) throw new JSONException("Not a JSON RPC request");
        this.method = msg.getString("method");
        this.id = msg.optInt("id", -1);
        if (msg.has("params")) {
            JSONObject tmpO = msg.optJSONObject("params");
            JSONArray tmpA = msg.optJSONArray("params");
            if (tmpO != null) {
                this.data = tmpO;
            }
            if (tmpA != null) {
                this.orderedData = tmpA;
            }
        }
    }

    public int getId() {
        return id;
    }

    public String getMethod() {
        return method;
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

    public JSONObject generateJSON(boolean named) {
        final JSONObject msg = new JSONObject();
        msg.put("jsonrpc", "2.0");
        msg.put("method", this.method);
        if (named) {
            if (data.length() > 0) msg.put("params", data);
        } else {
            if (orderedData.length() > 0) msg.put("params", orderedData);
        }
        if (this.id > 0) msg.put("id", this.id);
        return msg;
    }

    public String generateString(boolean named) {
        return generateJSON(named).toString();
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
        sb.append("id=").append(id);
        sb.append(", method='").append(method).append('\'');
        if (data.length() > orderedData.length()){
            sb.append(", data=").append(data);
        } else {
            sb.append(", data=").append(orderedData);
        }
        sb.append('}');
        return sb.toString();
    }
}
