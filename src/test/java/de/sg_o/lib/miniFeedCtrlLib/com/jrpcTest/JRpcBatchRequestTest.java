/*
 *
 * Copyright 2021 SG-O (Joerg Bayer)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.sg_o.lib.miniFeedCtrlLib.com.jrpcTest;

import de.sg_o.lib.miniFeedCtrlLib.com.Method;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcBatchRequest;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcRequest;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JRpcBatchRequestTest {

    @Test
    void generateTest() {
        JRpcRequest r0 = new JRpcRequest(3, Method.MAINBOARD_RUN_SELF_TEST);
        r0.dataPutInt("data1", 10);
        r0.dataPutString("data2", "abc");

        JRpcRequest r1 = new JRpcRequest(4, Method.FEEDER_SET_LONG_ID);
        r1.dataPutString("data3", "foo");
        r1.dataPutInt("data4", -13);

        JRpcBatchRequest br0 = new JRpcBatchRequest();
        br0.addRequests(r0);
        br0.addRequests(r1);

        JRpcBatchRequest br1 = new JRpcBatchRequest(r0);
        br1.addRequests(r1);

        JRpcBatchRequest br2 = new JRpcBatchRequest(br0.generate());
        JRpcBatchRequest br3 = new JRpcBatchRequest(br1.generate());
        JRpcBatchRequest br4 = new JRpcBatchRequest(new JSONArray(br1.generateString()));
        assertEquals(3, br4.generate().optJSONObject(0).optInt("id"));
        assertEquals(br0.generate().optJSONObject(0).toString(), br2.generate().optJSONObject(0).toString());
        assertEquals(br1.generate().optJSONObject(0).toString(), br3.generate().optJSONObject(0).toString());
        assertEquals(3, br0.getFirstID());
        assertEquals(br0.getFirstID(), br1.getFirstID());
        assertEquals(br0.getFirstID(), br2.getFirstID());
        assertEquals(br1.getFirstID(), br3.getFirstID());
    }
}