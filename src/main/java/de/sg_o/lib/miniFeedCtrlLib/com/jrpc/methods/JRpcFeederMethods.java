package de.sg_o.lib.miniFeedCtrlLib.com.jrpc.methods;

import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcRequest;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcTransaction;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcTransactionHandler;

public class JRpcFeederMethods {
    public static JRpcTransaction GetFeederFwVer(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetFeederFwVer");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetFeederHwVer(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetFeederHwVer");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetFeederID(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetFeederID");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetFeederProtoVer(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetFeederProtoVer");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetFeederStatus(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetFeederStatus");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetFeederError(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetFeederError");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetFeederTotalFeeds(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetFeederTotalFeeds");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction FeedFeeder(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "FeedFeeder");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction ResetFeederError(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "ResetFeederError");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction ForceFeederError(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "ForceFeederError");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction FeederReset(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(-1, "FeederReset");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction RunFeederSelf(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(-1, "RunFeederSelf");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetFeederPartPitch(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetFeederPartPitch");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetFeederFeedSpeed(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetFeederFeedSpeed");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetFeederDispBright(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetFeederDispBright");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetFeederMotDir(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetFeederMotDir");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetFeederRemParts(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetFeederRemParts");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetFeederTotParts(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetFeederTotParts");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetFeederLowWarn(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetFeederLowWarn");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetFeederMotSlowDelay(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetFeederMotSlowDelay");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetFeederShortID(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetFeederShortID");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetFeederLongID(JRpcTransactionHandler handler, byte slot) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetFeederLongID");
        request.addDataNumber("feederSlot", slot);
        return handler.putRequest(request);
    }

    //--------------------------------

    public static JRpcTransaction SetFeederPartPitch(JRpcTransactionHandler handler, byte slot, short partPitch) {
        if ((slot > 63) || (slot < 0)) return null;
        if ((partPitch < 1) || (partPitch > 255)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "SetFeederPartPitch");
        request.addDataNumber("feederSlot", slot);
        request.addDataNumber("pitch", partPitch);
        return handler.putRequest(request);
    }

    public static JRpcTransaction SetFeederFeedSpeed(JRpcTransactionHandler handler, byte slot, short feedSpeed) {
        if ((slot > 63) || (slot < 0)) return null;
        if ((feedSpeed < 1) || (feedSpeed > 255)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "SetFeederFeedSpeed");
        request.addDataNumber("feederSlot", slot);
        request.addDataNumber("speed", feedSpeed);
        return handler.putRequest(request);
    }

    public static JRpcTransaction SetFeederDispBright(JRpcTransactionHandler handler, byte slot, short displayBrightness) {
        if ((slot > 63) || (slot < 0)) return null;
        if ((displayBrightness < 1) || (displayBrightness > 255)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "SetFeederDispBright");
        request.addDataNumber("feederSlot", slot);
        request.addDataNumber("bright", displayBrightness);
        return handler.putRequest(request);
    }

    public static JRpcTransaction SetFeederMotDir(JRpcTransactionHandler handler, byte slot, short motorDirection) {
        if ((slot > 63) || (slot < 0)) return null;
        if (motorDirection < 0) motorDirection = 0;
        if (motorDirection > 1) motorDirection = 1;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "SetFeederMotDir");
        request.addDataNumber("feederSlot", slot);
        request.addDataNumber("dir", motorDirection);
        return handler.putRequest(request);
    }

    public static JRpcTransaction SetFeederRemParts(JRpcTransactionHandler handler, byte slot, int remainingParts) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "SetFeederRemParts");
        request.addDataNumber("feederSlot", slot);
        request.addDataNumber("remaining", remainingParts);
        return handler.putRequest(request);
    }

    public static JRpcTransaction SetFeederTotParts(JRpcTransactionHandler handler, byte slot, int totalParts) {
        if ((slot > 63) || (slot < 0)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "SetFeederTotParts");
        request.addDataNumber("feederSlot", slot);
        request.addDataNumber("total", totalParts);
        return handler.putRequest(request);
    }

    public static JRpcTransaction SetFeederLowWarn(JRpcTransactionHandler handler, byte slot, int lowPartsWarning) {
        if ((slot > 63) || (slot < 0)) return null;
        if ((lowPartsWarning < 0) || (lowPartsWarning > 65535)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "SetFeederLowWarn");
        request.addDataNumber("feederSlot", slot);
        request.addDataNumber("warn", lowPartsWarning);
        return handler.putRequest(request);
    }

    public static JRpcTransaction SetFeederMotSlowDelay(JRpcTransactionHandler handler, byte slot, int motorSlowdownDelay) {
        if ((slot > 63) || (slot < 0)) return null;
        if ((motorSlowdownDelay < 0) || (motorSlowdownDelay > 65535)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "SetFeederMotSlowDelay");
        request.addDataNumber("feederSlot", slot);
        request.addDataNumber("delay", motorSlowdownDelay);
        return handler.putRequest(request);
    }

    public static JRpcTransaction SetFeederShortID(JRpcTransactionHandler handler, byte slot, String shortPartID) {
        if ((slot > 63) || (slot < 0)) return null;
        if (shortPartID == null) return null;
        if ((shortPartID.length() < 1) || (shortPartID.length() > 5)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "SetFeederShortID");
        request.addDataNumber("feederSlot", slot);
        request.addDataString("shortID", shortPartID);
        return handler.putRequest(request);
    }

    public static JRpcTransaction SetFeederLongID(JRpcTransactionHandler handler, byte slot, String longPartID) {
        if ((slot > 63) || (slot < 0)) return null;
        if (longPartID == null) longPartID = "";
        if ((longPartID.length() > 126)) return null;
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "SetFeederLongID");
        request.addDataNumber("feederSlot", slot);
        request.addDataString("longID", longPartID);
        return handler.putRequest(request);
    }
}
