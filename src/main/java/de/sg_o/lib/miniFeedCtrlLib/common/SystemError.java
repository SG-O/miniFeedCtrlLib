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

package de.sg_o.lib.miniFeedCtrlLib.common;

import java.util.HashMap;
import java.util.Map;

public enum SystemError {
    NO_ERROR((short)0),
    UNKNOWN_ERROR((short)1),
    HARDWARE_FAILURE((short)2),
    POWER_FAILURE((short)3),
    WDT_RESET((short)4),
    INITIALISATION_FAILURE((short)5),
    INVALID_INPUT((short)6),
    UNKNOWN_METHOD((short)7),
    USER_CAUSED((short)8),
    CRC_ERROR((short)9),
    BUSY_EXECUTION_ERROR((short)10),
    PARSER((short)11),

    MOTOR_STALL((short)32),
    CONFIGURATION_ERROR((short)33),
    OUT_OF_COMPONENTS((short)34),
    NO_TAPE((short)35),
    STORAGE_INITIALISATION((short)36),
    INVALID_STORAGE_HEADER((short)37),
    STORAGE_WRITE_ERROR((short)38),
    TOO_MANY_CONFIG_WRITES((short)39),
    TOO_MANY_COUNTER_WRITES((short)40),

    NO_FEEDER((short)64),

    DEBUG_00((short)128),
    DEBUG_01((short)129),
    DEBUG_02((short)130),
    DEBUG_03((short)131),
    DEBUG_04((short)132),
    DEBUG_05((short)133),
    DEBUG_06((short)134),
    DEBUG_07((short)135),
    DEBUG_08((short)136),
    DEBUG_09((short)137),
    DEBUG_10((short)138),
    DEBUG_11((short)139),
    DEBUG_12((short)140),
    DEBUG_13((short)141),
    DEBUG_14((short)142),
    DEBUG_15((short)143),

    UNKNOWN((short)254);

    private final short code;
    private static final Map<Short, SystemError> fromCodeMap = new HashMap<>();

    static {
        for (SystemError error : SystemError.values()) {
            fromCodeMap.put(error.getCode(), error);
        }
    }

    SystemError(short code) {
        this.code = code;
    }

    public static SystemError fromCode(short code) {
        SystemError error = fromCodeMap.get(code);
        if (error == null) {
            return UNKNOWN;
        }
        return error;
    }

    public short getCode() {
        return this.code;
    }
}
