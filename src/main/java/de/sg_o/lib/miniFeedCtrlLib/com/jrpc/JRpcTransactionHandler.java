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

package de.sg_o.lib.miniFeedCtrlLib.com.jrpc;

import de.sg_o.lib.miniFeedCtrlLib.com.Method;
import de.sg_o.lib.miniFeedCtrlLib.com.Request;
import de.sg_o.lib.miniFeedCtrlLib.com.Transaction;
import de.sg_o.lib.miniFeedCtrlLib.com.TransactionHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class JRpcTransactionHandler extends TransactionHandler {

    public Request generateRequest(Method method) {
        return new JRpcRequest(super.getAndIncCounter(), method);
    }

    @Override
    public Request generateRequest(int id, Method method) {
        return new JRpcRequest(id, method);
    }

    public Transaction parseResponse(byte[] msg) {
        try {
            if (msg == null) throw new NullPointerException();
            String msgString = new String(msg, StandardCharsets.ISO_8859_1);
            JSONObject responseObject = new JSONObject(msgString);
            int id = responseObject.optInt("id", -1);
            if (id < 1) return null;
            JRpcResponse response = new JRpcResponse(responseObject);
            Transaction t = super.removeTransaction(id);
            t.setResponse(response);
            return t;
        } catch (NullPointerException | JSONException ignore){
        }
        return null;
    }
}
