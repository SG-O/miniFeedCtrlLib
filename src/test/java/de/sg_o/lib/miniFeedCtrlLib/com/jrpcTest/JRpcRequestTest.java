package de.sg_o.lib.miniFeedCtrlLib.com.jrpcTest;

import de.sg_o.lib.miniFeedCtrlLib.com.Method;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JRpcRequestTest {

    @Test
    void generateTest() {
        JRpcRequest r0 = new JRpcRequest(-5, Method.FEEDER_SET_TOTAL_PARTS);
        r0.addDataNumber("data1", 10);
        r0.addDataString("data2", "abc");
        r0.setNamedDataOutput(true);
        JRpcRequest r1 = new JRpcRequest(r0.generate());
        assertEquals(r0, r1);
        assertEquals(r0.getId(), r1.getId());
        assertEquals(r0.getMethod(), r1.getMethod());
        assertEquals(r0.getData().toString(), r1.getData().toString());
    }
}