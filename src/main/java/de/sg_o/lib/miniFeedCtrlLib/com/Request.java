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

package de.sg_o.lib.miniFeedCtrlLib.com;

@SuppressWarnings("unused")
public abstract class Request {
    private int id;
    private Method method;

    public Request(int id, Method method) {
        if (id < 1) id = -1;
        this.id = id;
        if (method == null) this.method = Method.UNKNOWN;
        this.method = method;
    }

    protected void setId(int id) {
        if (id < 1) id = -1;
        this.id = id;
    }

    public abstract void dataPutUnsignedByte(String key, short data);
    public abstract void dataPutUnsignedShort(String key, int data);
    public abstract void dataPutUnsignedInt(String key, long data);
    public abstract void dataPutByte(String key, byte data);
    public abstract void dataPutShort(String key, short data);
    public abstract void dataPutInt(String key, int data);
    public abstract void dataPutLong(String key, long data);
    public abstract void dataPutString(String key, String data);

    public abstract void setNamedDataOutput(boolean namedDataOutput);

    public abstract MessageDataType getDataType(String key, int index);

    public abstract long getDataAsLong(String key, int index);
    public abstract String getDataAsString(String key, int index);

    protected void setMethod(Method method) {
        if (method == null) this.method = Method.UNKNOWN;
        this.method = method;
    }

    public int getId() {
        return this.id;
    }

    public Method getMethod() {
        return this.method;
    }

    public abstract byte[] generate();
}
