package de.sg_o.lib.miniFeedCtrlLib.common;

import de.sg_o.lib.miniFeedCtrlLib.base.Feeder;
import de.sg_o.lib.miniFeedCtrlLib.base.Status;
import de.sg_o.lib.miniFeedCtrlLib.com.Response;
import de.sg_o.lib.miniFeedCtrlLib.com.MessageDataType;
import de.sg_o.lib.miniFeedCtrlLib.com.Transaction;
import de.sg_o.lib.miniFeedCtrlLib.com.TransactionHandler;

public class FeederParser {
    public static void parseFeederUpdate(Transaction t, Feeder feeder, Feeder feederBackup) throws InvalidDataException {
        if (t == null) return;
        if (t.getResponse().isError()) return;
        Response resp = t.getResponse();
        switch (t.getRequest().getMethod()) {
            case FEEDER_GET_FW_VERSION:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("FEEDER_GET_FW_VERSION", -1,-1);
                feeder.setFirmwareVersion(resp.getResultAsLong());
                feederBackup.setFirmwareVersion(resp.getResultAsLong());
                break;
            case FEEDER_GET_HW_VERSION:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("FEEDER_GET_HW_VERSION", -1,-1);
                feeder.setHardwareVersion(resp.getResultAsLong());
                feederBackup.setHardwareVersion(resp.getResultAsLong());
                break;
            case FEEDER_GET_REMAINING_PARTS:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("FEEDER_GET_REMAINING_PARTS", -1,-1);
                feeder.setRemainingParts((int)resp.getResultAsLong());
                feederBackup.setRemainingParts((int)resp.getResultAsLong());
                break;
            case FEEDER_GET_TOTAL_PARTS:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("FEEDER_GET_TOTAL_PARTS", -1,-1);
                feeder.setTotalParts((int)resp.getResultAsLong());
                feederBackup.setTotalParts((int)resp.getResultAsLong());
                break;
            case FEEDER_GET_LOW_PARTS_WARNING_THRESHOLD:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("FEEDER_GET_LOW_PARTS_WARNING_THRESHOLD", -1,-1);
                feeder.setLowPartsWarning((int)resp.getResultAsLong());
                feederBackup.setLowPartsWarning((int)resp.getResultAsLong());
                break;
            case FEEDER_GET_SHORT_ID:
                if (resp.getResultType() != MessageDataType.STRING) throw new InvalidDataException("FEEDER_GET_SHORT_ID", -1,-1);
                feeder.setShortPartID(resp.getResultAsString());
                feederBackup.setShortPartID(resp.getResultAsString());
                break;
            case FEEDER_GET_LONG_ID:
                if (resp.getResultType() != MessageDataType.STRING) throw new InvalidDataException("FEEDER_GET_LONG_ID", -1,-1);
                feeder.setLongPartID(resp.getResultAsString());
                feederBackup.setLongPartID(resp.getResultAsString());
                break;
            case FEEDER_GET_MOTOR_SLOWDOWN_DELAY:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("FEEDER_GET_MOTOR_SLOWDOWN_DELAY", -1,-1);
                feeder.setMotorSlowdownDelay((int)resp.getResultAsLong());
                feederBackup.setMotorSlowdownDelay((int)resp.getResultAsLong());
                break;
            case FEEDER_GET_TOTAL_FEEDS:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("FEEDER_GET_TOTAL_FEEDS", -1,-1);
                feeder.setTotalFeeds(resp.getResultAsLong());
                feederBackup.setTotalFeeds(resp.getResultAsLong());
                break;
            case FEEDER_GET_PROTOCOL_VERSION:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("FEEDER_GET_PROTOCOL_VERSION", -1,-1);
                feeder.setProtocolVersion((short) resp.getResultAsLong());
                feederBackup.setProtocolVersion((short) resp.getResultAsLong());
                break;
            case FEEDER_GET_STATUS:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("FEEDER_GET_STATUS", -1,-1);
                feeder.setStatus(Status.fromCode((short)resp.getResultAsLong()));
                feederBackup.setStatus(Status.fromCode((short)resp.getResultAsLong()));
                break;
            case FEEDER_GET_ERROR:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("FEEDER_GET_ERROR", -1,-1);
                feeder.setError(SystemError.fromCode((short)resp.getResultAsLong()));
                feederBackup.setError(SystemError.fromCode((short)resp.getResultAsLong()));
                break;
            case FEEDER_GET_PART_PITCH:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("FEEDER_GET_PART_PITCH", -1,-1);
                feeder.setPartPitch((short) resp.getResultAsLong());
                feederBackup.setPartPitch((short) resp.getResultAsLong());
                break;
            case FEEDER_GET_FEED_SPEED:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("FEEDER_GET_FEED_SPEED", -1,-1);
                feeder.setFeedSpeed((short) resp.getResultAsLong());
                feederBackup.setFeedSpeed((short) resp.getResultAsLong());
                break;
            case FEEDER_GET_DISPLAY_BRIGHTNESS:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("FEEDER_GET_DISPLAY_BRIGHTNESS", -1,-1);
                feeder.setDisplayBrightness((short) resp.getResultAsLong());
                feederBackup.setDisplayBrightness((short) resp.getResultAsLong());
                break;
            case FEEDER_GET_MOTOR_DIRECTION:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("FEEDER_GET_MOTOR_DIRECTION", -1,-1);
                feeder.setMotorDirection((short) resp.getResultAsLong());
                feederBackup.setMotorDirection((short) resp.getResultAsLong());
                break;
        }
    }

    public static void sendFeederChanges(TransactionHandler handler, Feeder feeder, Feeder feederBackup, boolean namedOutput) throws InvalidDataException {
        if (handler == null || feeder == null) return;
        boolean sendAll = false;
        if (feederBackup == null) {
            sendAll = true;
            feederBackup = feeder;
        }
        if (sendAll || (feeder.getPartPitch() != feederBackup.getPartPitch())) {
            FeederMethods.SetFeederPartPitch(handler, feeder.getSlot(), feeder.getPartPitch(), namedOutput);
            feederBackup.setPartPitch(feeder.getPartPitch());
        }
        if (sendAll || (feeder.getFeedSpeed() != feederBackup.getFeedSpeed())) {
            FeederMethods.SetFeederFeedSpeed(handler, feeder.getSlot(), feeder.getFeedSpeed(), namedOutput);
            feederBackup.setFeedSpeed(feeder.getFeedSpeed());
        }
        if (sendAll || (feeder.getDisplayBrightness() != feederBackup.getDisplayBrightness())) {
            FeederMethods.SetFeederDispBright(handler, feeder.getSlot(), feeder.getDisplayBrightness(), namedOutput);
            feederBackup.setDisplayBrightness(feeder.getDisplayBrightness());
        }
        if (sendAll || (feeder.getMotorDirection() != feederBackup.getMotorDirection())) {
            FeederMethods.SetFeederMotDir(handler, feeder.getSlot(), feeder.getMotorDirection(), namedOutput);
            feederBackup.setMotorDirection(feeder.getMotorDirection());
        }
        if (sendAll || (feeder.getRemainingParts() != feederBackup.getRemainingParts())) {
            FeederMethods.SetFeederRemParts(handler, feeder.getSlot(), feeder.getRemainingParts(), namedOutput);
            feederBackup.setRemainingParts(feeder.getRemainingParts());
        }
        if (sendAll || (feeder.getTotalParts() != feederBackup.getTotalParts())) {
            FeederMethods.SetFeederTotParts(handler, feeder.getSlot(), feeder.getTotalParts(), namedOutput);
            feederBackup.setTotalParts(feeder.getTotalParts());
        }
        if (sendAll || (feeder.getLowPartsWarning() != feederBackup.getLowPartsWarning())) {
            FeederMethods.SetFeederLowWarn(handler, feeder.getSlot(), feeder.getLowPartsWarning(), namedOutput);
            feederBackup.setLowPartsWarning(feeder.getLowPartsWarning());
        }
        if (sendAll || (feeder.getMotorSlowdownDelay() != feederBackup.getMotorSlowdownDelay())) {
            FeederMethods.SetFeederMotSlowDelay(handler, feeder.getSlot(), feeder.getMotorSlowdownDelay(), namedOutput);
            feederBackup.setMotorSlowdownDelay(feeder.getMotorSlowdownDelay());
        }
        if (sendAll || (!feeder.getShortPartID().equals(feederBackup.getShortPartID()))) {
            FeederMethods.SetFeederShortID(handler, feeder.getSlot(), feeder.getShortPartID(), namedOutput);
            feederBackup.setShortPartID(feeder.getShortPartID());
        }
        if (sendAll || (!feeder.getLongPartID().equals(feederBackup.getLongPartID()))) {
            FeederMethods.SetFeederLongID(handler, feeder.getSlot(), feeder.getLongPartID(), namedOutput);
            feederBackup.setLongPartID(feeder.getLongPartID());
        }
    }

    public static void requestAllFeederParameters(TransactionHandler handler, Feeder feeder, boolean namedOutput) {
        if (feeder == null) return;
        if (handler == null) return;
        if (feeder.getSlot() < 0) return;
        FeederMethods.GetFeederFwVer(handler, feeder.getSlot(), namedOutput);
        FeederMethods.GetFeederHwVer(handler, feeder.getSlot(), namedOutput);
        FeederMethods.GetFeederProtoVer(handler, feeder.getSlot(), namedOutput);
        FeederMethods.GetFeederStatus(handler, feeder.getSlot(), namedOutput);
        FeederMethods.GetFeederError(handler, feeder.getSlot(), namedOutput);
        FeederMethods.GetFeederTotalFeeds(handler, feeder.getSlot(), namedOutput);
        FeederMethods.GetFeederPartPitch(handler, feeder.getSlot(), namedOutput);
        FeederMethods.GetFeederFeedSpeed(handler, feeder.getSlot(), namedOutput);
        FeederMethods.GetFeederDispBright(handler, feeder.getSlot(), namedOutput);
        FeederMethods.GetFeederMotDir(handler, feeder.getSlot(), namedOutput);
        FeederMethods.GetFeederRemParts(handler, feeder.getSlot(), namedOutput);
        FeederMethods.GetFeederTotParts(handler, feeder.getSlot(), namedOutput);
        FeederMethods.GetFeederLowWarn(handler, feeder.getSlot(), namedOutput);
        FeederMethods.GetFeederMotSlowDelay(handler, feeder.getSlot(), namedOutput);
        FeederMethods.GetFeederShortID(handler, feeder.getSlot(), namedOutput);
        FeederMethods.GetFeederLongID(handler, feeder.getSlot(), namedOutput);
    }
}
