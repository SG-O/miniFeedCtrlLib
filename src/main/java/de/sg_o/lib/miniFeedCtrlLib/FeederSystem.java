package de.sg_o.lib.miniFeedCtrlLib;

import de.sg_o.lib.miniFeedCtrlLib.base.Feeder;
import de.sg_o.lib.miniFeedCtrlLib.base.Mainboard;
import de.sg_o.lib.miniFeedCtrlLib.com.*;
import de.sg_o.lib.miniFeedCtrlLib.common.*;
import de.sg_o.lib.miniFeedCtrlLib.io.IO;
import de.sg_o.lib.miniFeedCtrlLib.io.dummySerial.DummySerial;
import de.sg_o.lib.miniFeedCtrlLib.io.serial.Serial;
import de.sg_o.lib.miniFeedCtrlLib.util.DummyConnection;

import java.util.HashSet;
import java.util.LinkedList;

public class FeederSystem {
    private IO io;
    private String port;
    private boolean namedOutput = false;
    private int timeout = 1000;
    private Transaction feederUpdate;

    private Mainboard mainboard;
    private Mainboard mainboardBackup;
    private final Feeder[] feeder = new Feeder[64];
    private final Feeder[] feederBackup = new Feeder[64];
    private final DummySerial ds;
    private final Serial serial;

    public FeederSystem(TransactionHandler handler) {
        this.ds = DummyConnection.createDummyConnection();
        this.serial = new Serial(handler);
        clean();
    }

    public String[] listAllConnections() {
        String[] dsPorts = ds.listPorts();
        String[] serialPorts = serial.listPorts();

        String[] combinedPorts = new String[dsPorts.length + serialPorts.length];
        System.arraycopy(dsPorts, 0, combinedPorts, 0, dsPorts.length);
        System.arraycopy(serialPorts, 0, combinedPorts, dsPorts.length, serialPorts.length);
        return combinedPorts;
    }

    public void setNamedOutput(boolean namedOutput) {
        this.namedOutput = namedOutput;
    }

    public boolean isNamedOutput() {
        return namedOutput;
    }

    public Mainboard getMainboard() {
        return mainboard;
    }

    public Feeder[] getFeeders() {
        LinkedList<Feeder> feederList = new LinkedList<>();
        for (Feeder f: feeder) {
            if (f == null) continue;
            feederList.add(f);
        }
        return feederList.toArray(new Feeder[0]);
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean setIO(String port) {
        if (isConnected()) return false;
        if (port == null) {
            this.io = null;
            this.port = null;
            return false;
        }
        if (port.startsWith(DummySerial.PREFIX)) {
            this.io = ds;
            this.port = port;
            return true;
        }
        if (port.startsWith(Serial.PREFIX)) {
            this.io = serial;
            this.port = port;
            return true;
        }
        this.io = null;
        this.port = null;
        return false;
    }

    public IO getIO() {
        return io;
    }

    public boolean connect() {
        if (this.io == null) return false;
        if (this.port == null) return false;
        return io.connect(this.port);
    }

    public boolean isConnected() {
        if (this.io == null) return false;
        if (this.port == null) return false;
        return io.isConnected();
    }

    public void disconnect() {
        if (isConnected()) {
            this.io.disconnect();
        }
        clean();
    }

    public boolean init() {
        if (!isConnected()) {
            if (!connect()) return false;
        }
        clean();
        MainboardMethods.GetMainboardID(this.io.getHandler(), namedOutput);
        return true;
    }

    private void clean() {
        mainboard = null;
        mainboardBackup = null;
        for (byte i = 0; i < feeder.length; i++) {
            feeder[i] = null;
            feederBackup[i] = null;
        }
    }

    public void runRegularTasks() {
        if (!isConnected()) return;
        io.sendNext();
        io.getHandler().CheckTimeouts(timeout);
        parse();
    }

    public void parse() {
        if (!isConnected()) return;
        Transaction t = io.parseReceive();
        if (t == null) return;
        if (this.mainboard == null) {
            if (t.getRequest().getMethod() == Method.MAINBOARD_GET_ID) {
                if (t.getResponse().isError()) {
                    MainboardMethods.GetMainboardID(this.io.getHandler(), namedOutput);
                    return;
                }
                int[] id = t.getResponse().getResultAsIntArray();
                if (id == null) {
                    MainboardMethods.GetMainboardID(this.io.getHandler(), namedOutput);
                    return;
                }
                if (id.length != 3) {
                    MainboardMethods.GetMainboardID(this.io.getHandler(), namedOutput);
                } else {
                    try {
                        this.mainboard = new Mainboard(id);
                        this.mainboardBackup = new Mainboard(id);
                    } catch (InvalidDataException e) {
                        MainboardMethods.GetMainboardID(this.io.getHandler(), namedOutput);
                    }
                }
            }
            return;
        }
        switch (t.getRequest().getMethod()) {
            case MAINBOARD_GET_PROTOCOL_VERSION:
            case MAINBOARD_GET_STATUS:
            case MAINBOARD_GET_ERROR:
            case MAINBOARD_GET_FW_VERSION:
            case MAINBOARD_GET_HW_VERSION:
                if (t.getResponse().isError()) {
                    this.mainboard.setError(t.getResponse().getError());
                    return;
                }
                try {
                    MainboardParser.parseMainboardUpdate(t, mainboard, mainboardBackup);
                } catch (InvalidDataException e) {
                    this.mainboard.setError(SystemError.PARSE_ERROR);
                }
                break;
            case MAINBOARD_LIST_FEEDERS:
                if (t.getResponse().isError()) {
                    this.mainboard.setError(t.getResponse().getError());
                    return;
                }
                Response resp = t.getResponse();
                if (resp.getResultType() != MessageDataType.ARRAY) {
                    this.mainboard.setError(SystemError.PARSE_ERROR);
                    return;
                }
                byte[] slots = resp.getResultAsByteArray();
                HashSet<Byte> slotSet = new HashSet<>(slots.length);
                for (byte slot: slots) {
                    if (slot < 0 || slot > 63) continue;
                    slotSet.add(slot);
                    FeederMethods.GetFeederID(this.io.getHandler(), slot, namedOutput);
                }
                for (byte i = 0; i < feeder.length; i++) {
                    if (slotSet.contains(i)) continue;
                    feeder[i] = null;
                    feederBackup[i] = null;
                }
                break;
            case MAINBOARD_GET_ID:
            case MAINBOARD_LIST_ALL_FEEDER_STATUS:
            case MAINBOARD_LIST_ALL_FEEDER_ERROR:
            case MAINBOARD_NOP:
            case MAINBOARD_RESET_ERROR:
            case MAINBOARD_FORCE_ERROR:
            case MAINBOARD_RUN_SELF_TEST:
            case MAINBOARD_HW_RESET:
                if (t.getResponse().isError()) {
                    this.mainboard.setError(t.getResponse().getError());
                    return;
                }
                break;
            case FEEDER_NOP:
            case FEEDER_FEED:
            case FEEDER_RESET_ERROR:
            case FEEDER_FORCE_ERROR:
            case FEEDER_RUN_SELF_TEST:
            case FEEDER_HW_RESET:
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
                try {
                    byte slot = getFeederSlot(t);
                    if ((feeder[slot] != null) && (feederBackup[slot] != null)) {
                        if (t.getResponse().isError()) {
                            this.feeder[slot].setError(t.getResponse().getError());
                            return;
                        }
                    }
                } catch (InvalidDataException e) {
                    this.mainboard.setError(SystemError.PARSE_ERROR);
                }
                break;
            case FEEDER_GET_ID:
                if (t.getResponse().isError()) {
                    this.mainboard.setError(t.getResponse().getError());
                    return;
                }
                try {
                    byte slot = getFeederSlot(t);
                    if ((feeder[slot] != null) && (feederBackup[slot] != null)) {
                        break;
                    }
                    int[] id = t.getResponse().getResultAsIntArray();
                    if (id == null) {
                        break;
                    }
                    if (id.length != 3) {
                        break;
                    }
                    feeder[slot] = new Feeder(id, slot);
                    feederBackup[slot] = new Feeder(id, slot);
                } catch (InvalidDataException e) {
                    this.mainboard.setError(SystemError.PARSE_ERROR);
                }
                break;
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
                try {
                    byte slot = getFeederSlot(t);
                    if ((feeder[slot] != null) && (feederBackup[slot] != null)) {
                        if (t.getResponse().isError()) {
                            this.feeder[slot].setError(t.getResponse().getError());
                            return;
                        }
                        FeederParser.parseFeederUpdate(t, feeder[slot], feederBackup[slot]);
                    }
                } catch (InvalidDataException e) {
                    this.mainboard.setError(SystemError.PARSE_ERROR);
                }
                break;
            default:
                this.mainboard.setError(SystemError.PARSE_ERROR);
        }
    }

    public void feed(byte slot) {
        if (!isConnected()) return;
        if (slot < 0 || slot > 63) return;
        if (feeder[slot] == null) return;
        feeder[slot].setRemainingParts(feeder[slot].getRemainingParts() - 1);
        feederBackup[slot].setRemainingParts(feeder[slot].getRemainingParts());
        feeder[slot].setTotalFeeds(feeder[slot].getTotalFeeds() + 1);
        feederBackup[slot].setTotalFeeds(feeder[slot].getTotalFeeds());
        FeederMethods.FeedFeeder(this.io.getHandler(), slot, namedOutput);
    }

    public void resetFeederError(byte slot) {
        if (!isConnected()) return;
        if (slot < 0 || slot > 63) return;
        if (feeder[slot] == null) return;
        FeederMethods.ResetFeederError(this.io.getHandler(), slot, namedOutput);
    }

    public void forceFeederError(byte slot) {
        if (!isConnected()) return;
        if (slot < 0 || slot > 63) return;
        if (feeder[slot] == null) return;
        FeederMethods.ForceFeederError(this.io.getHandler(), slot, namedOutput);
    }

    public void resetMainboardError() {
        if (!isConnected()) return;
        if (mainboard == null) return;
        MainboardMethods.ResetMainboardError(this.io.getHandler(), namedOutput);
    }

    public void forceMainboardError() {
        if (!isConnected()) return;
        if (mainboard == null) return;
        MainboardMethods.ForceMainboardError(this.io.getHandler(), namedOutput);
    }

    public void requestAllMainboardParameters() {
        if (!isConnected()) return;
        MainboardParser.requestAllMainboardParameters(io.getHandler(), namedOutput);
    }

    public void updateConnectedFeeders() {
        if (!isConnected()) return;
        if (feederUpdate != null) {
            if (!(feederUpdate.isDone() || feederUpdate.hasFailed())) return;
        }
        feederUpdate = MainboardMethods.ListFeeders(this.io.getHandler(), namedOutput);
    }

    public void requestAllFeederParameters(byte slot) {
        if (!isConnected()) return;
        if (slot < 0 || slot > 63) return;
        if (feeder[slot] == null) return;
        FeederParser.requestAllFeederParameters(io.getHandler(),feeder[slot], namedOutput);
    }

    public void sendUpdatedFeeder(byte slot) {
        if (!isConnected()) return;
        if (slot < 0 || slot > 63) return;
        try {
            FeederParser.sendFeederChanges(io.getHandler(), feeder[slot], feederBackup[slot], namedOutput);
        } catch (InvalidDataException ignore) {
        }
    }

    private static byte getFeederSlot(Transaction t) throws InvalidDataException {
        Request req = t.getRequest();
        byte slot = (byte) req.getDataAsLong("feederSlot", 0);
        if (slot < 0 || slot > 63) {
            throw new InvalidDataException("feederSlot", 0, 63);
        }
        return slot;
    }
}
