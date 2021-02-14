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

import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcResponse;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JRpcResponseTest {

    @Test
    void generateTest() {
        //noinspection SpellCheckingInspection
        JRpcResponse r0 = new JRpcResponse(new JSONObject("{\"result\":{\"test\":123},\"id\":10,\"jsonrpc\":\"2.0\"}"));
        assertEquals("Response{id=10, error=NO_ERROR, result=[{\"test\":123}]}", r0.toString());
    }
}