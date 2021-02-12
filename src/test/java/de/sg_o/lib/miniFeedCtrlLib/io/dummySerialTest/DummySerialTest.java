package de.sg_o.lib.miniFeedCtrlLib.io.dummySerialTest;

import de.sg_o.lib.miniFeedCtrlLib.base.Feeder;
import de.sg_o.lib.miniFeedCtrlLib.com.*;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcTransactionHandler;
import de.sg_o.lib.miniFeedCtrlLib.com.methods.FeederMethods;
import de.sg_o.lib.miniFeedCtrlLib.com.methods.MainboardMethods;
import de.sg_o.lib.miniFeedCtrlLib.common.InvalidDataException;
import de.sg_o.lib.miniFeedCtrlLib.io.dummySerial.DummySerial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DummySerialTest {

    @Test
    void sendNextTest() throws InvalidDataException {
        TransactionHandler h0 = new JRpcTransactionHandler();
        DummySerial d0 = new DummySerial(h0, "dummy0");
        assertFalse(d0.isConnected());
        d0.connect("");
        assertFalse(d0.isConnected());
        d0.connect("dummy0");
        assertTrue(d0.isConnected());
        assertEquals("dummy0", d0.listPorts()[0]);

        Transaction t0 = MainboardMethods.GetMainboardID(h0, true);
        d0.sendNext();
        d0.parseReceive();
        assertNotNull(t0);
        assertTrue(t0.isDone());
        assertEquals(ResultType.ARRAY, t0.getResponse().getResultType());

        Transaction t1 = FeederMethods.GetFeederID(h0, (byte)1, true);
        d0.sendNext();
        d0.parseReceive();
        assertNotNull(t1);
        assertTrue(t1.isDone());
        assertTrue(t1.getResponse().isError());
        int[] id = {0x89ABCDEF, 0xFFFFFFFF, 0xFFFFFFFF};
        d0.getHw().plugInFeeder(new Feeder(id, (byte) 1));
        Transaction t2 = FeederMethods.GetFeederID(h0, (byte)1, true);
        d0.sendNext();
        d0.parseReceive();
        assertNotNull(t2);
        assertTrue(t2.isDone());
        assertFalse(t2.getResponse().isError());
        assertArrayEquals(id, t2.getResponse().getResultAsIntArray());
    }
}