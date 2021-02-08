package de.sg_o.lib.miniFeedCtrlLib.common;

public class InvalidDataException extends Exception {
    private String dataName;
    private long lowerBound;
    private long upperBound;


    public InvalidDataException(String dataName, long lowerBound, long upperBound) {
        super("Data \"" + dataName + "\" out of bounds. Min:" + lowerBound + " Max:" + upperBound);
        this.dataName = dataName;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public InvalidDataException(Throwable cause, String dataName, long lowerBound, long upperBound) {
        super("Data \"" + dataName + "\" out of bounds. Min:" + lowerBound + " Max:" + upperBound, cause);
        this.dataName = dataName;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }
}
