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

package de.sg_o.lib.miniFeedCtrlLib.common;

import java.util.Objects;

@SuppressWarnings("unused")
public class InvalidDataException extends Exception {
    private final String dataName;
    private final long lowerBound;
    private final long upperBound;


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

    public String getDataName() {
        return dataName;
    }

    public long getLowerBound() {
        return lowerBound;
    }

    public long getUpperBound() {
        return upperBound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvalidDataException that = (InvalidDataException) o;
        return getLowerBound() == that.getLowerBound() && getUpperBound() == that.getUpperBound() && Objects.equals(getDataName(), that.getDataName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDataName(), getLowerBound(), getUpperBound());
    }

    @Override
    public String toString() {
        return "InvalidDataException{" + "dataName='" + dataName + '\'' +
                ", lowerBound=" + lowerBound +
                ", upperBound=" + upperBound +
                '}';
    }
}
