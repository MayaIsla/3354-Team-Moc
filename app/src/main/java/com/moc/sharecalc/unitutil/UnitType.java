package com.moc.sharecalc.unitutil;

/**
 * A category of Units
 */
public enum UnitType {
    LENGTH, VOLUME, MASS_AND_WEIGHT, TEMPERATURE;

    /**
     * Returns a friendly, human-readable version of the enum name (e.g. Temperature)
     * @return a capitalized name of the enum value
     */
    public String toString() {
        String intermediate = this.name().toLowerCase().replace('_',' '); //lowercase w/ spaces instead of underscores
        return intermediate.substring(0,1).toUpperCase() + intermediate.substring(1); // capitalize first letter
    }

    /**
     * Given a human-friendly name, retrieve the matching UnitType enum
     * @param name Name of the enum value (case insensitive, may have spaces instead of underscores)
     * @return The matching UnitType
     */
    public static UnitType fromString(String name) {
        return UnitType.valueOf(name.toUpperCase().replace(' ','_'));
    }
}