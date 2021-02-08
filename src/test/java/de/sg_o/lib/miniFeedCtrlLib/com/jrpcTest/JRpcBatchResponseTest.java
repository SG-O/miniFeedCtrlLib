package de.sg_o.lib.miniFeedCtrlLib.com.jrpcTest;

import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcBatchResponse;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JRpcBatchResponseTest {

    @Test
    void generateTest() {
        JRpcResponse r0 = new JRpcResponse(new JSONObject("{\"result\":{\"test1\":123},\"id\":10,\"jsonrpc\":\"2.0\"}"));

        JRpcResponse r1 = new JRpcResponse(new JSONObject("{\"error\": {\"code\": -32600, \"message\": \"Invalid Request\"},\"id\":11,\"jsonrpc\":\"2.0\"}"));

        JRpcBatchResponse br0 = new JRpcBatchResponse();
        br0.addResponse(r0);
        br0.addResponse(r1);

        JRpcBatchResponse br1 = new JRpcBatchResponse(r0);
        br1.addResponse(r1);

        JRpcBatchResponse br2 = new JRpcBatchResponse(br0.generate());
        JRpcBatchResponse br3 = new JRpcBatchResponse(br1.generate());
        JRpcBatchResponse br4 = new JRpcBatchResponse(new JSONArray(br1.generateString()));
        assertEquals(10, br4.generate().optJSONObject(0).optInt("id"));
        assertEquals(br0.generate().optJSONObject(0).toString(), br2.generate().optJSONObject(0).toString());
        assertEquals(br1.generate().optJSONObject(0).toString(), br3.generate().optJSONObject(0).toString());
        assertEquals(10, br0.getFirstID());
        assertEquals(br0.getFirstID(), br1.getFirstID());
        assertEquals(br0.getFirstID(), br2.getFirstID());
        assertEquals(br1.getFirstID(), br3.getFirstID());
    }
}