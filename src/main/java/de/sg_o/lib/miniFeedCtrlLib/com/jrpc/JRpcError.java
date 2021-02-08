package de.sg_o.lib.miniFeedCtrlLib.com.jrpc;

import org.json.JSONObject;

import java.util.Objects;

public class JRpcError {
    private int code;
    private String message;

    public JRpcError(int code, String message) {
        this.code = code;
        if (message == null) message = "";
        this.message = message;
    }

    public JRpcError(JSONObject error) {
        if (error == null) {
            this.code = -32700;
            this.message = "";
            return;
        }
        this.code = error.optInt("code", -32700);
        String message = error.optString("message");
        if (message == null) message = "";
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public JSONObject generate() {
        JSONObject obj = new JSONObject();
        obj.put("code", this.code);
        obj.put("message", message);
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JRpcError jRpcError = (JRpcError) o;
        return getCode() == jRpcError.getCode() && Objects.equals(getMessage(), jRpcError.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getMessage());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JRpcError{");
        sb.append("code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
