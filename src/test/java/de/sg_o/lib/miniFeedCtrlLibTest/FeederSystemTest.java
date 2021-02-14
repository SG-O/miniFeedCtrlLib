package de.sg_o.lib.miniFeedCtrlLibTest;

import de.sg_o.lib.miniFeedCtrlLib.FeederSystem;
import de.sg_o.lib.miniFeedCtrlLib.base.Feeder;
import de.sg_o.lib.miniFeedCtrlLib.base.Status;
import de.sg_o.lib.miniFeedCtrlLib.com.TransactionHandler;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcTransactionHandler;
import de.sg_o.lib.miniFeedCtrlLib.common.SystemError;
import de.sg_o.lib.miniFeedCtrlLib.util.ByteArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeederSystemTest {
    private static FeederSystem f0;

    @BeforeAll
    static void beforeAll() {
        TransactionHandler handler = new JRpcTransactionHandler();
        f0 = new FeederSystem(handler);
    }

    @AfterAll
    static void afterAll() {
        f0 = null;
    }

    @Test
    void listAllConnectionsTest() {
        assertEquals("dummy://dummy0", f0.listAllConnections()[0]);
    }

    @Test
    void connectTest() {
        f0.disconnect();
        assertFalse(f0.setIO(null));
        assertFalse(f0.isConnected());
        assertFalse(f0.connect());
        assertFalse(f0.isConnected());
        assertFalse(f0.setIO("not://existing"));
        assertFalse(f0.isConnected());
        assertFalse(f0.connect());
        assertFalse(f0.isConnected());
        assertTrue(f0.setIO(f0.listAllConnections()[0]));
        assertFalse(f0.isConnected());
        assertTrue(f0.connect());
        assertTrue(f0.isConnected());
        f0.disconnect();
        assertFalse(f0.isConnected());
        assertTrue(f0.connect());
        assertTrue(f0.isConnected());
        assertFalse(f0.setIO(f0.listAllConnections()[0]));
        f0.disconnect();
        assertFalse(f0.isConnected());
        assertTrue(f0.setIO(f0.listAllConnections()[0]));
    }

    @Test
    void mainboardTest() {
        f0.disconnect();
        assertFalse(f0.setIO(null));
        assertFalse(f0.init());
        assertTrue(f0.setIO(f0.listAllConnections()[0]));
        assertTrue(f0.connect());
        assertTrue(f0.isConnected());
        assertNull(f0.getMainboard());
        assertTrue(f0.init());
        assertNull(f0.getMainboard());
        assertTrue(f0.getIO().sendNext());
        assertNull(f0.getMainboard());
        f0.parse();
        assertNotNull(f0.getMainboard());
        assertEquals("DEADBEEFFFFFFFFF00000000", ByteArray.intsToHex(f0.getMainboard().getId()));
        assertEquals(Status.UNKNOWN, f0.getMainboard().getStatus());
        f0.requestAllMainboardParameters();
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        assertEquals(Status.READY, f0.getMainboard().getStatus());
        assertEquals(SystemError.NO_ERROR, f0.getMainboard().getError());
        assertEquals(0xDEADBEEF00000000L, f0.getMainboard().getFirmwareVersion());
        assertEquals(0xDEADBEEF00000000L, f0.getMainboard().getHardwareVersion());
        assertEquals((byte) 0x01, f0.getMainboard().getProtocolVersion());
        f0.requestAllMainboardParameters();
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        assertEquals(Status.READY, f0.getMainboard().getStatus());
        assertEquals(SystemError.NO_ERROR, f0.getMainboard().getError());
        f0.forceMainboardError();
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        f0.requestAllMainboardParameters();
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        assertEquals(Status.ERROR, f0.getMainboard().getStatus());
        assertEquals(SystemError.USER_CAUSED, f0.getMainboard().getError());
        f0.resetMainboardError();
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        f0.requestAllMainboardParameters();
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        assertEquals(Status.READY, f0.getMainboard().getStatus());
        assertEquals(SystemError.NO_ERROR, f0.getMainboard().getError());
        f0.disconnect();
    }

    @Test
    void feederTest() {
        f0.disconnect();
        assertFalse(f0.setIO(null));
        assertTrue(f0.setIO(f0.listAllConnections()[0]));
        assertTrue(f0.init());
        assertFalse(f0.connect());
        assertTrue(f0.isConnected());
        assertTrue(f0.init());
        f0.runRegularTasks();
        assertEquals(0, f0.getFeeders().length);
        f0.updateConnectedFeeders();
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        Feeder[] feeders = f0.getFeeders();
        assertEquals(3, feeders.length);
        assertEquals(Status.UNKNOWN, feeders[2].getStatus());
        assertEquals(SystemError.UNKNOWN, feeders[2].getError());
        f0.requestAllFeederParameters(feeders[2].getSlot());
        for (int i = 0; i < 40; i++) {
            f0.runRegularTasks();
        }
        assertEquals(Status.READY, feeders[2].getStatus());
        assertEquals(SystemError.NO_ERROR, feeders[2].getError());
        assertEquals("89ABCDEFFFFFFFFF00000002", ByteArray.intsToHex(feeders[2].getId()));
        assertEquals(0, feeders[2].getTotalParts());
        feeders[2].setTotalParts(100);
        assertEquals(100, feeders[2].getTotalParts());
        f0.sendUpdatedFeeder(feeders[2].getSlot());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        f0.requestAllFeederParameters(feeders[2].getSlot());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        assertEquals(100, feeders[2].getTotalParts());

        assertEquals(0, feeders[2].getRemainingParts());
        feeders[2].setRemainingParts(100);
        assertEquals(100, feeders[2].getRemainingParts());
        f0.sendUpdatedFeeder(feeders[2].getSlot());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        f0.requestAllFeederParameters(feeders[2].getSlot());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        assertEquals(100, feeders[2].getRemainingParts());
        assertEquals(0, feeders[2].getTotalFeeds());
        f0.feed(feeders[2].getSlot());
        assertEquals(99, feeders[2].getRemainingParts());
        assertEquals(1, feeders[2].getTotalFeeds());
        f0.requestAllFeederParameters(feeders[2].getSlot());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        assertEquals(1, feeders[2].getTotalFeeds());
        assertEquals(99, feeders[2].getRemainingParts());

        assertEquals(Status.READY, feeders[2].getStatus());
        assertEquals(SystemError.NO_ERROR, feeders[2].getError());
        f0.forceFeederError(feeders[2].getSlot());
        assertEquals(Status.READY, feeders[2].getStatus());
        assertEquals(SystemError.NO_ERROR, feeders[2].getError());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        f0.requestAllFeederParameters(feeders[2].getSlot());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        assertEquals(Status.ERROR, feeders[2].getStatus());
        assertEquals(SystemError.USER_CAUSED, feeders[2].getError());
        f0.resetFeederError(feeders[2].getSlot());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        f0.requestAllFeederParameters(feeders[2].getSlot());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        assertEquals(Status.READY, feeders[2].getStatus());
        assertEquals(SystemError.NO_ERROR, feeders[2].getError());
        assertFalse(f0.isNamedOutput());
        f0.setNamedOutput(true);
        assertTrue(f0.isNamedOutput());
        f0.requestAllFeederParameters(feeders[2].getSlot());
        for (int i = 0; i < 40; i++) {
            f0.runRegularTasks();
        }
        assertEquals(Status.READY, feeders[2].getStatus());
        assertEquals(SystemError.NO_ERROR, feeders[2].getError());
        assertEquals("89ABCDEFFFFFFFFF00000002", ByteArray.intsToHex(feeders[2].getId()));
        assertEquals(100, feeders[2].getTotalParts());
        feeders[2].setTotalParts(1000);
        assertEquals(1000, feeders[2].getTotalParts());
        f0.sendUpdatedFeeder(feeders[2].getSlot());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        f0.requestAllFeederParameters(feeders[2].getSlot());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        assertEquals(1000, feeders[2].getTotalParts());

        assertEquals(99, feeders[2].getRemainingParts());
        feeders[2].setRemainingParts(200);
        assertEquals(200, feeders[2].getRemainingParts());
        f0.sendUpdatedFeeder(feeders[2].getSlot());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        f0.requestAllFeederParameters(feeders[2].getSlot());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        assertEquals(200, feeders[2].getRemainingParts());
        assertEquals(1, feeders[2].getTotalFeeds());
        f0.feed(feeders[2].getSlot());
        assertEquals(199, feeders[2].getRemainingParts());
        assertEquals(2, feeders[2].getTotalFeeds());
        f0.requestAllFeederParameters(feeders[2].getSlot());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        assertEquals(2, feeders[2].getTotalFeeds());
        assertEquals(199, feeders[2].getRemainingParts());

        assertEquals(Status.READY, feeders[2].getStatus());
        assertEquals(SystemError.NO_ERROR, feeders[2].getError());
        f0.forceFeederError(feeders[2].getSlot());
        assertEquals(Status.READY, feeders[2].getStatus());
        assertEquals(SystemError.NO_ERROR, feeders[2].getError());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        f0.requestAllFeederParameters(feeders[2].getSlot());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        assertEquals(Status.ERROR, feeders[2].getStatus());
        assertEquals(SystemError.USER_CAUSED, feeders[2].getError());
        f0.resetFeederError(feeders[2].getSlot());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        f0.requestAllFeederParameters(feeders[2].getSlot());
        for (int i = 0; i < 20; i++) {
            f0.runRegularTasks();
        }
        assertEquals(Status.READY, feeders[2].getStatus());
        assertEquals(SystemError.NO_ERROR, feeders[2].getError());
    }
}