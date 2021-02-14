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

package de.sg_o.lib.miniFeedCtrlLib.util;

import de.sg_o.lib.miniFeedCtrlLib.base.Feeder;
import de.sg_o.lib.miniFeedCtrlLib.base.Status;
import de.sg_o.lib.miniFeedCtrlLib.com.TransactionHandler;
import de.sg_o.lib.miniFeedCtrlLib.com.jrpc.JRpcTransactionHandler;
import de.sg_o.lib.miniFeedCtrlLib.common.InvalidDataException;
import de.sg_o.lib.miniFeedCtrlLib.common.SystemError;
import de.sg_o.lib.miniFeedCtrlLib.io.dummySerial.DummySerial;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class DummyConnection {
    private static final String DEFAULT_NAME_PREFIX = "dummy";
    private static final int[] DEFAULT_FEEDERS = {0, 1, 3};

    private static int connectionNumber = 0;
    private static int feederNumber = 0;

    public static DummySerial createDummyConnection() {
        Properties defaultProps = new Properties();
        try {
            FileInputStream in = new FileInputStream("dummyConnectionProperties");
            defaultProps.load(in);
            in.close();
        } catch (IOException ignore) {
        }
        String namePrefix = defaultProps.getProperty("namePrefix", DEFAULT_NAME_PREFIX);

        TransactionHandler handler = new JRpcTransactionHandler();
        DummySerial ds;
        try {
            ds = new DummySerial(handler, namePrefix + connectionNumber, connectionNumber);
        } catch (InvalidDataException ignore) {
            return null;
        }
        connectionNumber++;

        String pluggedFeeders = defaultProps.getProperty("pluggedFeeders", Arrays.toString(DEFAULT_FEEDERS));
        @SuppressWarnings("RegExpRedundantEscape")
        String[] pluggedFeedersArray = pluggedFeeders.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

        int[] feeders = new int[pluggedFeedersArray.length];
        for (int i = 0; i < pluggedFeedersArray.length; i++) {
            try {
                feeders[i] = Integer.parseInt(pluggedFeedersArray[i]);
            } catch (NumberFormatException ignore) {
                feeders[i] = -1;
            }
        }

        for (int feederSlot: feeders) {
            if (feederSlot < 0 || feederSlot > 63) continue;
            Feeder f;
            try {
                f = new Feeder(new int[] {0x89ABCDEF, 0xFFFFFFFF, feederNumber}, (byte)feederSlot);
                f.setStatus(Status.READY);
                f.setError(SystemError.NO_ERROR);
                f.setShortPartID("D"+ feederNumber);
                f.setLongPartID("Dummy Part " + feederSlot);
                feederNumber++;
            } catch (InvalidDataException ignore) {
                continue;
            }
            ds.getHw().plugInFeeder(f);
        }
        return ds;
    }
}
