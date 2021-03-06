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

import de.sg_o.lib.miniFeedCtrlLib.com.MessageDataType;
import de.sg_o.lib.miniFeedCtrlLib.com.Method;
import de.sg_o.lib.miniFeedCtrlLib.com.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static de.sg_o.lib.miniFeedCtrlLib.com.MessageDataType.parseType;

public class JRpcRequest extends Request {
    private JSONObject data = new JSONObject();
    private JSONArray orderedData = new JSONArray();
    private boolean namedDataOutput = false;

    public JRpcRequest(int id, Method method) {
        super(id, method);
    }

    public JRpcRequest(byte[] msg) {
        super(-1, Method.UNKNOWN);
        if (msg == null) throw new NullPointerException();
        String msgString = new String(msg, StandardCharsets.ISO_8859_1);
        JSONObject msgObj = new JSONObject(msgString);
        //noinspection SpellCheckingInspection
        if (!msgObj.has("jsonrpc")) throw new JSONException("Not a JSON RPC message");
        if (!msgObj.has("method")) throw new JSONException("Not a JSON RPC request");
        super.setMethod(Method.fromMethod(msgObj.getString("method")));
        super.setId(msgObj.optInt("id", -1));
        if (msgObj.has("params")) {
            JSONObject tmpO = msgObj.optJSONObject("params");
            JSONArray tmpA = msgObj.optJSONArray("params");
            if (tmpO != null) {
                this.data = tmpO;
            }
            if (tmpA != null) {
                this.orderedData = tmpA;
            }
        }
    }

    public void setNamedDataOutput(boolean namedDataOutput) {
        this.namedDataOutput = namedDataOutput;
    }

    @Override
    public MessageDataType getDataType(String key, int index) {
        if (index < this.orderedData.length()) {
            return parseType(this.orderedData.opt(index));
        }
        if (this.data.has(key)) {
            return parseType(this.data);
        }
        return null;
    }

    @Override
    public long getDataAsLong(String key, int index) {
        if (getDataType(key, index) != MessageDataType.LONG) return -1;
        if (index < this.orderedData.length()) {
            return this.orderedData.optLong(index, -1);
        }
        if (this.data.has(key)) {
            return this.data.optLong(key, -1);
        }
        return -1;
    }

    @Override
    public String getDataAsString(String key, int index) {
        if (getDataType(key, index) != MessageDataType.STRING) return null;
        if (index < this.orderedData.length()) {
            return this.orderedData.optString(index);
        }
        if (this.data.has(key)) {
            return this.data.optString(key);
        }
        return null;
    }

    public JSONObject getData() {
        return data;
    }

    public JSONArray getOrderedData() {
        return orderedData;
    }

    @Override
    public void dataPutUnsignedByte(String key, short data) {
        this.data.put(key, data);
        this.orderedData.put(data);
    }

    @Override
    public void dataPutUnsignedShort(String key, int data) {
        this.data.put(key, data);
        this.orderedData.put(data);
    }

    @Override
    public void dataPutUnsignedInt(String key, long data) {
        this.data.put(key, data);
        this.orderedData.put(data);
    }

    @Override
    public void dataPutByte(String key, byte data) {
        this.data.put(key, data);
        this.orderedData.put(data);
    }

    @Override
    public void dataPutShort(String key, short data) {
        this.data.put(key, data);
        this.orderedData.put(data);
    }

    @Override
    public void dataPutInt(String key, int data) {
        this.data.put(key, data);
        this.orderedData.put(data);
    }

    @Override
    public void dataPutLong(String key, long data) {
        this.data.put(key, data);
        this.orderedData.put(data);
    }

    @Override
    public void dataPutString(String key, String data) {
        this.data.put(key, data);
        this.orderedData.put(data);
    }

    public JSONObject generateJSON() {
        final JSONObject msg = new JSONObject();
        //noinspection SpellCheckingInspection
        msg.put("jsonrpc", "2.0");
        msg.put("method", super.getMethod().getMethod());
        if (this.namedDataOutput) {
            if (this.data.length() > 0) msg.put("params", this.data);
        } else {
            if (this.orderedData.length() > 0) msg.put("params", this.orderedData);
        }
        if (super.getId() > 0) msg.put("id", super.getId());
        return msg;
    }

    public byte[] generate() {
        String str = generateString();
        CharsetEncoder enc = StandardCharsets.ISO_8859_1.newEncoder();
        int len = str.length();
        byte[] buf = new byte[len + 1];
        ByteBuffer byteBuf = ByteBuffer.wrap(buf);
        enc.encode(CharBuffer.wrap(str), byteBuf, true);
        buf[len] = 0;
        return buf;
    }

    public String generateString() {
        return generateJSON().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JRpcRequest request = (JRpcRequest) o;
        return getId() == request.getId() && Objects.equals(getMethod(), request.getMethod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMethod());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Request{");
        sb.append("id=").append(super.getId());
        sb.append(", method='").append(super.getMethod().getMethod()).append('\'');
        if (data.length() > orderedData.length()){
            sb.append(", data=").append(data);
        } else {
            sb.append(", data=").append(orderedData);
        }
        sb.append('}');
        return sb.toString();
    }
}
