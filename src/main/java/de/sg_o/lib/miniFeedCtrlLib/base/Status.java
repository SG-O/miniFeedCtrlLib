package de.sg_o.lib.miniFeedCtrlLib.base;

public enum Status {
    ERROR((short)0),
    READY((short)1),
    BUSY((short)2),
    UNKNOWN((short)254);

    private short code;

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
