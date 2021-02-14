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

import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcBatchResponse;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JRpcBatchResponseTest {

    @Test
    void generateTest() {
        //noinspection SpellCheckingInspection
        JRpcResponse r0 = new JRpcResponse(new JSONObject("{\"result\":{\"test1\":123},\"id\":10,\"jsonrpc\":\"2.0\"}"));

        //noinspection SpellCheckingInspection
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