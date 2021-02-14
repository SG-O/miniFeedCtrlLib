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
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JRpcRequestTest {

    @Test
    void generateTest() {
        JRpcRequest r0 = new JRpcRequest(-5, Method.FEEDER_SET_TOTAL_PARTS);
        r0.dataPutInt("data1", 10);
        r0.dataPutString("data2", "abc");
        r0.setNamedDataOutput(true);
        JRpcRequest r1 = new JRpcRequest(r0.generate());
        assertEquals(r0, r1);
        assertEquals(r0.getId(), r1.getId());
        assertEquals(r0.getMethod(), r1.getMethod());
        assertEquals(r0.getData().toString(), r1.getData().toString());
    }
}