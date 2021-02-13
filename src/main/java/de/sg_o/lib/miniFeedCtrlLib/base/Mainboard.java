package de.sg_o.lib.miniFeedCtrlLib.base;

import de.sg_o.lib.miniFeedCtrlLib.common.SystemError;
import de.sg_o.lib.miniFeedCtrlLib.common.InvalidDataException;
import de.sg_o.lib.miniFeedCtrlLib.util.ByteArray;

import java.util.Arrays;

import static de.sg_o.lib.miniFeedCtrlLib.util.ByteArray.intsToHex;

public class Mainboard {
    private final int[] id = new int[3];

    private long firmwareVersion;
    private long hardwareVersion;
    private short protocolVersion;

    private Status status;
    private SystemError error;

    public Mainboard(int[] id) throws InvalidDataException {
        if (id == null) throw new InvalidDataException("ID(null)", 3, -1);;
        if (id.length < 3) throw new InvalidDataException("ID", 3, -1);
        System.arraycopy(id, 0, this.id, 0, this.id.length);
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

    public int[] getId() {
        return id;
    }

    public String getIdString() {
        return intsToHex(id);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mainboard mainboard = (Mainboard) o;
        return Arrays.equals(getId(), mainboard.getId());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Mainboard{");
        sb.append("id=").append(getIdString());
        sb.append(", firmwareVersion=0x").append(ByteArray.intsToHex(new int[] {(int)(getFirmwareVersion() >> 32),(int)getFirmwareVersion()}));
        sb.append(", hardwareVersion=0x").append(ByteArray.intsToHex(new int[] {(int)(getHardwareVersion() >> 32),(int)getHardwareVersion()}));
        sb.append(", protocolVersion=0x").append(ByteArray.bytesToHex(new byte[] {(byte) getProtocolVersion()}));
        sb.append(", status=").append(getStatus());
        sb.append(", error=").append(getError());
        sb.append('}');
        return sb.toString();
    }
}
