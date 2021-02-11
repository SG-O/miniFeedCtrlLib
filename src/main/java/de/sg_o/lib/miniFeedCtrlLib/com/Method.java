package de.sg_o.lib.miniFeedCtrlLib.com;

import java.util.HashMap;

public enum Method {
    MAINBOARD_GET_PROTOCOL_VERSION((short) (192 + 256), "GetMainboardProtoVer"),
    MAINBOARD_GET_STATUS((short) (193 + 256), "GetMainboardStatus"),
    MAINBOARD_GET_ERROR((short) (194 + 256), "GetMainboardError"),

    MAINBOARD_GET_ID((short) (64 + 256), "GetMainboardID"),
    MAINBOARD_GET_FW_VERSION((short) (65 + 256), "GetMainboardFwVer"),
    MAINBOARD_GET_HW_VERSION((short) (66 + 256), "GetMainboardHwVer"),
    MAINBOARD_LIST_FEEDERS((short) (67 + 256), "ListFeeders"),
    MAINBOARD_LIST_ALL_FEEDER_STATUS((short) (68 + 256), "ListAllFeederStatus"),
    MAINBOARD_LIST_ALL_FEEDER_ERROR((short) (69 + 256), "ListAllFeederError"),

    MAINBOARD_NOP((short) 192, "MainboardNOP"),
    MAINBOARD_RESET_ERROR((short) 194, "ResetMainboardError"),
    MAINBOARD_FORCE_ERROR((short) 195, "ForceMainboardError"),
    MAINBOARD_RUN_SELF_TEST((short) 196, "RunMainboardSelfTest"),
    MAINBOARD_HW_RESET((short) 197, "MainboardReset"),

    FEEDER_SET_REMAINING_PARTS((short) 0, "SetFeederRemParts"),
    FEEDER_SET_TOTAL_PARTS((short) 1, "SetFeederTotParts"),
    FEEDER_SET_PART_PITCH((short) 2, "SetFeederPartPitch"),
    FEEDER_SET_FEED_SPEED((short) 3, "SetFeederFeedSpeed"),
    FEEDER_SET_LOW_PARTS_WARNING_THRESHOLD((short) 4, "SetFeederLowWarn"),
    FEEDER_SET_SHORT_ID((short) 5, "SetFeederShortID"),
    FEEDER_SET_LONG_ID((short) 6, "SetFeederLongID"),
    FEEDER_SET_DISPLAY_BRIGHTNESS((short) 7, "SetFeederDispBright"),
    FEEDER_SET_MOTOR_DIRECTION((short) 8, "SetFeederMotDir"),
    FEEDER_SET_MOTOR_SLOWDOWN_DELAY((short) 9, "SetFeederMotSlowDelay"),

    FEEDER_GET_ID((short) (0 + 256), "GetFeederID"),
    FEEDER_GET_FW_VERSION((short) (1 + 256), "GetFeederFwVer"),
    FEEDER_GET_HW_VERSION((short) (2 + 256), "GetFeederHwVer"),
    FEEDER_GET_REMAINING_PARTS((short) (3 + 256), "GetFeederRemParts"),
    FEEDER_GET_TOTAL_PARTS((short) (4 + 256), "GetFeederTotParts"),
    FEEDER_GET_LOW_PARTS_WARNING_THRESHOLD((short) (5 + 256), "GetFeederLowWarn"),
    FEEDER_GET_SHORT_ID((short) (6 + 256), "GetFeederShortID"),
    FEEDER_GET_LONG_ID((short) (7 + 256), "GetFeederLongID"),
    FEEDER_GET_MOTOR_SLOWDOWN_DELAY((short) (8 + 256), "GetFeederMotSlowDelay"),
    FEEDER_GET_TOTAL_FEEDS((short) (125 + 256), "GetFeederTotalFeeds"),

    FEEDER_GET_PROTOCOL_VERSION((short) (128 + 256), "GetFeederProtoVer"),
    FEEDER_GET_STATUS((short) (129 + 256), "GetFeederStatus"),
    FEEDER_GET_ERROR((short) (130 + 256), "GetFeederError"),
    FEEDER_GET_PART_PITCH((short) (131 + 256), "GetFeederPartPitch"),
    FEEDER_GET_FEED_SPEED((short) (132 + 256), "GetFeederFeedSpeed"),
    FEEDER_GET_DISPLAY_BRIGHTNESS((short) (133 + 256), "GetFeederDispBright"),
    FEEDER_GET_MOTOR_DIRECTION((short) (134 + 256), "GetFeederMotDir"),

    FEEDER_NOP((short) 128, "FeederNOP"),
    FEEDER_FEED((short) 129, "FeedFeeder"),
    FEEDER_RESET_ERROR((short) 130, "ResetFeederError"),
    FEEDER_FORCE_ERROR((short) 131, "ForceFeederError"),
    FEEDER_RUN_SELF_TEST((short) 132, "RunFeederSelfTest"),
    FEEDER_HW_RESET((short) 252, "FeederReset"),

    UNKNOWN((short)1024, "Unknown");

    private short opcode;
    private String method;

    private static final HashMap<Short, Method> opcodeMap = new HashMap<>();
    private static final HashMap<String, Method> methodMap = new HashMap<>();

    Method(short opcode, String method) {
        this.method = method;
        this.opcode = opcode;
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
