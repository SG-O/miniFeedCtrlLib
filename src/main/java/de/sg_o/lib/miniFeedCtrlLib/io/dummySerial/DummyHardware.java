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

package de.sg_o.lib.miniFeedCtrlLib.io.dummySerial;

import de.sg_o.lib.miniFeedCtrlLib.base.Feeder;
import de.sg_o.lib.miniFeedCtrlLib.base.Mainboard;
import de.sg_o.lib.miniFeedCtrlLib.base.Status;
import de.sg_o.lib.miniFeedCtrlLib.com.Request;
import de.sg_o.lib.miniFeedCtrlLib.com.Response;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcRequest;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcResponse;
import de.sg_o.lib.miniFeedCtrlLib.common.InvalidDataException;
import de.sg_o.lib.miniFeedCtrlLib.common.SystemError;

import java.util.Arrays;

@SuppressWarnings("unused")
public class DummyHardware {
    private static final short MAINBOARD_PROTO_VERSION = 0x01;
    private static final long MAINBOARD_HW_VERSION = 0xDEADBEEF00000000L;
    private static final long MAINBOARD_FW_VERSION = 0xDEADBEEF00000000L;
    private static final short FEEDER_PROTO_VERSION = 0x01;
    private static final long FEEDER_HW_VERSION = 0xDEADBEEF00000000L;
    private static final long FEEDER_FW_VERSION = 0xDEADBEEF00000000L;
    
    private final Mainboard mainboard;
    private final Feeder[] feeders = new Feeder[64];

    public DummyHardware(int lowerID) throws InvalidDataException {
        int[] id = {0xDEADBEEF, 0xFFFFFFFF, lowerID};
        this.mainboard = new Mainboard(id);
        this.mainboard.setError(SystemError.NO_ERROR);
        this.mainboard.setStatus(Status.READY);
        Arrays.fill(feeders, null);
    }

    public void plugInFeeder(Feeder feeder) {
        if (feeder == null) return;
        if (feeder.getSlot() < 0) return;
        if (feeders[feeder.getSlot()] != null) return;
        feeders[feeder.getSlot()] = feeder;
    }

    public void removeFeeder(byte slot) {
        if (slot < 0) return;
        if (slot > 63) return;
        feeders[slot] = null;
    }

    public Mainboard getMainboard() {
        return mainboard;
    }

    public Feeder getFeeder(byte slot) {
        return feeders[slot];
    }

    public Response parseTransaction(byte[] msg) {
        if (msg == null) return null;
        JRpcRequest req = new JRpcRequest(msg);
        Response resp;
        resp = new JRpcResponse(req.getId());
        switch (req.getMethod()) {
            case MAINBOARD_GET_PROTOCOL_VERSION:
                resp.resultPutUnsignedByte(MAINBOARD_PROTO_VERSION);
                break;
            case MAINBOARD_GET_STATUS:
                if (mainboard.getError() != SystemError.NO_ERROR) {
                    resp.resultPutUnsignedByte(Status.ERROR.getCode());
                } else {
                    resp.resultPutUnsignedByte(mainboard.getStatus().getCode());
                }
                break;
            case MAINBOARD_GET_ERROR:
                resp.resultPutUnsignedByte(mainboard.getError().getCode());
                break;
            case MAINBOARD_GET_ID:
                int[] id = mainboard.getId();
                resp.resultPutUnsignedInt(id[0]);
                resp.resultPutUnsignedInt(id[1]);
                resp.resultPutUnsignedInt(id[2]);
                break;
            case MAINBOARD_GET_FW_VERSION:
                resp.resultPutLong(MAINBOARD_FW_VERSION);
                break;
            case MAINBOARD_GET_HW_VERSION:
                resp.resultPutLong(MAINBOARD_HW_VERSION);
                break;
            case MAINBOARD_LIST_FEEDERS:
                for (byte i = 0; i < 64; i++) {
                    if (feeders[i] != null) resp.resultPutUnsignedByte(i);
                }
                break;
            case MAINBOARD_LIST_ALL_FEEDER_STATUS:
                for (byte i = 0; i < 64; i++) {
                    if (feeders[i] != null) {
                        resp.resultPutUnsignedByte(feeders[i].getStatus().getCode());
                    }
                }
                break;
            case MAINBOARD_LIST_ALL_FEEDER_ERROR:
                for (byte i = 0; i < 64; i++) {
                    if (feeders[i] != null) {
                        resp.resultPutUnsignedByte(feeders[i].getError().getCode());
                    }
                }
                break;
            case MAINBOARD_NOP:
            case MAINBOARD_RUN_SELF_TEST:
            case MAINBOARD_HW_RESET:
                resp.resultPutUnsignedByte((short) 0);
                break;
            case MAINBOARD_RESET_ERROR:
                mainboard.setError(SystemError.NO_ERROR);
                resp.resultPutUnsignedByte((short) 0);
                break;
            case MAINBOARD_FORCE_ERROR:
                mainboard.setError(SystemError.USER_CAUSED);
                resp.setError(mainboard.getError());
                break;
            case FEEDER_SET_REMAINING_PARTS:
            case FEEDER_SET_TOTAL_PARTS:
            case FEEDER_SET_PART_PITCH:
            case FEEDER_SET_FEED_SPEED:
            case FEEDER_SET_LOW_PARTS_WARNING_THRESHOLD:
            case FEEDER_SET_SHORT_ID:
            case FEEDER_SET_LONG_ID:
            case FEEDER_SET_DISPLAY_BRIGHTNESS:
            case FEEDER_SET_MOTOR_DIRECTION:
            case FEEDER_SET_MOTOR_SLOWDOWN_DELAY:
            case FEEDER_GET_ID:
            case FEEDER_GET_FW_VERSION:
            case FEEDER_GET_HW_VERSION:
            case FEEDER_GET_REMAINING_PARTS:
            case FEEDER_GET_TOTAL_PARTS:
            case FEEDER_GET_LOW_PARTS_WARNING_THRESHOLD:
            case FEEDER_GET_SHORT_ID:
            case FEEDER_GET_LONG_ID:
            case FEEDER_GET_MOTOR_SLOWDOWN_DELAY:
            case FEEDER_GET_TOTAL_FEEDS:
            case FEEDER_GET_PROTOCOL_VERSION:
            case FEEDER_GET_STATUS:
            case FEEDER_GET_ERROR:
            case FEEDER_GET_PART_PITCH:
            case FEEDER_GET_FEED_SPEED:
            case FEEDER_GET_DISPLAY_BRIGHTNESS:
            case FEEDER_GET_MOTOR_DIRECTION:
            case FEEDER_NOP:
            case FEEDER_FEED:
            case FEEDER_RESET_ERROR:
            case FEEDER_FORCE_ERROR:
            case FEEDER_RUN_SELF_TEST:
            case FEEDER_HW_RESET:
                try {
                    parseFeeder(req, resp);
                } catch (InvalidDataException e) {
                    resp.setError(SystemError.INVALID_INPUT);
                }
                break;
            default:
                return null;
        }
        if (req.getId() > 0) {
            return resp;
        }
        return null;
    }

    private void parseFeeder(Request req, Response resp) throws InvalidDataException {
        if (req == null) return;
        byte feeder;
        String dataString = "";
        long dataLong = 0;
        if (req instanceof JRpcRequest) {
            JRpcRequest request = (JRpcRequest)req;
            if (request.getOrderedData().length() > 0) {
                feeder = (byte)request.getOrderedData().optInt(0, -1);
                dataString = request.getOrderedData().optString(1, "");
                dataLong = request.getOrderedData().optLong(1, 0);
            } else {
                feeder = (byte)request.getData().optInt("feederSlot", -1);
                for (String s: request.getData().keySet()) {
                    if (s.equals("feederSlot")) continue;
                    dataString = request.getData().optString(s, "");
                    dataLong = request.getData().optLong(s, 0);
                }
            }
        } else {
            resp.setError(SystemError.NO_FEEDER);
            return;
        }
        if (feeder < 0) {
            resp.setError(SystemError.NO_FEEDER);
            return;
        }
        if (feeders[feeder] == null) {
            resp.setError(SystemError.NO_FEEDER);
            return;
        }
        Feeder f = feeders[feeder];
        switch (req.getMethod()) {
            case FEEDER_SET_REMAINING_PARTS:
                f.setRemainingParts((int) dataLong);
                resp.resultPutUnsignedByte((short) 0);
                break;
            case FEEDER_SET_TOTAL_PARTS:
                f.setTotalParts((int) dataLong);
                resp.resultPutUnsignedByte((short) 0);
                break;
            case FEEDER_SET_PART_PITCH:
                f.setPartPitch((short) dataLong);
                resp.resultPutUnsignedByte((short) 0);
                break;
            case FEEDER_SET_FEED_SPEED:
                f.setFeedSpeed((short) dataLong);
                resp.resultPutUnsignedByte((short) 0);
                break;
            case FEEDER_SET_LOW_PARTS_WARNING_THRESHOLD:
                f.setLowPartsWarning((int) dataLong);
                resp.resultPutUnsignedByte((short) 0);
                break;
            case FEEDER_SET_SHORT_ID:
                f.setShortPartID(dataString);
                resp.resultPutUnsignedByte((short) 0);
                break;
            case FEEDER_SET_LONG_ID:
                f.setLongPartID(dataString);
                resp.resultPutUnsignedByte((short) 0);
                break;
            case FEEDER_SET_DISPLAY_BRIGHTNESS:
                f.setDisplayBrightness((short) dataLong);
                resp.resultPutUnsignedByte((short) 0);
                break;
            case FEEDER_SET_MOTOR_DIRECTION:
                f.setMotorDirection((short) dataLong);
                resp.resultPutUnsignedByte((short) 0);
                break;
            case FEEDER_SET_MOTOR_SLOWDOWN_DELAY:
                f.setMotorSlowdownDelay((int) dataLong);
                resp.resultPutUnsignedByte((short) 0);
                break;
            case FEEDER_GET_ID:
                int[] id = f.getId();
                resp.resultPutUnsignedInt(id[0]);
                resp.resultPutUnsignedInt(id[1]);
                resp.resultPutUnsignedInt(id[2]);
                break;
            case FEEDER_GET_FW_VERSION:
                resp.resultPutLong(FEEDER_FW_VERSION);
                break;
            case FEEDER_GET_HW_VERSION:
                resp.resultPutLong(FEEDER_HW_VERSION);
                break;
            case FEEDER_GET_REMAINING_PARTS:
                resp.resultPutInt(f.getRemainingParts());
                break;
            case FEEDER_GET_TOTAL_PARTS:
                resp.resultPutInt(f.getTotalParts());
                break;
            case FEEDER_GET_LOW_PARTS_WARNING_THRESHOLD:
                resp.resultPutUnsignedShort(f.getLowPartsWarning());
                break;
            case FEEDER_GET_SHORT_ID:
                resp.resultPutString(f.getShortPartID());
                break;
            case FEEDER_GET_LONG_ID:
                resp.resultPutString(f.getLongPartID());
                break;
            case FEEDER_GET_MOTOR_SLOWDOWN_DELAY:
                resp.resultPutUnsignedShort(f.getMotorSlowdownDelay());
                break;
            case FEEDER_GET_TOTAL_FEEDS:
                resp.resultPutLong(f.getTotalFeeds());
                break;
            case FEEDER_GET_PROTOCOL_VERSION:
                resp.resultPutUnsignedByte(FEEDER_PROTO_VERSION);
                break;
            case FEEDER_GET_STATUS:
                if (f.getError() != SystemError.NO_ERROR) {
                    resp.resultPutUnsignedByte(Status.ERROR.getCode());
                } else {
                    resp.resultPutUnsignedByte(f.getStatus().getCode());
                }
                break;
            case FEEDER_GET_ERROR:
                resp.resultPutUnsignedByte(f.getError().getCode());
                break;
            case FEEDER_GET_PART_PITCH:
                resp.resultPutUnsignedByte(f.getPartPitch());
                break;
            case FEEDER_GET_FEED_SPEED:
                resp.resultPutUnsignedByte(f.getFeedSpeed());
                break;
            case FEEDER_GET_DISPLAY_BRIGHTNESS:
                resp.resultPutUnsignedByte(f.getDisplayBrightness());
                break;
            case FEEDER_GET_MOTOR_DIRECTION:
                resp.resultPutUnsignedByte(f.getMotorDirection());
                break;
            case FEEDER_NOP:
            case FEEDER_RUN_SELF_TEST:
            case FEEDER_HW_RESET:
                resp.resultPutUnsignedByte((short) 0);
                break;
            case FEEDER_FEED:
                f.setRemainingParts(f.getRemainingParts() - 1);
                f.setTotalFeeds(f.getTotalFeeds() + 1);
                resp.resultPutUnsignedByte((short) 0);
                break;
            case FEEDER_RESET_ERROR:
                f.setError(SystemError.NO_ERROR);
                resp.resultPutUnsignedByte((short) 0);
                break;
            case FEEDER_FORCE_ERROR:
                f.setError(SystemError.USER_CAUSED);
                resp.resultPutUnsignedByte((short) 0);
                break;
            default:
                resp.setError(SystemError.UNKNOWN_METHOD);
                break;
        }
    }
}
