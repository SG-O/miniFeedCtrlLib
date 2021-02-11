package de.sg_o.lib.miniFeedCtrlLib.com.jrpcTest;

import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcResponse;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JRpcResponseTest {

    @Test
    void generateTest() {
        JRpcResponse r0 = new JRpcResponse(new JSONObject("{\"result\":{\"test\":123},\"id\":10,\"jsonrpc\":\"2.0\"}"));
        assertEquals("Response{id=10, error=NO_ERROR, result=[{\"test\":123}]}", r0.toString());
    }
}