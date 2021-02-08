package de.sg_o.lib.miniFeedCtrlLib.com.jrpc.methods;

import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcRequest;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcTransaction;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcTransactionHandler;

public class JRpcMainboardMethods {
    public static JRpcTransaction GetMainboardFwVer(JRpcTransactionHandler handler) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetMainboardFwVer");
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetMainboardHwVer(JRpcTransactionHandler handler) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetMainboardHwVer");
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetMainboardID(JRpcTransactionHandler handler) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetMainboardID");
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetMainboardProtoVer(JRpcTransactionHandler handler) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetMainboardProtoVer");
        return handler.putRequest(request);
    }

    public static JRpcTransaction ListFeeders(JRpcTransactionHandler handler) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "ListFeeders");
        return handler.putRequest(request);
    }

    public static JRpcTransaction MainboardReset(JRpcTransactionHandler handler) {
        JRpcRequest request = new JRpcRequest(-1, "MainboardReset");
        return handler.putRequest(request);
    }

    public static JRpcTransaction ResetMainboardError(JRpcTransactionHandler handler) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "ResetMainboardError");
        return handler.putRequest(request);
    }

    public static JRpcTransaction ForceMainboardError(JRpcTransactionHandler handler) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "ForceMainboardError");
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetMainboardStatus(JRpcTransactionHandler handler) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetMainboardStatus");
        return handler.putRequest(request);
    }

    public static JRpcTransaction GetMainboardError(JRpcTransactionHandler handler) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "GetMainboardError");
        return handler.putRequest(request);
    }

    public static JRpcTransaction ListAllFeederStatus(JRpcTransactionHandler handler) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "ListAllFeederStatus");
        return handler.putRequest(request);
    }

    public static JRpcTransaction ListAllFeederError(JRpcTransactionHandler handler) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "ListAllFeederError");
        return handler.putRequest(request);
    }

    public static JRpcTransaction RunMainboardSelfTest(JRpcTransactionHandler handler) {
        JRpcRequest request = new JRpcRequest(handler.getAndIncCounter(), "RunMainboardSelfTest");
        return handler.putRequest(request);
    }
}
