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

package de.sg_o.lib.miniFeedCtrlLib.base;

import de.sg_o.lib.miniFeedCtrlLib.common.SystemError;
import de.sg_o.lib.miniFeedCtrlLib.common.InvalidDataException;
import de.sg_o.lib.miniFeedCtrlLib.util.ByteArray;

import java.util.Arrays;
import java.util.Objects;

import static de.sg_o.lib.miniFeedCtrlLib.util.ByteArray.intsToHex;

public class Feeder {
    private final int[] id = new int[3];
    private final byte slot;

    private long firmwareVersion;
    private long hardwareVersion;
    private short protocolVersion;

    private Status status;
    private SystemError error;

    private int remainingParts;
    private int totalParts;
    private int lowPartsWarning;
    private long totalFeeds;

    private String shortPartID;
    private String longPartID;

    private int motorSlowdownDelay;
    private short partPitch;
    private short feedSpeed;
    private short displayBrightness;
    private short motorDirection;

    public Feeder(int[] id, byte slot) throws InvalidDataException {
        if (id == null) throw new InvalidDataException("ID(null)", 3, -1);
        if (id.length < 3) throw new InvalidDataException("ID", 3, -1);
        System.arraycopy(id, 0, this.id, 0, this.id.length);
        if ((slot > 63) || (slot < 0)) slot = -1;
        this.slot = slot;
        this.status = Status.UNKNOWN;
        this.error = SystemError.UNKNOWN;
    }

    public void setFirmwareVersion(long firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public void setHardwareVersion(long hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public void setProtocolVersion(short protocolVersion) throws InvalidDataException {
        if ((protocolVersion < 0) || (protocolVersion > 255)) throw new InvalidDataException("protocolVersion", 0, 255);
        this.protocolVersion = protocolVersion;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setError(SystemError error) {
        this.error = error;
    }

    public void setRemainingParts(int remainingParts) {
        this.remainingParts = remainingParts;
    }

    public void setTotalParts(int totalParts) {
        this.totalParts = totalParts;
    }

    public void setLowPartsWarning(int lowPartsWarning) throws InvalidDataException {
        if ((lowPartsWarning < 0) || (lowPartsWarning > 65535)) throw new InvalidDataException("lowPartsWarning", 0, 65535);
        this.lowPartsWarning = lowPartsWarning;
    }

    public void setTotalFeeds(long totalFeeds) {
        this.totalFeeds = totalFeeds;
    }

    public void setShortPartID(String shortPartID) throws InvalidDataException {
        if (shortPartID == null) throw new InvalidDataException("shortPartID(null)", 1, 5);
        if ((shortPartID.length() < 1) || (shortPartID.length() > 5)) throw new InvalidDataException("shortPartID", 1, 5);
        this.shortPartID = shortPartID;
    }

    public void setLongPartID(String longPartID) throws InvalidDataException {
        if (longPartID == null) longPartID = "";
        if ((longPartID.length() > 126)) throw new InvalidDataException("longPartID", 0, 126);
        this.longPartID = longPartID;
    }

    public void setMotorSlowdownDelay(int motorSlowdownDelay) throws InvalidDataException {
        if ((motorSlowdownDelay < 0) || (motorSlowdownDelay > 65535)) throw new InvalidDataException("motorSlowdownDelay", 0, 65535);
        this.motorSlowdownDelay = motorSlowdownDelay;
    }

    public void setPartPitch(short partPitch) throws InvalidDataException {
        if ((partPitch < 1) || (partPitch > 255)) throw new InvalidDataException("partPitch", 1, 255);
        this.partPitch = partPitch;
    }

    public void setFeedSpeed(short feedSpeed) throws InvalidDataException {
        if ((feedSpeed < 1) || (feedSpeed > 255)) throw new InvalidDataException("feedSpeed", 1, 255);
        this.feedSpeed = feedSpeed;
    }

    public void setDisplayBrightness(short displayBrightness) throws InvalidDataException {
        if ((displayBrightness < 1) || (displayBrightness > 255)) throw new InvalidDataException("displayBrightness", 1, 255);
        this.displayBrightness = displayBrightness;
    }

    public void setMotorDirection(short motorDirection) {
        if (motorDirection < 0) motorDirection = 0;
        if (motorDirection > 1) motorDirection = 1;
        this.motorDirection = motorDirection;
    }

    public int[] getId() {
        return id;
    }

    public String getIdString() {
        return intsToHex(id);
    }

    public byte getSlot() {
        if ((slot > 63) || (slot < 0)) return -1;
        return slot;
    }

    public long getFirmwareVersion() {
        return firmwareVersion;
    }

    public long getHardwareVersion() {
        return hardwareVersion;
    }

    public short getProtocolVersion() {
        if (protocolVersion < 0) return 0;
        if (protocolVersion > 255) return 255;
        return protocolVersion;
    }

    public Status getStatus() {
        return status;
    }

    public SystemError getError() {
        return error;
    }

    public int getRemainingParts() {
        return remainingParts;
    }

    public int getTotalParts() {
        return totalParts;
    }

    public int getLowPartsWarning() {
        if (lowPartsWarning < 0) return 0;
        return Math.min(lowPartsWarning, 65535);
    }

    public long getTotalFeeds() {
        return totalFeeds;
    }

    public String getShortPartID() {
        if (shortPartID == null) return "-";
        if (shortPartID.length() < 1) return "-";
        if (shortPartID.length() > 5) return shortPartID.substring(0, 6);
        return shortPartID;
    }

    public String getLongPartID() {
        if (longPartID == null) return "-";
        if (longPartID.length() < 1) return "";
        if (longPartID.length() > 126) return longPartID.substring(0, 127);
        return longPartID;
    }

    public int getMotorSlowdownDelay() {
        if (motorSlowdownDelay < 0) return 0;
        return Math.min(motorSlowdownDelay, 65535);
    }

    public short getPartPitch() {
        if (partPitch < 1) return 1;
        if (partPitch > 255) return 255;
        return partPitch;
    }

    public short getFeedSpeed() {
        if (feedSpeed < 1) return 1;
        if (feedSpeed > 255) return 255;
        return feedSpeed;
    }

    public short getDisplayBrightness() {
        if (displayBrightness < 1) return 1;
        if (displayBrightness > 255) return 255;
        return displayBrightness;
    }

    public short getMotorDirection() {
        if (motorDirection < 0) return 0;
        if (motorDirection > 1) return 1;
        return motorDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feeder feeder = (Feeder) o;
        return getSlot() == feeder.getSlot() && Arrays.equals(getId(), feeder.getId());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getSlot());
        result = 31 * result + Arrays.hashCode(getId());
        return result;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Feeder{");
        sb.append("id=").append(getIdString());
        sb.append(", slot=").append(getSlot());
        sb.append(", firmwareVersion=").append(ByteArray.intsToHex(new int[] {(int)(getFirmwareVersion() >> 32),(int)getFirmwareVersion()}));
        sb.append(", hardwareVersion=").append(ByteArray.intsToHex(new int[] {(int)(getHardwareVersion() >> 32),(int)getHardwareVersion()}));
        sb.append(", protocolVersion=").append(ByteArray.bytesToHex(new byte[] {(byte) getProtocolVersion()}));
        sb.append(", status=").append(getStatus());
        sb.append(", error=").append(getError());
        sb.append(", remainingParts=").append(getRemainingParts());
        sb.append(", totalParts=").append(getTotalParts());
        sb.append(", lowPartsWarning=").append(getLowPartsWarning());
        sb.append(", totalFeeds=").append(getTotalFeeds());
        sb.append(", shortPartID='").append(getShortPartID()).append('\'');
        sb.append(", longPartID='").append(getLongPartID()).append('\'');
        sb.append(", motorSlowdownDelay=").append(getMotorSlowdownDelay());
        sb.append(", partPitch=").append(getPartPitch());
        sb.append(", feedSpeed=").append(getFeedSpeed());
        sb.append(", displayBrightness=").append(getDisplayBrightness());
        sb.append(", motorDirection=").append(getMotorDirection());
        sb.append('}');
        return sb.toString();
    }
}
