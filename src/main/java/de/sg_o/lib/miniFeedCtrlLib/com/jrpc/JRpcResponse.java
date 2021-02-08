package de.sg_o.lib.miniFeedCtrlLib.com.jrpc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class JRpcResponse {
    private int id;
    private JRpcError error;
    private JSONArray result = new JSONArray();

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
            return;
        }
        Object tmpO = msg.opt("result");
        if (tmpO != null) {
            this.result.put(tmpO);
        }
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
}
