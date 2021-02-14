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

public enum SystemError {
    NO_ERROR((short)0),
    UNKNOWN_ERROR((short)1),
    MOTOR_STALL((short)2),
    CONFIGURATION_ERROR((short)3),
    OUT_OF_COMPONENTS((short)4),
    NO_TAPE((short)5),
    HARDWARE_FAILURE((short)6),
    POWER_FAILURE((short)7),
    WDT_RESET((short)8),
    INITIALISATION_FAILURE((short)9),
    INVALID_INPUT((short)10),
    UNKNOWN_METHOD((short)11),
    USER_CAUSED((short)12),
    CRC_ERROR((short)13),
    BUSY_EXECUTION_ERROR((short)14),
    NO_FEEDER((short)15),
    PARSE_ERROR((short) 16),
    UNKNOWN((short)254);

    private final short code;

    SystemError(short code) {
        this.code = code;
    }

    public static SystemError fromCode(short code) {
        switch (code) {
            case 0:
                return NO_ERROR;
            case 1:
                return UNKNOWN_ERROR;
            case 2:
                return MOTOR_STALL;
            case 3:
                return CONFIGURATION_ERROR;
            case 4:
                return OUT_OF_COMPONENTS;
            case 5:
                return NO_TAPE;
            case 6:
                return HARDWARE_FAILURE;
            case 7:
                return POWER_FAILURE;
            case 8:
                return WDT_RESET;
            case 9:
                return INITIALISATION_FAILURE;
            case 10:
                return INVALID_INPUT;
            case 11:
                return UNKNOWN_METHOD;
            case 12:
                return USER_CAUSED;
            case 13:
                return CRC_ERROR;
            case 14:
                return BUSY_EXECUTION_ERROR;
            case 15:
                return NO_FEEDER;
            case 16:
                return PARSE_ERROR;
        }
        return UNKNOWN;
    }

    public short getCode() {
        return this.code;
    }
}
