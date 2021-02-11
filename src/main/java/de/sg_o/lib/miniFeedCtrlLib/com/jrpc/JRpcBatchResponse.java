package de.sg_o.lib.miniFeedCtrlLib.com.jrpc;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class JRpcBatchResponse {
    private JSONArray responses = new JSONArray();
    private int firstID;

    public JRpcBatchResponse() {
        this.firstID = -1;
    }

    public JRpcBatchResponse(JRpcResponse response) {
        this.firstID = -1;
        if (response == null) return;
        this.responses.put(response.generateJSON());
        this.firstID = response.getId();
    }

    public JRpcBatchResponse(JSONArray responses) {
        this.firstID = -1;
        if (responses == null) return;
        this.responses = responses;
        for(int i = 0; i < responses.length(); i++) {
            if (this.firstID > 0) break;
            JSONObject obj = responses.optJSONObject(i);
            if (obj == null) continue;
            this.firstID = obj.optInt("id", -1);
        }
    }

    public void addResponse(JRpcResponse response) {
        if (response == null) return;
        this.responses.put(response.generateJSON());
        if (this.firstID < 0) {
            this.firstID = response.getId();
        }
    }

    public int getFirstID() {
        return firstID;
    }

    public JSONArray generate() {
        return responses;
    }

    public String generateString() {
        return generate().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JRpcBatchRequest that = (JRpcBatchRequest) o;
        return getFirstID() == that.getFirstID() && Objects.equals(generate(), that.generate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(generate(), getFirstID());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BatchResponse{");
        sb.append("responses=").append(responses);
        sb.append(", firstID=").append(firstID);
        sb.append('}');
        return sb.toString();
    }
}
