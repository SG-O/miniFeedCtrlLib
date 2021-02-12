package de.sg_o.lib.miniFeedCtrlLib.com.methods;

import de.sg_o.lib.miniFeedCtrlLib.com.Method;
import de.sg_o.lib.miniFeedCtrlLib.com.Transaction;
import de.sg_o.lib.miniFeedCtrlLib.com.TransactionHandler;
import de.sg_o.lib.miniFeedCtrlLib.com.Request;

public class FeederMethods {
    public static Transaction GetFeederFwVer(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_GET_FW_VERSION);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction GetFeederHwVer(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_GET_HW_VERSION);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction GetFeederID(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_GET_ID);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction GetFeederProtoVer(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_GET_PROTOCOL_VERSION);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction GetFeederStatus(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_GET_STATUS);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction GetFeederError(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_GET_ERROR);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction GetFeederTotalFeeds(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_GET_TOTAL_FEEDS);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction FeedFeeder(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_FEED);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction ResetFeederError(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_RESET_ERROR);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction ForceFeederError(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_FORCE_ERROR);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction FeederReset(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(-1, Method.FEEDER_HW_RESET);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction RunFeederSelf(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(-1, Method.FEEDER_RUN_SELF_TEST);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction GetFeederPartPitch(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_GET_PART_PITCH);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction GetFeederFeedSpeed(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_GET_FEED_SPEED);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction GetFeederDispBright(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_GET_DISPLAY_BRIGHTNESS);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction GetFeederMotDir(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_GET_MOTOR_DIRECTION);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction GetFeederRemParts(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_GET_REMAINING_PARTS);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction GetFeederTotParts(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_GET_TOTAL_PARTS);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction GetFeederLowWarn(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_GET_LOW_PARTS_WARNING_THRESHOLD);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction GetFeederMotSlowDelay(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_GET_MOTOR_SLOWDOWN_DELAY);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction GetFeederShortID(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_GET_SHORT_ID);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    public static Transaction GetFeederLongID(TransactionHandler handler, byte slot, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_GET_LONG_ID);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        return handler.putRequest(request);
    }

    //--------------------------------

    public static Transaction SetFeederPartPitch(TransactionHandler handler, byte slot, short partPitch, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        if ((partPitch < 1) || (partPitch > 255)) return null;
        Request request = handler.generateRequest(Method.FEEDER_SET_PART_PITCH);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        request.dataPutUnsignedByte("pitch", partPitch);
        return handler.putRequest(request);
    }

    public static Transaction SetFeederFeedSpeed(TransactionHandler handler, byte slot, short feedSpeed, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        if ((feedSpeed < 1) || (feedSpeed > 255)) return null;
        Request request = handler.generateRequest(Method.FEEDER_SET_FEED_SPEED);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        request.dataPutUnsignedByte("speed", feedSpeed);
        return handler.putRequest(request);
    }

    public static Transaction SetFeederDispBright(TransactionHandler handler, byte slot, short displayBrightness, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        if ((displayBrightness < 1) || (displayBrightness > 255)) return null;
        Request request = handler.generateRequest(Method.FEEDER_SET_DISPLAY_BRIGHTNESS);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        request.dataPutUnsignedByte("bright", displayBrightness);
        return handler.putRequest(request);
    }

    public static Transaction SetFeederMotDir(TransactionHandler handler, byte slot, short motorDirection, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        if (motorDirection < 0) motorDirection = 0;
        if (motorDirection > 1) motorDirection = 1;
        Request request = handler.generateRequest(Method.FEEDER_SET_MOTOR_DIRECTION);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        request.dataPutUnsignedByte("dir", motorDirection);
        return handler.putRequest(request);
    }

    public static Transaction SetFeederRemParts(TransactionHandler handler, byte slot, int remainingParts, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_SET_REMAINING_PARTS);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        request.dataPutInt("remaining", remainingParts);
        return handler.putRequest(request);
    }

    public static Transaction SetFeederTotParts(TransactionHandler handler, byte slot, int totalParts, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        Request request = handler.generateRequest(Method.FEEDER_SET_TOTAL_PARTS);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        request.dataPutInt("total", totalParts);
        return handler.putRequest(request);
    }

    public static Transaction SetFeederLowWarn(TransactionHandler handler, byte slot, int lowPartsWarning, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        if ((lowPartsWarning < 0) || (lowPartsWarning > 65535)) return null;
        Request request = handler.generateRequest(Method.FEEDER_SET_LOW_PARTS_WARNING_THRESHOLD);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        request.dataPutUnsignedShort("warn", lowPartsWarning);
        return handler.putRequest(request);
    }

    public static Transaction SetFeederMotSlowDelay(TransactionHandler handler, byte slot, int motorSlowdownDelay, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        if ((motorSlowdownDelay < 0) || (motorSlowdownDelay > 65535)) return null;
        Request request = handler.generateRequest(Method.FEEDER_SET_MOTOR_SLOWDOWN_DELAY);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        request.dataPutUnsignedShort("delay", motorSlowdownDelay);
        return handler.putRequest(request);
    }

    public static Transaction SetFeederShortID(TransactionHandler handler, byte slot, String shortPartID, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        if (shortPartID == null) return null;
        if ((shortPartID.length() < 1) || (shortPartID.length() > 5)) return null;
        Request request = handler.generateRequest(Method.FEEDER_SET_SHORT_ID);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        request.dataPutString("shortID", shortPartID);
        return handler.putRequest(request);
    }

    public static Transaction SetFeederLongID(TransactionHandler handler, byte slot, String longPartID, boolean namedDataOutput) {
        if ((slot > 63) || (slot < 0)) return null;
        if (longPartID == null) longPartID = "";
        if ((longPartID.length() > 126)) return null;
        Request request = handler.generateRequest(Method.FEEDER_SET_LONG_ID);
        request.setNamedDataOutput(namedDataOutput);
        request.dataPutByte("feederSlot", slot);
        request.dataPutString("longID", longPartID);
        return handler.putRequest(request);
    }
}
