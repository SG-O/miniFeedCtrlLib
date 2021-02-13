package de.sg_o.lib.miniFeedCtrlLib.com;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;

public enum MessageDataType {
    ERROR,
    LONG,
    DOUBLE,
    BOOLEAN,
    NULL,
    STRING,
    ARRAY,
    OBJECT,
    BIGINT,
    BIGDECIMAL;

    public static MessageDataType parseType(Object data) {
        if (data == null) return MessageDataType.NULL;
        if (data instanceof Boolean) return MessageDataType.BOOLEAN;
        if (data instanceof BigDecimal) {
            if (((BigDecimal)data).precision() > 15) {
                return MessageDataType.BIGDECIMAL;
            } else {
                return MessageDataType.DOUBLE;
            }
        }
        if ((data instanceof Float) || (data instanceof Double)) {
            return MessageDataType.DOUBLE;
        }
        if (data instanceof BigInteger) return MessageDataType.BIGINT;
        if (data instanceof Number) return MessageDataType.LONG;
        if (data instanceof String) return MessageDataType.STRING;
        if (data instanceof JSONArray) return MessageDataType.ARRAY;
        if (data instanceof JSONObject) return MessageDataType.OBJECT;
        return MessageDataType.NULL;
    }
}
