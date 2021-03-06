/*
 *
 * Copyright 2021 SG-O (Joerg Bayer)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.sg_o.lib.miniFeedCtrlLib.common;

import de.sg_o.lib.miniFeedCtrlLib.com.Method;
import de.sg_o.lib.miniFeedCtrlLib.com.Transaction;
import de.sg_o.lib.miniFeedCtrlLib.com.TransactionHandler;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcRequest;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public class MainboardMethods {
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
