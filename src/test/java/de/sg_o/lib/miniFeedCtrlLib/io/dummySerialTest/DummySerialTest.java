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

package de.sg_o.lib.miniFeedCtrlLib.io.dummySerialTest;

import de.sg_o.lib.miniFeedCtrlLib.base.Feeder;
import de.sg_o.lib.miniFeedCtrlLib.com.*;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcTransactionHandler;
import de.sg_o.lib.miniFeedCtrlLib.common.FeederMethods;
import de.sg_o.lib.miniFeedCtrlLib.common.MainboardMethods;
import de.sg_o.lib.miniFeedCtrlLib.common.InvalidDataException;
import de.sg_o.lib.miniFeedCtrlLib.io.dummySerial.DummySerial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DummySerialTest {

    @Test
    void sendNextTest() throws InvalidDataException {
        TransactionHandler h0 = new JRpcTransactionHandler();
        DummySerial d0 = new DummySerial(h0, "dummy0", 0xFFFFFFFF);
        assertFalse(d0.isConnected());
        d0.connect("");
        assertFalse(d0.isConnected());
        d0.connect(DummySerial.PREFIX + DummySerial.SEPARATOR + "dummy0");
        assertTrue(d0.isConnected());
        assertEquals("dummy://dummy0", d0.listPorts()[0]);

        Transaction t0 = MainboardMethods.GetMainboardID(h0, true);
        d0.sendNext();
        d0.parseReceive();
        assertNotNull(t0);
        assertTrue(t0.isDone());
        assertEquals(MessageDataType.ARRAY, t0.getResponse().getResultType());

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