package com.moc.sharecalc.unitutil;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UnitConversionTests {



    // Basic unit conversion tests.
    // Note that we use a precision delta of 0.5 to ignore rounding errors.
    @ParameterizedTest
    @MethodSource("basicConvertTestProvider")
    void basicConvertTest(double amount, Unit unit, double toAmount, Unit toUnit) {
        assertEquals(toAmount, unit.convertTo(toUnit, amount), 0.5);
    }

    /**
     * Unit test for all unit conversions. Asserts that the first arguments of Arguements.of() is equal to the
     * third argument, using second and fourth arguments to perform the conversion; the second argument being
     * the base unit and the fourth the unit to convert to.
     *
     * @return Stream of arguments that test the conversion between units
     */
    private static Stream<Arguments> basicConvertTestProvider() {
        return Stream.of(
                // Because temperature conversions are special, it helps to test them extra thoroughly
                Arguments.of(55, Unit.FAHRENHEIT, 12.8, Unit.CELSIUS),
                Arguments.of(55, Unit.FAHRENHEIT, 55, Unit.FAHRENHEIT),
                Arguments.of(55, Unit.FAHRENHEIT, 285.9, Unit.KELVIN),

                Arguments.of(55, Unit.CELSIUS, 55, Unit.CELSIUS),
                Arguments.of(55, Unit.CELSIUS, 131, Unit.FAHRENHEIT),
                Arguments.of(55, Unit.CELSIUS, 328.15, Unit.KELVIN),

                Arguments.of(55, Unit.KELVIN, -218.5, Unit.CELSIUS),
                Arguments.of(55, Unit.KELVIN, -360.7, Unit.FAHRENHEIT),
                Arguments.of(55, Unit.KELVIN, 55, Unit.KELVIN),

                //Tests for conversions between various lengths
                Arguments.of(55, Unit.METERS, 55, Unit.METERS),
                Arguments.of(55, Unit.METERS, 55E2, Unit.CENTIMETERS),
                Arguments.of(55, Unit.FEET, 660, Unit.INCHES),
                Arguments.of(55E-8, Unit.LIGHTSECONDS, 1.803E2, Unit.YARDS),
                Arguments.of(55E-5, Unit.MILES, 8.851E2, Unit.MILLIMETERS),
                Arguments.of(55E-5, Unit.DECAMETERS, 5500, Unit.MICROMETERS),

                //Tests for conversions between various volumes
                Arguments.of(10, Unit.LITERS, 10, Unit.LITERS),
                Arguments.of(10, Unit.LITERS, 10E3, Unit.MILLILITERS),
                Arguments.of(10, Unit.LITERS, 2028.84, Unit.TEASPOONS),
                Arguments.of(10, Unit.LITERS, 676.28, Unit.TABLESPOONS),
                Arguments.of(10, Unit.LITERS, 338.14, Unit.FL_OZ),
                Arguments.of(10, Unit.LITERS, 42.2675, Unit.CUPS),
                Arguments.of(10, Unit.LITERS, 21.1338, Unit.PINTS),
                Arguments.of(10, Unit.LITERS, 10.5669, Unit.QUARTS),
                Arguments.of(10, Unit.LITERS, 2.64172, Unit.GALLONS),

                //Tests for conversions between various masses/weights.
                Arguments.of(45, Unit.KILOGRAMS, 45, Unit.KILOGRAMS),
                Arguments.of(45, Unit.KILOGRAMS, 45E3, Unit.GRAMS),
                Arguments.of(45, Unit.KILOGRAMS, 45E6, Unit.MILLIGRAMS),
                Arguments.of(45, Unit.KILOGRAMS, 45E5, Unit.CENTIGRAMS),
                Arguments.of(45, Unit.KILOGRAMS, 1587.33, Unit.OUNCES),
                Arguments.of(45, Unit.KILOGRAMS, 99.208, Unit.POUNDS),
                Arguments.of(45, Unit.KILOGRAMS, 0.049604, Unit.TONS)


                );
    }





}