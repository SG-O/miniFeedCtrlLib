package de.sg_o.lib.miniFeedCtrlLib.common;

import de.sg_o.lib.miniFeedCtrlLib.base.Feeder;
import de.sg_o.lib.miniFeedCtrlLib.base.Mainboard;
import de.sg_o.lib.miniFeedCtrlLib.base.Status;
import de.sg_o.lib.miniFeedCtrlLib.com.Response;
import de.sg_o.lib.miniFeedCtrlLib.com.MessageDataType;
import de.sg_o.lib.miniFeedCtrlLib.com.Transaction;
import de.sg_o.lib.miniFeedCtrlLib.com.TransactionHandler;

public class MainboardParser {
    public static void parseMainboardUpdate(Transaction t, Mainboard mainboard, Mainboard mainboardBackup) throws InvalidDataException {
        if (t == null) return;
        if (t.getResponse().isError()) return;
        Response resp = t.getResponse();
        switch (t.getRequest().getMethod()) {
            case MAINBOARD_GET_PROTOCOL_VERSION:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("MAINBOARD_GET_PROTOCOL_VERSION", -1,-1);
                mainboard.setProtocolVersion((short)resp.getResultAsLong());
                mainboardBackup.setProtocolVersion((short)resp.getResultAsLong());
                break;
            case MAINBOARD_GET_STATUS:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("MAINBOARD_GET_STATUS", -1,-1);
                mainboard.setStatus(Status.fromCode((short)resp.getResultAsLong()));
                mainboardBackup.setStatus(Status.fromCode((short)resp.getResultAsLong()));
                break;
            case MAINBOARD_GET_ERROR:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("MAINBOARD_GET_ERROR", -1,-1);
                mainboard.setError(SystemError.fromCode((short)resp.getResultAsLong()));
                mainboardBackup.setError(SystemError.fromCode((short)resp.getResultAsLong()));
                break;
            case MAINBOARD_GET_FW_VERSION:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("MAINBOARD_GET_FW_VERSION", -1,-1);
                mainboard.setFirmwareVersion(resp.getResultAsLong());
                mainboardBackup.setFirmwareVersion(resp.getResultAsLong());
                break;
            case MAINBOARD_GET_HW_VERSION:
                if (resp.getResultType() != MessageDataType.LONG) throw new InvalidDataException("MAINBOARD_GET_HW_VERSION", -1,-1);
                mainboard.setHardwareVersion(resp.getResultAsLong());
                mainboardBackup.setHardwareVersion(resp.getResultAsLong());
                break;
        }
    }

    public static void requestAllMainboardParameters(TransactionHandler handler, boolean namedOutput) {
        if (handler == null) return;
        MainboardMethods.GetMainboardFwVer(handler, namedOutput);
        MainboardMethods.GetMainboardHwVer(handler, namedOutput);
        MainboardMethods.GetMainboardProtoVer(handler, namedOutput);
        MainboardMethods.GetMainboardStatus(handler, namedOutput);
        MainboardMethods.GetMainboardError(handler, namedOutput);
    }
}
