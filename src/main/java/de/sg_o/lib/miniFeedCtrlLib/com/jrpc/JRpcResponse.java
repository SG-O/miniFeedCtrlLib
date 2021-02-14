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

import de.sg_o.lib.miniFeedCtrlLib.com.Response;
import de.sg_o.lib.miniFeedCtrlLib.com.MessageDataType;
import de.sg_o.lib.miniFeedCtrlLib.common.SystemError;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static de.sg_o.lib.miniFeedCtrlLib.com.MessageDataType.parseType;

@SuppressWarnings("unused")
public class JRpcResponse extends Response {
    private JSONArray result = new JSONArray();

    public JRpcResponse(int id) {
        super(id);
    }

    public JRpcResponse(JSONObject msg) {
        super(-1);
        if (msg == null) throw new NullPointerException();
        //noinspection SpellCheckingInspection
        if (!msg.has("jsonrpc")) throw new JSONException("Not a JSON RPC message");
        super.setId(msg.optInt("id", -1));
        if (msg.has("error")) {
            super.setError(SystemError.fromCode((short)msg.getJSONObject("error").optInt("code", 254)));
        } else {
            parseResult(msg);
        }
    }

    private void parseResult(JSONObject msg) {
        if (!msg.has("result")) {
            super.setError(SystemError.UNKNOWN);
            return;
        }
        super.setError(SystemError.NO_ERROR);
        JSONArray tmpA = msg.optJSONArray("result");
        if (tmpA != null) {
            this.result = tmpA;
            parseResultType();
            return;
        }
        Object tmpO = msg.opt("result");
        if (tmpO != null) {
            this.result.put(tmpO);
            parseResultType();
        }
    }

    private void parseResultType() {
        if (result.length() > 1) {
            super.setResultType(MessageDataType.ARRAY);
            return;
        }
        super.setResultType(parseType(result.opt(0)));

    }

    public JSONArray getResult() {
        if (super.isError()) return null;
        return result;
    }

    public MessageDataType getResultType() {
        if (super.isError()) return MessageDataType.ERROR;
        return super.getResultType();
    }

    public long getResultAsLong() {
        if (super.getResultType() != MessageDataType.LONG) return 0;
        return result.optLong(0, -1);
    }

    public String getResultAsString() {
        if (super.getResultType() != MessageDataType.STRING) return null;
        return result.optString(0);
    }

    public long[] getResultAsLongArray() {
        if (super.getResultType() != MessageDataType.ARRAY) return null;
        long[] longs = new long[result.length()];
        for(int i = 0; i < result.length(); i++) {
            if (parseType(result.opt(i)) != MessageDataType.LONG) {
                longs[i] = -1;
                continue;
            }
            longs[i] = result.optLong(i, -1);
        }
        return longs;
    }

    public byte[] getResultAsByteArray() {
        if (super.getResultType() != MessageDataType.ARRAY) return null;
        byte[] longs = new byte[result.length()];
        for(int i = 0; i < result.length(); i++) {
            if (parseType(result.opt(i)) != MessageDataType.LONG) {
                longs[i] = -1;
                continue;
            }
            longs[i] = (byte)(result.optLong(i, -1) & 0xFFL);
        }
        return longs;
    }

    public void resultPutUnsignedByte(short data) {
        result.put(data & 0xFF);
    }
    public void resultPutUnsignedShort(int data) {
        result.put(data & 0xFFFF);
    }
    public void resultPutUnsignedInt(long data) {
        result.put(data & 0xFFFFFFFFL);
    }

    public void resultPutByte(byte data) {
        result.put(data);
    }

    public void resultPutShort(short data) {
        result.put(data);
    }

    public void resultPutInt(int data) {
        result.put(data);
    }

    public void resultPutLong(long data) {
        result.put(data);
    }

    public void resultPutString(String data) {
        result.put(data);
    }

    public short[] getResultAsShortArray() {
        if (super.getResultType() != MessageDataType.ARRAY) return null;
        short[] longs = new short[result.length()];
        for(int i = 0; i < result.length(); i++) {
            if (parseType(result.opt(i)) != MessageDataType.LONG) {
                longs[i] = -1;
                continue;
            }
            longs[i] = (short)(result.optLong(i, -1) & 0xFFFFL);
        }
        return longs;
    }

    public int[] getResultAsIntArray() {
        if (super.getResultType() != MessageDataType.ARRAY) return null;
        int[] longs = new int[result.length()];
        for(int i = 0; i < result.length(); i++) {
            if (parseType(result.opt(i)) != MessageDataType.LONG) {
                longs[i] = -1;
                continue;
            }
            longs[i] = (int)(result.optLong(i, -1) & 0xFFFFFFFFL);
        }
        return longs;
    }

    public JSONObject generateJSON() {
        final JSONObject msg = new JSONObject();
        //noinspection SpellCheckingInspection
        msg.put("jsonrpc", "2.0");
        if (isError()) {
            JSONObject error = new JSONObject();
            error.put("code", super.getError().getCode());
            error.put("message", super.getError().toString());
            msg.put("error", error);
        } else {
            msg.put("result", this.result);
        }
        if (super.getId() < 1) return null;
        msg.put("id", super.getId());
        return msg;
    }

    public String generateString() {
        return generateJSON().toString();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JRpcResponse response = (JRpcResponse) o;
        return getId() == response.getId() && Objects.equals(isError(), response.isError());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isError());
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Response{");
        sb.append("id=").append(super.getId());
        sb.append(", error=").append(super.getError());
        sb.append(", result=").append(result);
        sb.append('}');
        return sb.toString();
    }
}
