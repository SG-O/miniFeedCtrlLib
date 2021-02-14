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

package de.sg_o.lib.miniFeedCtrlLib.utilTest;

import de.sg_o.lib.miniFeedCtrlLib.util.ByteArray;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static de.sg_o.lib.miniFeedCtrlLib.util.ByteArray.*;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("FieldCanBeLocal")
class ByteArrayTest {
    private final byte[] b0 = {0, 1, 2, 3};
    private final byte[] b1 = {9, 10, 127, -1};
    private final byte[] b2 = {};
    private final byte[] b3 = null;

    private final int[] i0 = {1234567890, 987654321, -1111111111};
    private final int[] i1 = {1, 2, 3, -1};
    private final int[] i2 = {};
    private final int[] i3 = null;

    private final String s0 = "00010203";
    private final String s1 = "090A7FFF";
    private final String s2 = "";
    private final String s3 = "499602D23ADE68B1BDC5CA39";
    @SuppressWarnings("SpellCheckingInspection")
    private final String s4 = "000000010000000200000003FFFFFFFF";

    @Test
    public void byteArrayTest() {
        //noinspection InstantiationOfUtilityClass
        ByteArray b = new ByteArray();
        assertNotNull(b);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void bytesToHexTest() {
        assertEquals(s0, bytesToHex(b0));
        assertEquals(s1, bytesToHex(b1));
        assertEquals(s2, bytesToHex(b2));
        assertEquals(s2, bytesToHex(b3));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void intsToHexTest() {
        assertEquals(s3, intsToHex(i0));
        assertEquals(s4, intsToHex(i1));
        assertEquals(s2, intsToHex(i2));
        assertEquals(s2, intsToHex(i3));
    }

    @Test
    public void hexToBytesTest() {
        assertArrayEquals(b0, hexToBytes(s0));
        assertArrayEquals(b1, hexToBytes(s1));
        assertArrayEquals(b1, hexToBytes("090a7fff"));
        assertArrayEquals(b2, hexToBytes(s2));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void appendTest() {
        byte[] b4 = {0, 1, 2, 3, 9, 10, 127, -1};
        assertArrayEquals(b4, append(b0, b1));
        assertArrayEquals(b0, append(b0, b2));
        assertArrayEquals(b0, append(b2, b0));
        assertNull(append(b0, b3));
        assertNull(append(b3, b0));
        assertNull(append(b3, b3));
    }

    @Test
    public void toFromBytesTest() {
        byte[] o0 = toBytes(-1, 8);
        byte[] o1 = toBytes(10000, 8);
        byte[] o2 = toBytes(Long.MAX_VALUE, 8);
        byte[] o3 = toBytes(Long.MIN_VALUE, 8);
        byte[] o4 = toBytes(0, 8);
        byte[] o5 = toBytes(0, 9);
        byte[] o6 = toBytes(0, -5);

        //noinspection SpellCheckingInspection
        assertEquals("FFFFFFFFFFFFFFFF", bytesToHex(o0));
        assertEquals(-1, fromBytes(o0));
        assertEquals("0000000000002710", bytesToHex(o1));
        assertEquals(10000, fromBytes(o1));
        //noinspection SpellCheckingInspection
        assertEquals("7FFFFFFFFFFFFFFF", bytesToHex(o2));
        assertEquals(Long.MAX_VALUE, fromBytes(o2));
        assertEquals("8000000000000000", bytesToHex(o3));
        assertEquals(Long.MIN_VALUE, fromBytes(o3));
        assertEquals("0000000000000000", bytesToHex(o4));
        assertEquals(0, fromBytes(o4));
        assertEquals(0, fromBytes(null));

        assertEquals("0000000000000000", bytesToHex(o5));
        assertEquals("", bytesToHex(o6));

        byte[] b4 = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        assertEquals(-1, fromBytes(b4));

        Random r = new Random();
        long l;
        byte[] o;
        for (int i = 0; i < 1000; i++) {
            l = r.nextLong();
            o = toBytes(l, 8);
            assertEquals(l, fromBytes(o));
        }
        int j;
        for (int i = 0; i < 1000; i++) {
            j = r.nextInt();
            o = toBytes(j, 4);
            assertEquals(j, (int) fromBytes(o));
        }
        short s;
        for (int i = 0; i < 1000; i++) {
            s = (short) r.nextInt();
            o = toBytes(s, 2);
            assertEquals(s, (short) fromBytes(o));
        }
    }

}