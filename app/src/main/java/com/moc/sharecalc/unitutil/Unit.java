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
    LITERS(UnitType.VOLUME, 1E0),
    MILLILITERS(UnitType.VOLUME, 1E-4),
    TEASPOONS(UnitType.VOLUME, 202.884),
    TABLESPOONS(UnitType.VOLUME, 67.628),
    FL_OZ(UnitType.VOLUME, 33.814),
    CUPS(UnitType.VOLUME, 4.22675),
    PINTS(UnitType.VOLUME, 2.11338),
    QUARTS(UnitType.VOLUME, 1.05669),
    GALLONS(UnitType.VOLUME, 0.264172),

    // Length unit base is meter
    METERS(UnitType.LENGTH, 1E0),
    MICROMETERS(UnitType.LENGTH, 1E-6),
    MILLIMETERS(UnitType.LENGTH, 1E-3),
    CENTIMETERS(UnitType.LENGTH, 1E-2),
    DECIMETERS(UnitType.LENGTH, 1E-1),
    DECAMETERS(UnitType.LENGTH, 1E1),
    KILOMETERS(UnitType.LENGTH, 1E3),
    INCHES(UnitType.LENGTH, 0.0254),
    FEET(UnitType.LENGTH, 0.3048),
    YARDS(UnitType.LENGTH, 0.9144),
    MILES(UnitType.LENGTH, 1609.34),
    LIGHTSECONDS(UnitType.LENGTH, 2.998E8),
    LIGHTMINUTES(UnitType.LENGTH, 1.799E10),
    LIGHTMONTHS(UnitType.LENGTH, 7.771E14),
    LIGHTYEARS(UnitType.LENGTH, 9.461E15),


    // Mass / weight unit base is kilogram
    // Note: mass != weight, but one can convert between the two
    // i.e. weight = mass * gravitational acceleration
    KILOGRAMS(UnitType.MASS_AND_WEIGHT, 1),
    GRAMS(UnitType.MASS_AND_WEIGHT, TODO__ADD_ACTUAL_VALUE),
    MILLIGRAMS(UnitType.MASS_AND_WEIGHT, TODO__ADD_ACTUAL_VALUE),
    CENTIGRAMS(UnitType.MASS_AND_WEIGHT, TODO__ADD_ACTUAL_VALUE),
    OUNCES(UnitType.MASS_AND_WEIGHT, TODO__ADD_ACTUAL_VALUE),
    POUNDS(UnitType.MASS_AND_WEIGHT, TODO__ADD_ACTUAL_VALUE),
    TONS(UnitType.MASS_AND_WEIGHT, TODO__ADD_ACTUAL_VALUE),

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
                case KELVIN: // C -> K
                    return amount + C_TO_K__SHIFT;
                case FAHRENHEIT: // C -> F
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
            return (amount * _baseFactor) // first convert to base unit
                    / toUnit._baseFactor; // then scale to the new unit
        }
    }


    public static String getConversions(Unit fromUnit, Double fromAmount) {
        StringBuilder result = new StringBuilder();
        Unit[] destinationUnits = Unit.values();
        for (Unit toUnit : destinationUnits)
        {
            if (toUnit != fromUnit // don't convert toUnit to itself
                && toUnit._type == fromUnit._type) // only convert between units of the same type
            {
                // Format example when converting to e.g. feet: 'in feet: 23'
                result.append("in "+toUnit.toString().toLowerCase() +": " + fromUnit.convertTo(toUnit, fromAmount) + "\n");
            }
        }
        return result.toString();
    }

}
