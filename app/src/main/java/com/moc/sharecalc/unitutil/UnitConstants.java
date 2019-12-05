package com.moc.sharecalc.unitutil;

/**
 * Contains constants used by special conversions in the Unit enum
 * NOTE: A constant is not given for every single conversion ratio because
 * such default-behaviour ratios are only ever referenced once and hence are DRY by default.
 */
public class UnitConstants {



    // Temperature constants
    // A. Shifts
    static final double C_TO_K__SHIFT = 273.15;
    static final double C_TO_F__SHIFT = 32;
    // B. Scales
    static final double C_TO_F__SCALE = 9.0/5.0;
}
