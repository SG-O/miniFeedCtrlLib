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

package de.sg_o.lib.miniFeedCtrlLib.base;

public enum Status {
    ERROR((short)0),
    READY((short)1),
    BUSY((short)2),
    UNKNOWN((short)254);

    private final short code;

    Status(short code) {
        this.code = code;
    }

    public static Status fromCode(short code) {
        switch (code) {
            case 0:
                return ERROR;
            case 1:
                return READY;
            case 2:
                return BUSY;
        }
        return UNKNOWN;
    }

    public short getCode() {
        return this.code;
    }
}
