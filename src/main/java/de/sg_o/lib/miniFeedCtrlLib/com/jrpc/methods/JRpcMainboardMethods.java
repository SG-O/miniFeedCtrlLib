package de.sg_o.lib.miniFeedCtrlLib.com.jrpc.methods;

import de.sg_o.lib.miniFeedCtrlLib.com.Method;
import de.sg_o.lib.miniFeedCtrlLib.com.Transaction;
import de.sg_o.lib.miniFeedCtrlLib.com.TransactionHandler;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcRequest;

public class JRpcMainboardMethods {
    public static Transaction GetMainboardFwVer(TransactionHandler handler, boolean namedDataOutput) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), Method.MAINBOARD_GET_FW_VERSION);
        request.setNamedDataOutput(namedDataOutput);
        return handler.putRequest(request);
    }

    public static Transaction GetMainboardHwVer(TransactionHandler handler, boolean namedDataOutput) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), Method.MAINBOARD_GET_HW_VERSION);
        request.setNamedDataOutput(namedDataOutput);
        return handler.putRequest(request);
    }

    public static Transaction GetMainboardID(TransactionHandler handler, boolean namedDataOutput) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), Method.MAINBOARD_GET_ID);
        request.setNamedDataOutput(namedDataOutput);
        return handler.putRequest(request);
    }

    public static Transaction GetMainboardProtoVer(TransactionHandler handler, boolean namedDataOutput) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), Method.MAINBOARD_GET_PROTOCOL_VERSION);
        request.setNamedDataOutput(namedDataOutput);
        return handler.putRequest(request);
    }

    public static Transaction ListFeeders(TransactionHandler handler, boolean namedDataOutput) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), Method.MAINBOARD_LIST_FEEDERS);
        request.setNamedDataOutput(namedDataOutput);
        return handler.putRequest(request);
    }

    public static Transaction MainboardReset(TransactionHandler handler, boolean namedDataOutput) {
        JRpcRequest request = new JRpcRequest(-1, Method.MAINBOARD_HW_RESET);
        request.setNamedDataOutput(namedDataOutput);
        return handler.putRequest(request);
    }

    public static Transaction ResetMainboardError(TransactionHandler handler, boolean namedDataOutput) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), Method.MAINBOARD_RESET_ERROR);
        request.setNamedDataOutput(namedDataOutput);
        return handler.putRequest(request);
    }

    public static Transaction ForceMainboardError(TransactionHandler handler, boolean namedDataOutput) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), Method.MAINBOARD_FORCE_ERROR);
        request.setNamedDataOutput(namedDataOutput);
        return handler.putRequest(request);
    }

    public static Transaction GetMainboardStatus(TransactionHandler handler, boolean namedDataOutput) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), Method.MAINBOARD_GET_STATUS);
        request.setNamedDataOutput(namedDataOutput);
        return handler.putRequest(request);
    }

    public static Transaction GetMainboardError(TransactionHandler handler, boolean namedDataOutput) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), Method.MAINBOARD_GET_ERROR);
        request.setNamedDataOutput(namedDataOutput);
        return handler.putRequest(request);
    }

    public static Transaction ListAllFeederStatus(TransactionHandler handler, boolean namedDataOutput) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), Method.MAINBOARD_LIST_ALL_FEEDER_STATUS);
        request.setNamedDataOutput(namedDataOutput);
        return handler.putRequest(request);
    }

    public static Transaction ListAllFeederError(TransactionHandler handler, boolean namedDataOutput) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), Method.MAINBOARD_LIST_ALL_FEEDER_ERROR);
        request.setNamedDataOutput(namedDataOutput);
        return handler.putRequest(request);
    }

    public static Transaction RunMainboardSelfTest(TransactionHandler handler, boolean namedDataOutput) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), Method.MAINBOARD_RUN_SELF_TEST);
        request.setNamedDataOutput(namedDataOutput);
        return handler.putRequest(request);
    }
}
