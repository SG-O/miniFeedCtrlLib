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

import java.util.HashMap;

@SuppressWarnings("unused")
public enum Method {
    MAINBOARD_GET_PROTOCOL_VERSION((short) (192 + 256), "GetMainboardProtoVer", false, MessageDataType.LONG),
    MAINBOARD_GET_STATUS((short) (193 + 256), "GetMainboardStatus", false, MessageDataType.LONG),
    MAINBOARD_GET_ERROR((short) (194 + 256), "GetMainboardError", false, MessageDataType.LONG),

    MAINBOARD_GET_ID((short) (64 + 256), "GetMainboardID", false, MessageDataType.ARRAY),
    MAINBOARD_GET_FW_VERSION((short) (65 + 256), "GetMainboardFwVer", false, MessageDataType.LONG),
    MAINBOARD_GET_HW_VERSION((short) (66 + 256), "GetMainboardHwVer", false, MessageDataType.LONG),
    MAINBOARD_LIST_FEEDERS((short) (67 + 256), "ListFeeders", false, MessageDataType.ARRAY),
    MAINBOARD_LIST_ALL_FEEDER_STATUS((short) (68 + 256), "ListAllFeederStatus", false, MessageDataType.ARRAY),
    MAINBOARD_LIST_ALL_FEEDER_ERROR((short) (69 + 256), "ListAllFeederError", false, MessageDataType.ARRAY),

    MAINBOARD_NOP((short) 192, "MainboardNOP", true, MessageDataType.LONG),
    MAINBOARD_RESET_ERROR((short) 194, "ResetMainboardError", true, MessageDataType.LONG),
    MAINBOARD_FORCE_ERROR((short) 195, "ForceMainboardError", true, MessageDataType.LONG),
    MAINBOARD_RUN_SELF_TEST((short) 196, "RunMainboardSelfTest", true, MessageDataType.LONG),
    MAINBOARD_HW_RESET((short) 197, "MainboardReset", true, MessageDataType.LONG),

    FEEDER_SET_REMAINING_PARTS((short) 0, "SetFeederRemParts", true, MessageDataType.LONG),
    FEEDER_SET_TOTAL_PARTS((short) 1, "SetFeederTotParts", true, MessageDataType.LONG),
    FEEDER_SET_PART_PITCH((short) 2, "SetFeederPartPitch", true, MessageDataType.LONG),
    FEEDER_SET_FEED_SPEED((short) 3, "SetFeederFeedSpeed", true, MessageDataType.LONG),
    FEEDER_SET_LOW_PARTS_WARNING_THRESHOLD((short) 4, "SetFeederLowWarn", true, MessageDataType.LONG),
    FEEDER_SET_SHORT_ID((short) 5, "SetFeederShortID", true, MessageDataType.LONG),
    FEEDER_SET_LONG_ID((short) 6, "SetFeederLongID", true, MessageDataType.LONG),
    @SuppressWarnings("SpellCheckingInspection")
    FEEDER_SET_DISPLAY_BRIGHTNESS((short) 7, "SetFeederDispBright", true, MessageDataType.LONG),
    FEEDER_SET_MOTOR_DIRECTION((short) 8, "SetFeederMotDir", true, MessageDataType.LONG),
    FEEDER_SET_MOTOR_SLOWDOWN_DELAY((short) 9, "SetFeederMotSlowDelay", true, MessageDataType.LONG),

    @SuppressWarnings("PointlessArithmeticExpression")
    FEEDER_GET_ID((short) (0 + 256), "GetFeederID", false, MessageDataType.ARRAY),
    FEEDER_GET_FW_VERSION((short) (1 + 256), "GetFeederFwVer", false, MessageDataType.LONG),
    FEEDER_GET_HW_VERSION((short) (2 + 256), "GetFeederHwVer", false, MessageDataType.LONG),
    FEEDER_GET_REMAINING_PARTS((short) (3 + 256), "GetFeederRemParts", false, MessageDataType.LONG),
    FEEDER_GET_TOTAL_PARTS((short) (4 + 256), "GetFeederTotParts", false, MessageDataType.LONG),
    FEEDER_GET_LOW_PARTS_WARNING_THRESHOLD((short) (5 + 256), "GetFeederLowWarn", false, MessageDataType.LONG),
    FEEDER_GET_SHORT_ID((short) (6 + 256), "GetFeederShortID", false, MessageDataType.STRING),
    FEEDER_GET_LONG_ID((short) (7 + 256), "GetFeederLongID", false, MessageDataType.STRING),
    FEEDER_GET_MOTOR_SLOWDOWN_DELAY((short) (8 + 256), "GetFeederMotSlowDelay", false, MessageDataType.LONG),
    FEEDER_GET_TOTAL_FEEDS((short) (125 + 256), "GetFeederTotalFeeds", false, MessageDataType.LONG),

    FEEDER_GET_PROTOCOL_VERSION((short) (128 + 256), "GetFeederProtoVer", false, MessageDataType.LONG),
    FEEDER_GET_STATUS((short) (129 + 256), "GetFeederStatus", false, MessageDataType.LONG),
    FEEDER_GET_ERROR((short) (130 + 256), "GetFeederError", false, MessageDataType.LONG),
    FEEDER_GET_PART_PITCH((short) (131 + 256), "GetFeederPartPitch", false, MessageDataType.LONG),
    FEEDER_GET_FEED_SPEED((short) (132 + 256), "GetFeederFeedSpeed", false, MessageDataType.LONG),
    @SuppressWarnings("SpellCheckingInspection")
    FEEDER_GET_DISPLAY_BRIGHTNESS((short) (133 + 256), "GetFeederDispBright", false, MessageDataType.LONG),
    FEEDER_GET_MOTOR_DIRECTION((short) (134 + 256), "GetFeederMotDir", false, MessageDataType.LONG),

    FEEDER_NOP((short) 128, "FeederNOP", true, MessageDataType.LONG),
    FEEDER_FEED((short) 129, "FeedFeeder", true, MessageDataType.LONG),
    FEEDER_RESET_ERROR((short) 130, "ResetFeederError", true, MessageDataType.LONG),
    FEEDER_FORCE_ERROR((short) 131, "ForceFeederError", true, MessageDataType.LONG),
    FEEDER_RUN_SELF_TEST((short) 132, "RunFeederSelfTest", true, MessageDataType.LONG),
    FEEDER_HW_RESET((short) 252, "FeederReset", true, MessageDataType.LONG),

    UNKNOWN((short)1024, "Unknown", true, MessageDataType.NULL);

    private final short opcode;
    private final String method;
    private final boolean write;
    private final MessageDataType result;

    private static final HashMap<Short, Method> opcodeMap = new HashMap<>();
    private static final HashMap<String, Method> methodMap = new HashMap<>();

    Method(short opcode, String method, boolean write, MessageDataType result) {
        this.method = method;
        this.opcode = opcode;
        this.write = write;
        this.result = result;
    }

    static {
        for (Method m : Method.values()) {
            opcodeMap.put(m.getOpcode(), m);
            methodMap.put(m.getMethod(), m);
        }
    }

    public String getMethod() {
        return this.method;
    }

    public short getOpcode() {
        return this.opcode;
    }

    public boolean isWrite() {
        return write;
    }

    public MessageDataType getResultType() {
        return result;
    }

    public static HashMap<Short, Method> getOpcodeMap() {
        return opcodeMap;
    }

    public static HashMap<String, Method> getMethodMap() {
        return methodMap;
    }

    public static Method fromOpcode(short opcode) {
        Method m = opcodeMap.get(opcode);
        if (m == null) return UNKNOWN;
        return m;
    }

    public static Method fromMethod(String method) {
        Method m = methodMap.get(method);
        if (m == null) return UNKNOWN;
        return m;
    }
}
