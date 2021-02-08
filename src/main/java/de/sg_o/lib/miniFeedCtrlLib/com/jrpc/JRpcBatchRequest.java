package de.sg_o.lib.miniFeedCtrlLib.com.jrpc;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class JRpcBatchRequest {
    private JSONArray requests = new JSONArray();
    private int firstID;

    public JRpcBatchRequest() {
        this.firstID = -1;
    }

    public JRpcBatchRequest(JRpcRequest request, boolean named) {
        this.firstID = -1;
        if (request == null) return;
        this.requests.put(request.generate(named));
        this.firstID = request.getId();
    }

    public JRpcBatchRequest(JSONArray requests) {
        this.firstID = -1;
        if (requests == null) return;
        this.requests = requests;
        for(int i = 0; i < requests.length(); i++) {
            if (this.firstID > 0) break;
            JSONObject obj = requests.optJSONObject(i);
            if (obj == null) continue;
            this.firstID = obj.optInt("id", -1);
        }
    }

    public void addRequests(JRpcRequest request, boolean named) {
        if (request == null) return;
        this.requests.put(request.generate(named));
        if (this.firstID < 0) {
            this.firstID = request.getId();
        }
    }

    public int getFirstID() {
        return firstID;
    }

    public JSONArray generate() {
        return requests;
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
        final StringBuilder sb = new StringBuilder("BatchRequest{");
        sb.append("requests=").append(requests);
        sb.append(", firstID=").append(firstID);
        sb.append('}');
        return sb.toString();
    }
}
