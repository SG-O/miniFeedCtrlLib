package de.sg_o.lib.miniFeedCtrlLib.com;

public enum Method {
    MAINBOARD_GET_FW_VERSION("GetMainboardFwVer", (short) 0),
    UNKNOWN("Unknown", (short)254);

    private String method;
    private short opcode;

    Method(String method, short opcode) {
        this.method = method;
        this.opcode = opcode;
    }
}
