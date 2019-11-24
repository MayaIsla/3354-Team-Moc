package com.moc.sharecalc.unitutil;

import static com.moc.sharecalc.unitutil.UnitConstants.C_TO_F__SCALE;
import static com.moc.sharecalc.unitutil.UnitConstants.C_TO_F__SHIFT;
import static com.moc.sharecalc.unitutil.UnitConstants.C_TO_K__SHIFT;
import static com.moc.sharecalc.unitutil.UnitConstants.TODO__ADD_ACTUAL_VALUE;


/**
 * A Unit represents a unit of temperature, volume, length, etc.
 * A Unit can be converted to any other unit of the same UnitType.
 * It accomplieshes this by first converting to a base unit (e.g. the base for length is meter),
 * then converting from the base unit to the result unit.
 */
public enum Unit {

    // Volume unit base is liter
    LITERS(UnitType.VOLUME, 1),
    MILLILITERS(UnitType.VOLUME, TODO__ADD_ACTUAL_VALUE),
    TEASPOONS(UnitType.VOLUME, TODO__ADD_ACTUAL_VALUE),
    TABLESPOONS(UnitType.VOLUME, TODO__ADD_ACTUAL_VALUE),
    FL_OZ(UnitType.VOLUME, TODO__ADD_ACTUAL_VALUE),
    CUPS(UnitType.VOLUME, TODO__ADD_ACTUAL_VALUE),
    PINTS(UnitType.VOLUME, TODO__ADD_ACTUAL_VALUE),
    QUARTS(UnitType.VOLUME, TODO__ADD_ACTUAL_VALUE),
    GALLONS(UnitType.VOLUME, TODO__ADD_ACTUAL_VALUE),

    // Length unit base is meter
    METERS(UnitType.LENGTH, 1),
    MICROMETERS(UnitType.LENGTH, TODO__ADD_ACTUAL_VALUE),
    MILLIMETERS(UnitType.LENGTH, TODO__ADD_ACTUAL_VALUE),
    CENTIMETERS(UnitType.LENGTH, TODO__ADD_ACTUAL_VALUE),
    DECIMETERS(UnitType.LENGTH, TODO__ADD_ACTUAL_VALUE),
    DECAMETERS(UnitType.LENGTH, TODO__ADD_ACTUAL_VALUE),
    KILOMETERS(UnitType.LENGTH, TODO__ADD_ACTUAL_VALUE),
    INCHES(UnitType.LENGTH, TODO__ADD_ACTUAL_VALUE),
    FEET(UnitType.LENGTH, TODO__ADD_ACTUAL_VALUE),
    YARDS(UnitType.LENGTH, TODO__ADD_ACTUAL_VALUE),
    MILES(UnitType.LENGTH, TODO__ADD_ACTUAL_VALUE),
    LIGHTSECOND(UnitType.LENGTH, TODO__ADD_ACTUAL_VALUE),
    LIGHTMINUTE(UnitType.LENGTH, TODO__ADD_ACTUAL_VALUE),
    LIGHTMONTH(UnitType.LENGTH, TODO__ADD_ACTUAL_VALUE),
    LIGHTYEAR(UnitType.LENGTH, TODO__ADD_ACTUAL_VALUE),


    // Mass / weight unit base is kilogram
    // Note: mass != weight, but one can convert between the two
    // i.e. weight = mass * gravitational acceleration
    KILOGRAM(UnitType.MASS_AND_WEIGHT, 1),
    GRAM(UnitType.MASS_AND_WEIGHT, TODO__ADD_ACTUAL_VALUE),
    MILLIGRAM(UnitType.MASS_AND_WEIGHT, TODO__ADD_ACTUAL_VALUE),
    CENTIGRAM(UnitType.MASS_AND_WEIGHT, TODO__ADD_ACTUAL_VALUE),
    OUNCE(UnitType.MASS_AND_WEIGHT, TODO__ADD_ACTUAL_VALUE),
    POUND(UnitType.MASS_AND_WEIGHT, TODO__ADD_ACTUAL_VALUE),
    TON(UnitType.MASS_AND_WEIGHT, TODO__ADD_ACTUAL_VALUE),

    // Temperature is special to calculate since it involves not just scaling but also shifting
    // Hence we use an alternate constructor and manually override the conversion methods.
    // To make the code as DRY and error-free as possible, for converting from Kelvin and
    // Fahrenheit to any other unit, we simply convert to Celsius and then call the Celsius's
    // conversion method. Celsius is the only temperature unit that directly converts to all
    // other units. (I.e. Celsius is the base unit.)
    CELSIUS(UnitType.TEMPERATURE) {
        @Override
        public Double convertTo(Unit toUnit, Double amount) {
            switch (toUnit) {
                case CELSIUS: // trivial, same unit
                    return amount;
                case KELVIN:
                    return amount + C_TO_K__SHIFT;
                case FAHRENHEIT:
                    return (amount * C_TO_F__SCALE) + C_TO_F__SHIFT;
            }
            // If exhausted every valid option
            throw new IllegalArgumentException();
        }
    },
    KELVIN(UnitType.TEMPERATURE) {
        @Override
        public Double convertTo(Unit toUnit, Double amount) {
            // Convert to Celsius, then call Celsius's convert method to do the rest
            double inCelsius = amount - C_TO_K__SHIFT;
            return CELSIUS.convertTo(toUnit, inCelsius);
        }
    },
    FAHRENHEIT(UnitType.TEMPERATURE) {
        @Override
        public Double convertTo(Unit toUnit, Double amount) {
            // Convert to Celsius, then call Celsius's convert method to do the rest
            double inCelsius = (amount - C_TO_F__SHIFT) / C_TO_F__SCALE;
            return CELSIUS.convertTo(toUnit, inCelsius);
        }
    };


    /**
     * The category of the unit
     */
    private UnitType _type;

    /**
     * What one should multiply a value of this Unit to convert to the base unit.
     * E.g. the base factor of millimeter is 1E-3 so that one can multiply it
     * by a number of millimeters to get a number of meters.
     */
    private double _baseFactor;

    /**
     * Private constructor, used for Units with default conversion implementation
     * @param type category of unit
     * @param baseFactor see _baseFactor
     */
    Unit(UnitType type, double baseFactor) {_type = type; _baseFactor = baseFactor;}

    /**
     * Private constructor, used for Units with special conversion implementation
     * @param type category of unit
     */
    Unit(UnitType type) {_type = type;}

    /**
     * Convert an amount in one unit to an amount in another unit
     * @param toUnit The new unit to convert to
     * @param amount The amount of the old unit you want to convert
     * @return The amount in the new unit
     */
    public Double convertTo(Unit toUnit, Double amount) {
        if (_type != toUnit._type)
        {
            throw new IllegalArgumentException();
        } else {
            return (amount / _baseFactor) // first convert to base unit
                    * toUnit._baseFactor; // then scale to the new unit
        }
    }

}
