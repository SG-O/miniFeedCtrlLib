package de.sg_o.lib.miniFeedCtrlLib.io.dummySerial;

import de.sg_o.lib.miniFeedCtrlLib.base.Feeder;
import de.sg_o.lib.miniFeedCtrlLib.base.Mainboard;
import de.sg_o.lib.miniFeedCtrlLib.common.InvalidDataException;

import java.util.Arrays;

public class DummyHardware {
    private Mainboard mainboard;
    private Feeder[] feeders = new Feeder[64];

    public DummyHardware() throws InvalidDataException {
        int[] id = {0xDEADBEEF};
        this.mainboard = new Mainboard(id);
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
}
